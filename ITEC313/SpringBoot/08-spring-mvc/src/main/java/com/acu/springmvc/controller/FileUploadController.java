package com.acu.springmvc.controller;

import com.acu.springmvc.model.UploadedFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class FileUploadController {

    private static final String UPLOAD_DIR = "uploads";
    private static final List<UploadedFile> uploadedFiles = new ArrayList<>();

    @GetMapping("/upload")
    public String uploadForm(Model model) {
        model.addAttribute("uploadedFiles", uploadedFiles);
        return "upload";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                 RedirectAttributes redirectAttributes) {
        
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Please select a file to upload.");
            return "redirect:/upload";
        }

        try {
            // Create upload directory if it doesn't exist
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generate unique filename
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String storedFilename = UUID.randomUUID().toString() + fileExtension;

            // Save file
            Path filePath = uploadPath.resolve(storedFilename);
            Files.copy(file.getInputStream(), filePath);

            // Create uploaded file record
            UploadedFile uploadedFile = new UploadedFile(
                originalFilename,
                storedFilename,
                file.getSize(),
                file.getContentType()
            );
            uploadedFiles.add(uploadedFile);

            redirectAttributes.addFlashAttribute("successMessage", 
                "File '" + originalFilename + "' uploaded successfully!");

        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Failed to upload file: " + e.getMessage());
        }

        return "redirect:/upload";
    }

    @GetMapping("/download/{filename}")
    public String downloadFile(@PathVariable String filename, RedirectAttributes redirectAttributes) {
        // In a real application, you would implement actual file download
        redirectAttributes.addFlashAttribute("infoMessage", 
            "Download functionality would be implemented here for file: " + filename);
        return "redirect:/upload";
    }

    @PostMapping("/upload/clear")
    public String clearFiles(RedirectAttributes redirectAttributes) {
        uploadedFiles.clear();
        redirectAttributes.addFlashAttribute("infoMessage", "All uploaded files cleared.");
        return "redirect:/upload";
    }
}
