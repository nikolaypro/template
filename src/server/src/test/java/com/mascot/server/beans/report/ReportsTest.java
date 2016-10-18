package com.mascot.server.beans.report;

import com.mascot.server.model.Role;
import com.mascot.server.model.User;
import junit.framework.Assert;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Nikolay on 13.10.2016.
 */
public class ReportsTest {

    private Path usersCompiledReportFile;

    @BeforeClass
    public void init() throws IOException {
        final Path sourceReportPath = Paths.get("src", "report", "src", "main", "resources").normalize().toAbsolutePath();
        final Path compiledReportPath = Paths.get("src", "server", "src", "test", "resources", "report").normalize().toAbsolutePath();
        Files.walkFileTree(sourceReportPath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(final Path dir,
                                                     final BasicFileAttributes attrs) throws IOException {
                Files.createDirectories(compiledReportPath.resolve(sourceReportPath.relativize(dir)));
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(final Path file,
                                             final BasicFileAttributes attrs) throws IOException {
                Files.copy(file, compiledReportPath.resolve(sourceReportPath.relativize(file)), StandardCopyOption.REPLACE_EXISTING);
                return FileVisitResult.CONTINUE;
            }
        });
        // Compile reports
        usersCompiledReportFile = ReportCompiler.compileReport(MascotReport.USERS_REPORT, compiledReportPath.toString(), compiledReportPath.toString());
    }

    @Test
    public void testUsersReportGeneration() throws JRException {
        final List<UserReportItem> data = Stream.of(
                UserReportItem.create(createUser("login-1", "full-name-1", "role-1-1", "role-1-2")),
                UserReportItem.create(createUser("login-2", "full-name-2", "role-2-1")),
                UserReportItem.create(createUser("login-3", "full-name-3", "role-3-1", "role-3-2", "role-3-3"))
        ).collect(Collectors.toList());
        final JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(data);
        final JasperPrint print = JasperFillManager.fillReport(usersCompiledReportFile.toString(),
                new HashMap<String, Object>(), beanCollectionDataSource);
        final Path outputPath = usersCompiledReportFile.getParent().toAbsolutePath().resolve("report.pdf");
        JasperExportManager.exportReportToPdfFile(print, outputPath.toString());
        Assert.assertTrue(outputPath.toFile().exists());
    }

    private User createUser(String login, String fullName, String... roles) {
        final User user = new User();
        user.setLogin(login);
        user.setFullName(fullName);
        user.setRoles(Arrays.asList(roles).
                stream().
                map(e -> {
                    Role r = new Role();
                    r.setName(e);
                    return r;
                }).
                collect(Collectors.toSet())
        );
        return user;
    }
}
