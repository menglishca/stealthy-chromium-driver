package ca.menglish.undetected;

import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String... args) throws IOException, InterruptedException {
        File chromedriver = Downloader.downloadChromedriver();
        ExecutablePatcher.patchExecutable(chromedriver);

        ChromeDriver chromeDriver = new ChromeDriver();

        System.out.println(chromedriver);
    }
}
