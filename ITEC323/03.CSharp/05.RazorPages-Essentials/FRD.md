# Functional Requirements Document (FRD)
## Razor Pages Essentials

**Version**: 1.0  
**Date**: February 2026  
**Target Framework**: .NET 8.0 / .NET 10.0

---

## Purpose

An educational web app teaching ASP.NET Core Razor Pages fundamentals: forms, validation, and navigation.

### What You'll Learn

- Form handling with Data Annotations validation
- Tag Helpers (`asp-for`, `asp-validation-for`)
- Post-Redirect-Get pattern
- TempData for page-to-page messages
- Model-View separation

---

## Features

### 1. Home Page
- Welcome message and project overview
- Navigation to Contact page

### 2. Contact Form
- Three fields: Name, Email, Message
- All fields required
- Email format validation
- Message length: 10-500 characters
- Error messages displayed inline

### 3. Validation
- **Server-side**: `[Required]`, `[EmailAddress]`, `[StringLength]`
- **Client-side**: jQuery validation for immediate feedback
- Form preserves values when validation fails

### 4. Success Page
- Confirmation message from TempData
- Options to submit another or go home
- Demonstrates Post-Redirect-Get pattern

### 5. Shared Layout
- Consistent header/footer on all pages
- Navigation menu (Home, Contact)
- Responsive design

---

## Technical Requirements

- Works with .NET 8.0 and .NET 10.0
- Runs on Windows, macOS, and Linux
- Works in modern browsers
- Clean, well-commented code for learning
- No external packages (uses ASP.NET Core built-ins)



---

## Success Criteria

✅ Application builds and runs without errors  
✅ Contact form validates correctly (client and server side)  
✅ Form redirects to Success page after valid submission  
✅ Refreshing Success page doesn't resubmit form (PRG pattern)  
✅ Code is well-documented for learning

---

## Not Included

- ❌ Database (data not saved)
- ❌ Email sending
- ❌ User authentication
- ❌ File uploads
- ❌ Unit tests

---

## Testing Guide

**Test 1: Valid Submission**
- Fill all fields correctly → Should redirect to Success page

**Test 2: Empty Fields**
- Leave fields empty → Should show error messages

**Test 3: Invalid Email**
- Enter "notemail" → Should show "Please enter a valid email address"

**Test 4: Short Message**
- Enter less than 10 characters → Should show message too short error

**Test 5: Refresh Success Page**
- Submit form, then refresh Success page → Should NOT resubmit form

---

**For detailed learning, see**: README.md, QUICKSTART.md, and docs/Key-Takeaways.md

- **Data Annotations**: Attributes used to specify validation rules and metadata (e.g., `[Required]`, `[EmailAddress]`)
- **Tag Helpers**: Server-side code that helps generate HTML in Razor files (e.g., `asp-for`, `asp-validation-for`)
- **Model Binding**: Automatic mapping of HTTP request data to C# objects
- **TempData**: A dictionary for storing data for exactly one subsequent HTTP request
- **Post-Redirect-Get (PRG)**: A web development pattern where after a POST, the server redirects to a GET request
- **ModelState**: Object that stores the state of model binding and validation
- **Razor Pages**: A page-based programming model in ASP.NET Core for building web UI
- **Page Model**: The code-behind class for a Razor Page (e.g., `IndexModel`)

