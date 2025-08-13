package com.acu.webapp.controller;

import com.acu.webapp.model.User;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

/**
 * Web controller for Thymeleaf templates and form handling
 * 
 * This controller demonstrates:
 * - @Controller annotation for web pages
 * - Model attribute binding
 * - Form validation with @Valid
 * - Redirect attributes for flash messages
 * - Thymeleaf template integration
 */
@Controller
public class WebController {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Home page - demonstrates basic Thymeleaf usage
     */
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("currentTime", LocalDateTime.now().format(formatter));
        model.addAttribute("welcomeMessage", "Welcome to Spring Boot Web App with Thymeleaf!");
        return "home";
    }

    /**
     * About page - demonstrates static content with i18n
     */
    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("pageTitle", "About");
        return "about";
    }

    /**
     * Contact form page - demonstrates form handling
     */
    @GetMapping("/contact")
    public String contactForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("languages", getAvailableLanguages());
        return "contact";
    }

    /**
     * Contact form submission - demonstrates form validation
     */
    @PostMapping("/contact")
    public String contactSubmit(@Valid @ModelAttribute User user, 
                               BindingResult bindingResult, 
                               Model model,
                               RedirectAttributes redirectAttributes) {
        
        if (bindingResult.hasErrors()) {
            // If validation fails, return to form with errors
            model.addAttribute("languages", getAvailableLanguages());
            return "contact";
        }

        // Form is valid, process the submission
        String successMessage = String.format("Thank you %s! Your message has been received.", user.getName());
        redirectAttributes.addFlashAttribute("successMessage", successMessage);
        
        // Log the submission (in a real app, you'd save to database)
        System.out.println("Form submitted: " + user);
        
        return "redirect:/contact";
    }

    /**
     * Language selection page - demonstrates i18n features
     */
    @GetMapping("/languages")
    public String languages(Model model) {
        model.addAttribute("availableLanguages", getAvailableLanguages());
        model.addAttribute("currentTime", LocalDateTime.now().format(formatter));
        return "languages";
    }

    /**
     * API endpoint for getting current time (demonstrates REST with web app)
     */
    @GetMapping("/api/time")
    public String getCurrentTime(@RequestParam(defaultValue = "default") String format) {
        LocalDateTime now = LocalDateTime.now();
        
        if ("short".equals(format)) {
            return now.format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm"));
        } else {
            return now.format(formatter);
        }
    }

    /**
     * Helper method to get available languages for the dropdown
     */
    private List<String> getAvailableLanguages() {
        return Arrays.asList("English", "Español", "Français", "Deutsch", "Italiano");
    }
}
