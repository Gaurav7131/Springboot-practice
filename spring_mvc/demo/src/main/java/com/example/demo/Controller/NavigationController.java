package com.example.demo.Controller;

import java.util.Map;
import org.springframework.stereotype.Controller; // ✅ Use @Controller instead of @RestController
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class NavigationController {

    // 1. Forward-Prefix: URL stays at /legacy-route, internal handoff to /new-route
    @GetMapping("/legacy-route")
    public String handleForward(Model model) {
        model.addAttribute("info", "Forwarded internally");
        return "forward:/new-route";
    }

    @GetMapping("/new-route")
    public String destinationRoute() {
        return "destination"; // Renders templates/destination.html
    }

    // 2. Redirect with Prefix and Query Parameter resolution
    @PostMapping("/process-route")
    public String redirectWithQuery(@RequestParam("username") String username, Model model) {
        model.addAttribute("name", username); // Appended as ?name=... to URL
        return "redirect:/welcome";
    }

    // 3. Redirect with Flash Attributes (PRG pattern)
    @PostMapping("/register-user")
    public String registerUser(
            @RequestParam("email") String email,
            RedirectAttributes redirectAttributes) {

        // FlashAttribute is stored in HttpSession and consumed after 1 GET request
        redirectAttributes.addFlashAttribute("successMessage", "Account Created Successfully for: " + email);

        // addAttribute adds a standard query parameter (?status=active)
        redirectAttributes.addAttribute("status", "active");

        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String showDashboard() {
        // Return view name directly so flash attributes are accessible in HTML
        return "dashboard"; // Renders templates/dashboard.html
    }

    // 4. Redirect via ModelAndView
    @GetMapping("/mav-redirect")
    public ModelAndView mavRedirect() {
        ModelAndView mav = new ModelAndView("redirect:/dashboard");
        mav.addObject("status", "from-mav");
        return mav;
    }

    // 5. Explicit RedirectView
    @GetMapping("/external-redirect")
    public RedirectView externalRedirect() {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("https://spring.io");
        redirectView.setContextRelative(false);
        return redirectView;
    }

    // 6. Map / ModelMap in GET endpoint
    @GetMapping("/welcome")
    public String welcomePage(
            @RequestParam(name = "name", required = false) String name,
            Map<String, Object> modelMap) {

        modelMap.put("greeting", "Welcome, " + (name != null ? name : "Guest"));
        return "welcome"; // Renders templates/welcome.html
    }
}