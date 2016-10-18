package com.mascot.server.beans.report;

import com.mascot.server.common.ServerUtils;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;

import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Nikolay on 12.10.2016.
 */
public class ReportCompiler {

    private static String getReportPath() {
        return Paths.get(ServerUtils.getConfPath(), "reports").toString();
    }

    public static Path compileReport(MascotReport report) {
        return compileReport(report, getReportPath(), getReportPath());
    }

    public static Path compileReport(MascotReport report, String sourceDirectory, String compileDirectory) {
        final Path fileReport = Paths.get(sourceDirectory, report.getSubPackageName(), report.getReportName() + ".jrxml");
        final Path compiledReport = getCompiledReportPath(report, compileDirectory);
        final File compiledFile = compiledReport.toFile();
        deleteIfExists(compiledFile);
        createFile(compiledFile);
        try (OutputStream outputStream = new FileOutputStream(compiledFile);
             InputStream inputStream = getSourceUrl(fileReport).openStream()) {
            JasperCompileManager.compileReportToStream(inputStream, outputStream);
        } catch (JRException e) {
            throw new IllegalStateException(
                    String.format("report-compile: incorrect source report syntax for : '%s'", fileReport.toAbsolutePath()),
                    e
            );
        } catch (IOException e) {
            throw new IllegalStateException(
                    String.format("report-compile: bad source stream for : %s", fileReport.toAbsolutePath()),
                    e
            );
        } catch (Exception e) {
            throw new IllegalStateException("unknown exception : ", e);
        }
        return compiledReport;
    }


    public static Path getCompiledReportPath(MascotReport report, String compileDirectory) {
        return Paths.get(compileDirectory, report.getSubPackageName(), report.getReportName() + "_compiled.jasper");
    }

    private static URL getSourceUrl(Path fileReport) {
        try {
            return fileReport.toUri().toURL();
        } catch (IOException e) {
            throw new IllegalStateException(
                    String.format("report-compile: incorrect source report: %s", fileReport.toAbsolutePath()),
                    e
            );
        }
    }

    private static void createFile(File compiledFile) {
        try {
            if (!compiledFile.createNewFile()) {
                throw new IllegalStateException(
                        String.format("Unable create a file '%s' report because createNewFile return false",
                                compiledFile.getAbsolutePath()));
            }
        } catch (IOException e) {
            throw new IllegalStateException(
                    String.format("report-compile: incorrect destination file for compile report: '%s'", compiledFile.getAbsolutePath()),
                    e
            );
        }
    }

    private static void deleteIfExists(File compiledFile) {
        if (compiledFile.exists()) {
            if (!compiledFile.delete()) {
                throw new IllegalStateException(
                        String.format("Unable delete a file '%s' report because delete return false",
                                compiledFile.getAbsolutePath()));
            }
        }
    }

}
