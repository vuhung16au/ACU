/**
 * String Templates Demo - Java 25 Feature
 * 
 * String Templates (JEP 430) provide a concise and safe way to compose strings
 * with embedded expressions. This feature was in preview in earlier versions
 * and is finalized in Java 25.
 * 
 * Course: ITEC313 - Advanced Programming Concepts
 * @author Java 25 Features Demo
 * Date: 2025
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class StringTemplatesDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Java 25 String Templates Demo ===\n");
        
        // Note: Since Java 25 isn't available yet, these examples show
        // the intended syntax and simulate the behavior
        
        basicStringTemplates();
        htmlTemplateExample();
        sqlTemplateExample();
        jsonTemplateExample();
        performanceComparison();
    }
    
    /**
     * Demonstrates basic string template usage
     * In Java 25, you would use STR template processor for simple interpolation
     */
    private static void basicStringTemplates() {
        System.out.println("1. Basic String Templates:");
        
        String name = "Java Developer";
        int version = 25;
        double rating = 4.8;
        
        // Traditional string concatenation (current approach)
        String traditional = "Hello " + name + "! Welcome to Java " + version + 
                           " with rating " + rating + "/5.0";
        System.out.println("Traditional: " + traditional);
        
        // String.format approach (current approach)
        String formatted = String.format("Hello %s! Welcome to Java %d with rating %.1f/5.0", 
                                        name, version, rating);
        System.out.println("Formatted:   " + formatted);
        
        // Java 25 String Template (simulated - actual syntax would be):
        // String template = STR."Hello \{name}! Welcome to Java \{version} with rating \{rating}/5.0";
        String template = simulateStringTemplate("Hello \\{name}! Welcome to Java \\{version} with rating \\{rating}/5.0",
                                                name, version, rating);
        System.out.println("Template:    " + template);
        
        System.out.println();
    }
    
    /**
     * Demonstrates HTML template processing with type safety
     */
    private static void htmlTemplateExample() {
        System.out.println("2. HTML Template Example:");
        
        String title = "Java 25 Features";
        String content = "String Templates are now finalized!";
        List<String> items = List.of("Type Safety", "Performance", "Readability");
        
        // Traditional approach - error-prone and verbose
        StringBuilder html = new StringBuilder();
        html.append("<html><head><title>").append(title).append("</title></head>");
        html.append("<body><h1>").append(title).append("</h1>");
        html.append("<p>").append(content).append("</p>");
        html.append("<ul>");
        for (String item : items) {
            html.append("<li>").append(item).append("</li>");
        }
        html.append("</ul></body></html>");
        
        System.out.println("Traditional HTML:\n" + html.toString());
        
        // Java 25 approach (simulated):
        // In actual Java 25, you would use a custom template processor for HTML
        // String htmlTemplate = HTML."""
        //     <html>
        //         <head><title>\{title}</title></head>
        //         <body>
        //             <h1>\{title}</h1>
        //             <p>\{content}</p>
        //             <ul>\{items.stream().map(item -> STR."<li>\{item}</li>").collect(joining())}</ul>
        //         </body>
        //     </html>
        // """;
        
        String itemsList = items.stream()
                               .map(item -> "<li>" + item + "</li>")
                               .reduce("", String::concat);
        
        String htmlTemplate = String.format("""
            <html>
                <head><title>%s</title></head>
                <body>
                    <h1>%s</h1>
                    <p>%s</p>
                    <ul>%s</ul>
                </body>
            </html>
            """, title, content, content, itemsList);
        
        System.out.println("Template HTML:\n" + htmlTemplate);
        System.out.println();
    }
    
    /**
     * Demonstrates SQL template with parameter binding safety
     */
    private static void sqlTemplateExample() {
        System.out.println("3. SQL Template Example:");
        
        String tableName = "users";
        String userEmail = "user@example.com";
        int minAge = 21;
        
        // Traditional approach - SQL injection risk
        String traditionalSql = "SELECT * FROM " + tableName + 
                               " WHERE email = '" + userEmail + "' AND age >= " + minAge;
        System.out.println("Traditional SQL: " + traditionalSql);
        
        // Java 25 SQL Template (simulated):
        // In actual Java 25, custom template processors would provide SQL safety
        // var query = SQL."SELECT * FROM \{tableName} WHERE email = \{userEmail} AND age >= \{minAge}";
        
        String safeSql = simulateSqlTemplate("SELECT * FROM ? WHERE email = ? AND age >= ?",
                                           tableName, userEmail, minAge);
        System.out.println("Safe SQL Template: " + safeSql);
        System.out.println();
    }
    
    /**
     * Demonstrates JSON template processing
     */
    private static void jsonTemplateExample() {
        System.out.println("4. JSON Template Example:");
        
        String userId = "12345";
        String userName = "John Doe";
        LocalDateTime timestamp = LocalDateTime.now();
        String formattedTime = timestamp.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        
        // Traditional JSON building
        String traditionalJson = "{" +
            "\"userId\":\"" + userId + "\"," +
            "\"name\":\"" + userName + "\"," +
            "\"timestamp\":\"" + formattedTime + "\"," +
            "\"active\":true" +
            "}";
        System.out.println("Traditional JSON: " + traditionalJson);
        
        // Java 25 JSON Template (simulated):
        // var json = JSON."""
        //     {
        //         "userId": "\{userId}",
        //         "name": "\{userName}",
        //         "timestamp": "\{timestamp}",
        //         "active": true
        //     }
        // """;
        
        String jsonTemplate = String.format("""
            {
                "userId": "%s",
                "name": "%s", 
                "timestamp": "%s",
                "active": true
            }
            """, userId, userName, formattedTime);
        
        System.out.println("JSON Template:\n" + jsonTemplate);
        System.out.println();
    }
    
    /**
     * Demonstrates performance benefits of string templates
     */
    private static void performanceComparison() {
        System.out.println("5. Performance Comparison:");
        
        String name = "Performance Test";
        int iterations = 1000000;
        
        // Measure traditional concatenation
        long startTime = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            String result = "Hello " + name + " iteration " + i;
        }
        long concatTime = System.nanoTime() - startTime;
        
        // Measure StringBuilder approach
        startTime = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append("Hello ").append(name).append(" iteration ").append(i);
            String result = sb.toString();
        }
        long sbTime = System.nanoTime() - startTime;
        
        // Measure String.format approach
        startTime = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            String result = String.format("Hello %s iteration %d", name, i);
        }
        long formatTime = System.nanoTime() - startTime;
        
        System.out.printf("String concatenation: %.2f ms%n", concatTime / 1_000_000.0);
        System.out.printf("StringBuilder:        %.2f ms%n", sbTime / 1_000_000.0);
        System.out.printf("String.format:        %.2f ms%n", formatTime / 1_000_000.0);
        System.out.println("Note: String Templates in Java 25 would be optimized for performance");
        System.out.println("      and compile to efficient bytecode, likely faster than concatenation");
    }
    
    /**
     * Simulates string template behavior for demonstration
     */
    private static String simulateStringTemplate(String template, Object... values) {
        // This is a simplified simulation of how string templates would work
        String result = template.replace("\\{name}", String.valueOf(values[0]))
                               .replace("\\{version}", String.valueOf(values[1]))
                               .replace("\\{rating}", String.valueOf(values[2]));
        return result;
    }
    
    /**
     * Simulates SQL template with parameter binding for safety
     */
    private static String simulateSqlTemplate(String template, Object... values) {
        // In real Java 25, this would create parameterized queries for safety
        String result = template;
        for (Object value : values) {
            result = result.replaceFirst("\\?", 
                value instanceof String ? "'" + value + "'" : String.valueOf(value));
        }
        return result + " -- (Parameters would be safely bound in real implementation)";
    }
}