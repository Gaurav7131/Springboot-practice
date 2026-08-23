package com.example.demo.Controller;

import java.util.Locale;
import java.util.Map;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
    private final MessageSource messageSource;

    // constructor
    public GreetingController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping("/greet")
    public ResponseEntity<Map<String, String>> greetuser(@RequestParam(defaultValue = "Gaurav") String name) {
        // Retrieve current locale
        Locale currentLocale = LocaleContextHolder.getLocale();// ThreadLocal

        // Resolve message with parameter substitution
        String localizedMsg = messageSource.getMessage(
                "greeting.welcome", new Object[] { name }, currentLocale);// must match with .properties files

        return ResponseEntity.ok(Map.of("locale", currentLocale.toString(), "message", localizedMsg));
    }
}
