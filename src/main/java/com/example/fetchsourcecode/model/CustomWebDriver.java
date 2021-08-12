package com.example.fetchsourcecode.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.openqa.selenium.WebDriver;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomWebDriver {

    WebDriver webDriver;
    String url;
    String filename;

}
