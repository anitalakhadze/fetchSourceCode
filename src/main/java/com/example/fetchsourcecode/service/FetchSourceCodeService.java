package com.example.fetchsourcecode.service;

import com.example.fetchsourcecode.model.CustomWebDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class FetchSourceCodeService {

    private final ConfigurableApplicationContext context;
    boolean programIsShutDown;

    @Autowired
    public FetchSourceCodeService(ConfigurableApplicationContext context) {
        this.context = context;
        this.programIsShutDown = false;
    }

    public void openBrowserInASeparateThread(List<String> urls) {
        MyRunnable myRunnable = new MyRunnable(urls);
        Thread myThread = new Thread(myRunnable);
        myThread.setDaemon(true);
        myThread.start();
    }

    public void openBrowser(List<String> urls) {
        System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver");

        List<CustomWebDriver> customWebDrivers = new ArrayList<>();
        populateWebDriversCollection(customWebDrivers, urls);


        customWebDrivers.parallelStream().forEach(customWebDriver -> {
            WebDriver webDriver = customWebDriver.getWebDriver();
            String url = customWebDriver.getUrl();
            String fileName = customWebDriver.getFilename();
            webDriver.get(url);
            try {
                while(!programIsShutDown) {
                    try (FileWriter fileWriter = new FileWriter(fileName, true)) {
                        TimeUnit.SECONDS.sleep(10);
                        String pageSource = webDriver.getPageSource();
                        fileWriter.write(pageSource);
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            } finally {
                webDriver.quit();
            }
        });
    }

    private void populateWebDriversCollection(List<CustomWebDriver> customWebDrivers, List<String> urls) {
        for (int i = 0; i < urls.size(); i++) {
            WebDriver webDriver = new FirefoxDriver();
            String url = urls.get(i);
            String fileName = String.format("/home/anita/folder/folder/file%s.txt", i + 1);
            customWebDrivers.add(new CustomWebDriver(webDriver, url, fileName));
        }
    }

    public void closeBrowser() throws InterruptedException {
        programIsShutDown = true;
        TimeUnit.SECONDS.sleep(10);
        int exitCode = SpringApplication.exit(context, () -> 0);
        context.close();
        System.exit(exitCode);
    }

    class MyRunnable implements Runnable {
        List<String> urls;

        public MyRunnable(List<String> urls) {
            this.urls = urls;
        }

        @Override
        public void run() {
            while(!programIsShutDown) {
                openBrowser(urls);
            }
        }
    }
}
