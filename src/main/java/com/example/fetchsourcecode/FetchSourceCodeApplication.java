package com.example.fetchsourcecode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@SpringBootApplication
public class FetchSourceCodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(FetchSourceCodeApplication.class, args);
        openHomePage();
    }

    private static void openHomePage() {
        String homepage = "http://localhost:9099/welcome";
        if (Desktop.isDesktopSupported()){
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(homepage));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("xdg-open " + homepage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
