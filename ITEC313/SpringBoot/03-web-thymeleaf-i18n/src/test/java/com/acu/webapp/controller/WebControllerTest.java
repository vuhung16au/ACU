package com.acu.webapp.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for WebController demonstrating @WebMvcTest with Thymeleaf
 * 
 * This test shows how to:
 * - Use @WebMvcTest for testing web layer with Thymeleaf
 * - Test page rendering and form handling
 * - Verify view names and model attributes
 * - Test form validation
 */
@WebMvcTest(WebController.class)
class WebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void home_ShouldReturnHomePage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attributeExists("currentTime"))
                .andExpect(model().attributeExists("welcomeMessage"))
                .andExpect(content().contentType("text/html;charset=UTF-8"));
    }

    @Test
    void about_ShouldReturnAboutPage() throws Exception {
        mockMvc.perform(get("/about"))
                .andExpect(status().isOk())
                .andExpect(view().name("about"))
                .andExpect(model().attribute("pageTitle", "About"))
                .andExpect(content().contentType("text/html;charset=UTF-8"));
    }

    @Test
    void contactForm_ShouldReturnContactPage() throws Exception {
        mockMvc.perform(get("/contact"))
                .andExpect(status().isOk())
                .andExpect(view().name("contact"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("languages"))
                .andExpect(content().contentType("text/html;charset=UTF-8"));
    }

    @Test
    void contactSubmit_WithValidData_ShouldRedirect() throws Exception {
        mockMvc.perform(post("/contact")
                .param("name", "John Doe")
                .param("email", "john@example.com")
                .param("message", "This is a test message with more than 10 characters")
                .param("language", "English"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/contact"))
                .andExpect(flash().attributeExists("successMessage"));
    }

    @Test
    void contactSubmit_WithInvalidData_ShouldReturnContactPage() throws Exception {
        mockMvc.perform(post("/contact")
                .param("name", "")  // Invalid: empty name
                .param("email", "invalid-email")  // Invalid: not a valid email
                .param("message", "Short")  // Invalid: too short
                .param("language", "English"))
                .andExpect(status().isOk())
                .andExpect(view().name("contact"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeExists("languages"));
    }

    @Test
    void languages_ShouldReturnLanguagesPage() throws Exception {
        mockMvc.perform(get("/languages"))
                .andExpect(status().isOk())
                .andExpect(view().name("languages"))
                .andExpect(model().attributeExists("availableLanguages"))
                .andExpect(model().attributeExists("currentTime"))
                .andExpect(content().contentType("text/html;charset=UTF-8"));
    }

    @Test
    void getCurrentTime_WithDefaultFormat_ShouldReturnTime() throws Exception {
        mockMvc.perform(get("/api/time"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=UTF-8"));
    }

    @Test
    void getCurrentTime_WithShortFormat_ShouldReturnShortTime() throws Exception {
        mockMvc.perform(get("/api/time")
                .param("format", "short"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=UTF-8"));
    }
}
