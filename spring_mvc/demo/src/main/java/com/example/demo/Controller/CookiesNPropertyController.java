package com.example.demo.Controller;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class CookiesNPropertyController {

    // Injecting values in spring env
    @Value("${app.theme.default:light}")
    private String defaultTheme;

    @Value("${app.max-login-attempts}")
    private int maxAttempts;

    // Setting cookies 1)HttpServletResponse 2)ResponseCookie builder
    @GetMapping("/set-cookie")
    public ResponseEntity<String> setCookie(HttpServletResponse response) {
        // Approach 1.standard Servlet Api(legacy) with HttpServletRespose
        Cookie servletCookie = new Cookie("user_theme", defaultTheme);
        servletCookie.setMaxAge(7 * 24 * 60 * 60);// 7 days
        servletCookie.setPath("/");
        servletCookie.setHttpOnly(false);
        response.addCookie(servletCookie);

        // Approach 2:ResponseCookie(Modern) with SetCookie
        ResponseCookie springCookie = ResponseCookie.from("session_mode", "active")
                // set secure(True) in prods with Https
                .httpOnly(true).secure(false).sameSite("Strict").maxAge(Duration.ofHours(1)).path("/").build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, springCookie.toString())
                .body("Cookie configures Successfully:" + maxAttempts);
    }

    // Reading Cookie:@CookieValue:declarative binding,type conversion without
    // manual iteration VS.HttpServletRequest.getCookie() leads to null
    // check,looping issue
    @GetMapping("/read-cookie")
    public ResponseEntity<String> readCookie(
            @CookieValue(name = "user_theme", defaultValue = "standard") String UserTheme,
            @CookieValue(name = "session_mode", required = false) String sessionMode) {

        return ResponseEntity.ok("Theme Cookie:" + UserTheme + "\nSession Mode:" + sessionMode);

    }

}
