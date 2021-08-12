package com.example.fetchsourcecode.model;

import java.util.List;

public class UrlForms {
    private List<UrlForm> urls;

    public UrlForms(List<UrlForm> urls) {
        this.urls = urls;
    }

    public void addUrlForm(UrlForm urlForm) {
        this.urls.add(urlForm);
    }

    public List<UrlForm> getUrls() {
        return urls;
    }

    public void setUrls(List<UrlForm> urls) {
        this.urls = urls;
    }
}
