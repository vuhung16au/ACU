// StringAlgorithms.java
// Main source file for String-Algorithms project
// For ITEC313 - Object-Oriented Programming
// Demonstrates common string algorithms in Java with Python comparisons

public class StringAlgorithms {
    // Reverse a string
    public static String reverse(String s) {
        // Java: Use StringBuilder for reversal
        return new StringBuilder(s).reverse().toString();
        // Python: s[::-1]
    }

    // Check if a string is a palindrome
    public static boolean isPalindrome(String s) {
        String cleaned = s.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        return cleaned.equals(reverse(cleaned));
        // Python: cleaned = re.sub(r'[^a-zA-Z0-9]', '', s).lower(); cleaned == cleaned[::-1]
    }

    // Search for a substring
    public static boolean containsSubstring(String s, String sub) {
        return s.contains(sub);
        // Python: sub in s
    }

    // Replace all occurrences of a substring
    public static String replaceSubstring(String s, String target, String replacement) {
        return s.replace(target, replacement);
        // Python: s.replace(target, replacement)
    }

    // Split a string by spaces
    public static String[] splitBySpace(String s) {
        return s.split(" ");
        // Python: s.split(' ')
    }

    // Join an array of strings with a delimiter
    public static String joinWithDash(String[] arr) {
        return String.join("-", arr);
        // Python: '-'.join(arr)
    }

    public static void main(String[] args) {
        String example = "Java is fun and powerful";
        System.out.println("Original: " + example);
        System.out.println("Reversed: " + reverse(example));
        System.out.println("Is palindrome: " + isPalindrome("A man, a plan, a canal: Panama"));
        System.out.println("Contains 'fun': " + containsSubstring(example, "fun"));
        System.out.println("Replace 'fun' with 'awesome': " + replaceSubstring(example, "fun", "awesome"));
        String[] words = splitBySpace(example);
        System.out.print("Split by space: ");
        for (String word : words) {
            System.out.print(word + " | ");
        }
        System.out.println();
        System.out.println("Join with dash: " + joinWithDash(words));
    }
} 