package com.exist.project_plan.web;

import com.exist.project_plan.service.web.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class IndexController {

    IndexService indexService;

    @Autowired
    IndexController(IndexService indexService) {
        this.indexService = indexService;
    }
    @RequestMapping("/")
    public String index(Model model){
        indexService.loadIndex(model);
        return "index";
    }
}
