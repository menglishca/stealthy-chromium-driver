package ca.menglish.undetected;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Downloader {

    private static final String baseUrl = "https://chromedriver.storage.googleapis.com/96.0.4664.45/";

    public static DriverType getDriverType() {
        String osName = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);
        if (osName.startsWith("windows")) {
            return DriverType.WINDOWS;
        }
        else if (osName.startsWith("mac")) {
            String osArch = System.getProperty("os.arch").toLowerCase(Locale.ENGLISH);
            if (osArch.startsWith("x86")) {
                return DriverType.MAC_INTEL;
            }
            return DriverType.MAC_M1;
        }
        else {
            return DriverType.LINUX;
        }
    }

    public static String getDownloadUrl() {
        return getDownloadUrl(getDriverType());
    }

    public static String getDownloadUrl(DriverType driverType) {
        return baseUrl + driverType.getDriverPackageName();
    }

    public static File downloadChromedriver() throws IOException, InterruptedException {
        return downloadChromedriver(getDownloadUrl());
    }

    public static File downloadChromedriver(String downloadUrl) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<InputStream> downloadRequestResponse = httpClient.send(
                HttpRequest
                        .newBuilder()
                        .GET()
                .uri(URI.create(downloadUrl))
                .build(),
                HttpResponse.BodyHandlers.ofInputStream()
        );

        File chromeDriver = File.createTempFile("chromedriver", "");

        byte[] buffer = new byte[1024];
        ZipInputStream zipStream = new ZipInputStream(downloadRequestResponse.body());
        ZipEntry zipEntry = zipStream.getNextEntry();
        while (zipEntry != null) {
            if (zipEntry.isDirectory()) {
                continue;
            }

            if (!zipEntry.getName().startsWith("chromedriver")) {
                continue;
            }

            FileOutputStream chromeDriverOutStream = new FileOutputStream(chromeDriver);
            int length;
            while ((length = zipStream.read(buffer)) > 0) {
                chromeDriverOutStream.write(buffer, 0, length);
            }

            chromeDriverOutStream.close();
            break;
        }
        zipStream.closeEntry();
        zipStream.close();

        return chromeDriver;
    }

    @AllArgsConstructor
    @Getter
    public enum DriverType {
        MAC_INTEL("chromedriver_mac64.zip"),
        MAC_M1("chromedriver_mac64_m1.zip"),
        LINUX("chromedriver_linux64.zip"),
        WINDOWS("chromedriver_win64.zip"),
        ;

        private String driverPackageName;

    }
}
