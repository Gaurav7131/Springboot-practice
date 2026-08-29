package com.example.demo.Controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

record UserDto(Long id, String name, String email) {
}

@RestController
@RequestMapping("/api/v1/restusers")
public class UserRestController {
    // PathVariable:extract specific resource
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getallUser(@PathVariable Long id) {
        UserDto user = new UserDto(id, "Gaurav", "gaurav@gmail.com");
        return ResponseEntity.ok(user);
    }

    // RequestParameter:extract query parameter(single,Default value)
    @GetMapping("/search")
    public ResponseEntity<String> searchUser(@RequestParam(name = "name", defaultValue = "Guest") String name,
            @RequestParam(name = "role", required = false) String role) {
        return ResponseEntity.ok("Searching for user:" + name + "with role:" + role);
    }

    // RequestBody,@ResponseStatus
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Status code:201 declaratively created
    public UserDto createUser(@RequestBody UserDto input)// bind java object to request body(json/xml)
    {
        return new UserDto(101L, "Thakare", "Thakare@gmail.com");
    }

    // RequestParam
    @GetMapping("/headers")
    public ResponseEntity<String> inspectHeaders(
            @RequestHeader(value = "User-Agent", defaultValue = "Unknown") String userAgent,
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @RequestHeader HttpHeaders allHeaders) {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("X-Custom-Server-Header", "SpringRestApi-V1");

        String body = "Client Agent:" + userAgent + "with authorization:"
                + (authHeader != null ? "Present" : "Missing");
        return new ResponseEntity<>(body, responseHeaders, HttpStatus.OK);
    }

}
