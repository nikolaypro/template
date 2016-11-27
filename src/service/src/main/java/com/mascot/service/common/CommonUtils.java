package com.mascot.service.common;

import com.mascot.common.MascotUtils;
import com.mascot.server.common.ServerUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * Created by Николай on 20.11.2016.
 */
public class CommonUtils {
    public static String formatJson(String json) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final Object object = mapper.readValue(json, Object.class);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (Exception e) {
            return json;
        }
    }

    public static void checkRegistered() {
        String zipFile = ServerUtils.getConfPath() + "/../lib/tomcat-lic.jar";
        final Logger logger = Logger.getLogger(CommonUtils.class);
        LocalDate date = null;
        try(FileInputStream fis = new FileInputStream(zipFile);
            ZipInputStream zis = new
                    ZipInputStream(new BufferedInputStream(fis))) {
//            ZipFile file = new ZipFile(zipFile);
            ZipEntry nextEntry = zis.getNextEntry();
            byte data[] = new byte[10];
            zis.read(data, 0, 10);
            date = LocalDate.parse(new String(data), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception e) {
            logger.info("Not found licence or it is incorrect");
            throw new AuthenticationCredentialsNotFoundException("You use a trial version of system");
        }
        if (LocalDate.now().isAfter(date)) {
            throw new AuthenticationCredentialsNotFoundException("You use a trial version of system");
//                throw new IllegalStateException("You use a trial version of system");
        }
    }
}
