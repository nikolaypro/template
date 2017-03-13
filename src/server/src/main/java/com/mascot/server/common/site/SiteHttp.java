package com.mascot.server.common.site;

import com.mascot.common.MascotUtils;
import org.apache.commons.httpclient.auth.BasicScheme;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * Created by Nikolay on 10.03.2017.
 */
public class SiteHttp <RequestClass, ResponseClass> {
    public static boolean DEBUG = false;
    private final ObjectMapper mapper = new ObjectMapper();
    private static int unique_id = 0;
    private static final Logger httpLogger = LoggerFactory.getLogger("SiteHttpRequests");
    private static final Logger logger = LoggerFactory.getLogger(SiteHttp.class);

    private final SiteSettings settings;

    public SiteHttp(SiteSettings settings) {
        this.settings = settings;
        mapper.setDeserializationConfig(mapper.getDeserializationConfig().without(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES));
    }

    //    private static final String TEST_URL = "https://partnerapi.judopay-sandbox.com/";
//    private static final String PROD_URL = "https://partnerapi.judopay.com/";
//    private static final String TOKEN = "xAwMsQLRNypKYgAF";
//    private static final String SECRET = "4511ab2b5e2a11957aada6deb538538a2f6bf798e4f0d3e32b7606e794d86b6f";

    public ResponseClass doPost(String url, RequestClass requestObject, Class<ResponseClass> typeParameterClass) throws SiteHttpException {
        return processRequest(url, requestObject, true, typeParameterClass);
    }

    public ResponseClass doGet(String url, Class<ResponseClass> typeParameterClass) throws SiteHttpException {
        return processRequest(url, null, false, typeParameterClass);
    }

    private ResponseClass processRequest(String commandUrl, Object requestObject, boolean post, Class<ResponseClass> valueType) throws SiteHttpException {
        final int counter = unique_id++;
        final String url = settings.getUrl() + commandUrl;
        final String requestBody = toJsonString(requestObject);
        final String responseBody;
        HttpClient httpClient = null;
        try {
            httpClient = createHttpClient();
            final HttpRequestBase request;
            if (post) {
                request = createHttpPost(url, requestBody);
                httpLogger.info("ID: {}\nURL: {}\n Request: \n{}\n", new Object[]{counter, url, requestBody});
            } else {
                request = createHttpGet(url);
                httpLogger.info("ID: {}\nURL: {}\n", new Object[]{counter, url});
            }
            responseBody = sendRequest(httpClient, request, counter);
            return parseJson(responseBody, valueType);
        } finally {
            if (httpClient != null) {
                httpClient.getConnectionManager().shutdown();
            }
        }
    }

    private HttpClient createHttpClient() throws SiteHttpException {
        int timeoutSec = 10;
        final DefaultHttpClient httpClient = new DefaultHttpClient();
        final HttpParams httpParams = httpClient.getParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, timeoutSec * 1000); // http.connection.timeout
        HttpConnectionParams.setSoTimeout(httpParams, timeoutSec * 1000); // http.socket.timeout

        if (DEBUG) {
            final String path = getKeystorePath() + "\\" + "client.truststore";
            System.out.println("path = " + path);
            final String psw = "echo2013";
            System.setProperty("javax.net.ssl.trustStore", path);
            System.setProperty("javax.net.ssl.trustStorePassword", psw);
//                org.apache.log4j.Logger.getLogger("org.apache.http.headers").setLevel(Level.DEBUG);
//                org.apache.log4j.Logger.getLogger("org.apache.http.wire").setLevel(Level.DEBUG);
//                System.setProperty("javax.net.debug", "all");
//                System.setProperty("jsse.enableSNIExtension", "false");
//                System.setProperty("https.protocols", "TLSv1.2");
        }
        return wrapClient(httpClient);
    }

    public HttpClient wrapClient(HttpClient base) throws SiteHttpException {
        try {
            final SSLContext ctx = SSLContext.getInstance("TLSv1.2");
            ctx.init(null, null, null);
            final SSLSocketFactory ssf = new SSLSocketFactory(ctx);
            final ClientConnectionManager ccm = base.getConnectionManager();
            final SchemeRegistry sr = ccm.getSchemeRegistry();
            sr.register(new Scheme("https", 443, ssf));
            return new DefaultHttpClient(ccm, base.getParams());
        } catch (Exception ex) {
            throw createException(ex, "Unable wrap http client");
        }
    }

    private String getKeystorePath() {
        throw new UnsupportedOperationException();
/*
        String confDir = System.getProperty(EchoConstants.JBOSS_CONF_DIR_PROPERTY);
        if (DEBUG || confDir == null) {
            logger.info("Will use a test configuration path");
            confDir = new File("core\\jboss-config\\src\\main\\resources\\jboss-nonconfigurable\\server\\default\\conf").getAbsolutePath();
        }
        return confDir.replaceAll("\\\\", "/");
*/
    }

    private <A> A parseJson(String responseBody, Class<A> valueType) throws SiteHttpException {
        final A jsonResponse;
        try {
            jsonResponse = mapper.readValue(responseBody, valueType);
        } catch (IOException e) {
            throw createException(e, "Error parsing JSON response (" + responseBody + ")");
        }
        return jsonResponse;
    }

    private String sendRequest(HttpClient httpClient, HttpRequestBase postRequest, int counter) throws SiteHttpException {
        String responseBody = null;
        int statusCode = -1;
        try {
            final HttpResponse response = httpClient.execute(postRequest);
            responseBody = EntityUtils.toString(response.getEntity());
            statusCode = response.getStatusLine().getStatusCode();
            httpLogger.info("ID: {}, Status: {} \n Response: \n{}\n Not formatted response: '{}'\n", new Object[]{counter, statusCode, formatJson(responseBody), responseBody});
        } catch (IOException e) {
            throw createException(e, "Error in send request");
        } finally {
            try {
                if (postRequest instanceof HttpPost) {
                    EntityUtils.consume(((HttpPost) postRequest).getEntity());
                }
            } catch (IOException e) {
                logger.warn("Unable consume a entity", e);
            }

        }
        if (statusCode != 200) {
            String message = getErrorMessage(responseBody);
            if (!MascotUtils.isEmpty(message)) {
                throw createException(message);
            }
            throw createException("Error in send request. Bad status code: " + statusCode);
        }
        return responseBody;
    }

    private String getErrorMessage(String responseBody) {
        try {
            Map map = mapper.readValue(responseBody, Map.class);
            if (map.containsKey("details")) {
                String result = "";
                List<Map> list = (List) map.get("details");
                for (Map m : list) {
                    result += ("[" + m.get("message") + "] ");
                }
                return result;
            }
            return (String) map.get("message");
        } catch (Throwable e) {
            logger.error("Unable parse a response: '" + responseBody + "'", e);
            return null;
        }

    }

    private SiteHttpException createException(Exception e, String message) {
        logger.error(message, e);
        return new SiteHttpException(message, e);
    }

    private SiteHttpException createException(String message) {
        logger.error(message);
        return new SiteHttpException(message);
    }

    private HttpPost createHttpPost(String url, String requestBody) throws SiteHttpException {
        HttpPost postRequest = fillHeaders(new HttpPost(url));

        final StringEntity input;
        try {
            input = new StringEntity(requestBody);
        } catch (UnsupportedEncodingException e) {
            throw createException(e, "Unable encode a request (" + requestBody + ")");

        }
        input.setContentType("application/json");
        postRequest.setEntity(input);
        return postRequest;
    }

    private HttpGet createHttpGet(String url) throws SiteHttpException {
        return fillHeaders(new HttpGet(url));
    }

    private <A extends HttpRequestBase> A fillHeaders(A request) {
        request.setHeader("API-Version", "5.0.1");
        request.setHeader("Accept", "application/json");
        request.setHeader("Authorization", BasicScheme.authenticate(
                new org.apache.commons.httpclient.UsernamePasswordCredentials(settings.getToken(), settings.getSecret()), "UTF-8"));
        return request;
    }

    private String toJsonString(Object requestObject) throws SiteHttpException {
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestObject);
        } catch (Exception e) {
            throw createException(e, "Unable convert to JSON (" + requestObject + ")");
        }
    }

    private String formatJson(String json) throws SiteHttpException {
        try {
            final Object object = parseJson(json, Object.class);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (IOException e) {
            logger.warn("Unable format json: '" + json + "'", e);
            return json;
        } catch (Exception e) {
            logger.warn("Unable format json: '" + json + "'", e);
            return "UNABLE FORMAT JSON";
        }
    }


}

