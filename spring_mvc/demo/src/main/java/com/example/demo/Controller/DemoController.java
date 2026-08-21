package com.example.demo.Controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/demo")
public class DemoController {
    // HttpServeletRequest & httpSession injection
    @GetMapping("/session-info")
    public ResponseEntity<String> getSessionInfo(HttpServletRequest request, HttpSession session) {
        String clientIp = request.getRemoteAddr();

        // get user session-info
        String userSession = (String) session.getAttribute("user");// cast Obj-String
        if (userSession == null) {
            session.setAttribute("user", "Gaurav");
            userSession = "Gaurav Thakare";
        }
        return ResponseEntity.ok("Client IP:" + clientIp + "\nUser Session:" + userSession);
    }

    // RequestParameter for extracting single value & Map(MultiMap)for multi-value
    @GetMapping("/search")
    public ResponseEntity<Map<String, String[]>> getallitems(@RequestParam Map<String, String[]> allparam) {

        // automatically maps all incoming query-parameter(multi-value)
        return ResponseEntity.ok(allparam);
    }

    // @PathVariable exract the specific resource from Url path structure(fetch)
    @GetMapping("/users/{id}")
    public ResponseEntity<String> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok("Fetched User with:" + id);
    }

    // PostMapping:create brand new user
    @PostMapping("/users")
    public ResponseEntity<String> createUser(@RequestBody Map<String, String> payload) {
        return ResponseEntity.ok("User created:" + payload.get("name"));
    }

    // PutMapping: replace existing stuff with new one
    @PutMapping("/users/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        return ResponseEntity.ok("Updated user Id:" + id + " With name:" + payload.get("name"));
    }

    // PatchMapping:Update exisitng
    @PatchMapping("/users/{id}")
    public ResponseEntity<String> patchUser(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        return ResponseEntity.ok("Patched user Id:" + id + "field" + payload.entrySet());
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        return ResponseEntity.ok("Delete user Id:" + id).noContent().build();
    }
}
