package com.mascot.server.beans.report;

import com.mascot.server.beans.AbstractMascotService;
import com.mascot.server.model.Role;
import com.mascot.server.model.User;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Nikolay on 12.10.2016.
 */
@Service(ReportService.NAME)
@Transactional(propagation = Propagation.REQUIRED)
public class ReportServiceImpl extends AbstractMascotService implements ReportService {
    @Override
    public byte[] usersReport() {
/*
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
*/
        logger.info("ReportServiceImpl: Success");
        final List<User> users = users();
        List<UserReportItem> data = users.stream().map(UserReportItem::create).collect(Collectors.toList());
        final Path compileFilePath = ReportCompiler.compileReport(MascotReport.USERS_REPORT);
        final JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(data);
        final JasperPrint print;
        try {
            print = JasperFillManager.fillReport(compileFilePath.toString(), new HashMap<String, Object>(), beanCollectionDataSource);
            final Path outputPath = compileFilePath.getParent().toAbsolutePath().resolve("users_report_" + System.currentTimeMillis() + ".pdf");
            JasperExportManager.exportReportToPdfFile(print, outputPath.toString());

            // PDF
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(print, out);
            return out.toByteArray();

/*
            // HTML
            final Path outputPath = compileFilePath.getParent().toAbsolutePath().resolve("users_report_.html");
            JasperExportManager.exportReportToHtmlFile(print, outputPath.toString());
            try {
                FileInputStream fileInputStream = new FileInputStream(outputPath.toFile());
                return IOUtils.toByteArray(fileInputStream);
            } catch (IOException e) {
                throw new RuntimeException("Unable create a report because not found generated html file: '" + outputPath.toAbsolutePath() + "");
            }
*/


/*
            final Path resolve = compileFilePath.getParent().toAbsolutePath().resolve("output-byte.pdf");
            try(final FileOutputStream outputStream = new FileOutputStream(resolve.toFile())) {
                outputStream.write(out.toByteArray());
            } catch (IOException e) {
                e.printStackTrace();
            }
*/


/*
            final Path resolve = compileFilePath.getParent().toAbsolutePath().resolve("output.pdf");
            try(final FileOutputStream outputStream = new FileOutputStream(resolve.toFile())) {
                JasperExportManager.exportReportToPdfStream(print, outputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
*/

//            throw new IllegalStateException("Success");
//            return Base64.encode(bytes);
        } catch (JRException e) {
            throw new IllegalStateException(e);
        }

    }

    private List<User> users() {
        final List<User> users = new ArrayList<User>(em.createQuery("select distinct e from User e join fetch e.roles").getResultList());
        for (int i = 0; i < 500; i++) {
            final User e = new User();
            e.setFullName("Полное имя A" + i);
            e.setLogin("Логин B" + i);
            e.setRoles(new HashSet<>());
            final Role role = new Role();
            role.setName("Слон");
            e.getRoles().add(role);
            users.add(e);
        }
        return users;
    }

    @Override
    public List<User> getUsers() {
        return users();
    }

}
