package cz.itnetwork.springblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomePageController {

    @GetMapping
    public String renderIndex() {
        return "pages/home/indexMainPage";
    }

    @GetMapping("contact")
    public String renderContact() {
        return "pages/home/contact";
    }

    @GetMapping("references")
    public String renderReferences() {
        return "pages/home/references";
    }

    @GetMapping("skills")
    public String renderSkills() {
        return "pages/home/skills";
    }
}
