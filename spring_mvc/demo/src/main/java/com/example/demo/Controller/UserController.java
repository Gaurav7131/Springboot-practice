package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.Model.UserForm;

import jakarta.validation.Valid;

@Controller
public class UserController {
    // successful acknowlegment after submision:success.html root path:/
    @GetMapping("/")
    public String home() {
        return "redirect:/register";
    }

    // Display empty form data
    @GetMapping("/register")
    public String showForm(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "register";
    }

    // Process Submission & handle validation error
    @PostMapping("/register")
    public String processForm(
            // BindingResult must be immediately followed by Validated model object
            @Valid @ModelAttribute("userForm") UserForm userForm, BindingResult bindingResult, Model model) {

        // Data Binding Error Handling:preseve submitted values,bad i/p
        if (bindingResult.hasErrors()) {
            System.out.println("Validation Failed:");
            bindingResult.getFieldErrors().forEach(
                    error -> System.out.println("Field:" + error.getField() + ">" + error.getDefaultMessage()));
            return "register";// Re-render form view to show validation errors alongside entered values
        }
        System.out.println("Validation Successful:)");
        model.addAttribute("userForm", userForm);
        return "success";
    }

}
