package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;//must have ui.model package
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller // returns logical view_name resolve by ViewNameResolver
public class HelloController {
    @GetMapping("/hello")
    public String sayHello(@RequestParam(name = "name", required = false, defaultValue = "Gaurav") String name,
            Model model) {
        // add data to the Model tobe rendered by the view
        model.addAttribute("message", "Hello " + name + "! Welcome to Spring MVC.");

        // return logical view name:/WEB-INF/views/hello.jsp
        return "hello";

    }

}
