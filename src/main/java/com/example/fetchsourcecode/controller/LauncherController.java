package com.example.fetchsourcecode.controller;

import com.example.fetchsourcecode.model.NumOfWindows;
import com.example.fetchsourcecode.model.UrlForm;
import com.example.fetchsourcecode.model.UrlForms;
import com.example.fetchsourcecode.service.FetchSourceCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LauncherController {
    private FetchSourceCodeService fetchSourceCodeService;

    @Autowired
    public LauncherController(FetchSourceCodeService fetchSourceCodeService) {
        this.fetchSourceCodeService = fetchSourceCodeService;
    }

    @GetMapping("welcome")
    public String home() {
        return "home";
    }

    @GetMapping("/window")
    public String submitNumOfWindows(@Valid Model model) {
        model.addAttribute("numOfWindows", new NumOfWindows());
        return "window";
    }

    @PostMapping("/url")
    public String submitUrls(@ModelAttribute NumOfWindows numOfWindows, Model model) {
        UrlForms urlForms = new UrlForms(new ArrayList<>());
        for (int i = 0; i < numOfWindows.getNumOfWindows(); i++) {
            urlForms.addUrlForm(new UrlForm(""));
        }
        model.addAttribute("urlForms", urlForms);
        return "url";
    }

    @PostMapping("/process")
    public String inProcess(@ModelAttribute UrlForms urlForms, Model model) {
        List<String> urls = new ArrayList<>();
        for (UrlForm form : urlForms.getUrls()) {
            urls.add(form.url);
        }
        fetchSourceCodeService.openBrowserInASeparateThread(urls);
        model.addAttribute("urls", urls);
        return "process";
    }

    @GetMapping("/close")
    public String close() throws InterruptedException {
        // close selenium window
        fetchSourceCodeService.closeBrowser();
        return "redirect:welcome";
    }
}
