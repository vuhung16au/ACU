import java.io.*;
import java.util.*;

/**
 * Example2.java - Complex Object Serialization
 * 
 * This example demonstrates serialization of complex objects with nested structures,
 * collections, and custom serialization methods.
 * 
 * Key differences from Python:
 * - Java automatically handles nested object serialization
 * - Java collections are serializable if their elements are
 * - Custom serialization requires explicit methods
 * 
 * @author ITEC313 - Object-Oriented Programming
 */
public class Example2 {
    
    /**
     * Course class representing a university course
     */
    public static class Course implements Serializable {
        private static final long serialVersionUID = 1L;
        
        private String courseCode;
        private String courseName;
        private int credits;
        private List<String> prerequisites;
        
        public Course(String courseCode, String courseName, int credits) {
            this.courseCode = courseCode;
            this.courseName = courseName;
            this.credits = credits;
            this.prerequisites = new ArrayList<>();
        }
        
        public void addPrerequisite(String prereq) {
            prerequisites.add(prereq);
        }
        
        // Getters
        public String getCourseCode() { return courseCode; }
        public String getCourseName() { return courseName; }
        public int getCredits() { return credits; }
        public List<String> getPrerequisites() { return prerequisites; }
        
        @Override
        public String toString() {
            return String.format("Course{code='%s', name='%s', credits=%d, prereqs=%s}", 
                               courseCode, courseName, credits, prerequisites);
        }
    }
    
    /**
     * Enrollment class showing nested object serialization
     */
    public static class Enrollment implements Serializable {
        private static final long serialVersionUID = 1L;
        
        private Student student;
        private Course course;
        private String semester;
        private char grade;
        private Date enrollmentDate;
        
        public Enrollment(Student student, Course course, String semester) {
            this.student = student;
            this.course = course;
            this.semester = semester;
            this.grade = 'N'; // Not graded yet
            this.enrollmentDate = new Date();
        }
        
        public void setGrade(char grade) {
            this.grade = grade;
        }
        
        // Getters
        public Student getStudent() { return student; }
        public Course getCourse() { return course; }
        public String getSemester() { return semester; }
        public char getGrade() { return grade; }
        public Date getEnrollmentDate() { return enrollmentDate; }
        
        @Override
        public String toString() {
            return String.format("Enrollment{student=%s, course=%s, semester='%s', grade='%c', date=%s}", 
                               student.getName(), course.getCourseCode(), semester, grade, enrollmentDate);
        }
    }
    
    /**
     * Student class (reused from Example1 with enhancements)
     */
    public static class Student implements Serializable {
        private static final long serialVersionUID = 1L;
        
        private String name;
        private int studentId;
        private double gpa;
        private List<Enrollment> enrollments;
        private Map<String, String> contactInfo;
        
        public Student(String name, int studentId, double gpa) {
            this.name = name;
            this.studentId = studentId;
            this.gpa = gpa;
            this.enrollments = new ArrayList<>();
            this.contactInfo = new HashMap<>();
        }
        
        public void addEnrollment(Enrollment enrollment) {
            enrollments.add(enrollment);
        }
        
        public void addContactInfo(String type, String info) {
            contactInfo.put(type, info);
        }
        
        // Getters
        public String getName() { return name; }
        public int getStudentId() { return studentId; }
        public double getGpa() { return gpa; }
        public List<Enrollment> getEnrollments() { return enrollments; }
        public Map<String, String> getContactInfo() { return contactInfo; }
        
        @Override
        public String toString() {
            return String.format("Student{name='%s', id=%d, gpa=%.2f, enrollments=%d, contacts=%d}", 
                               name, studentId, gpa, enrollments.size(), contactInfo.size());
        }
    }
    
    /**
     * Demonstrates serializing complex nested objects
     */
    public static void demonstrateNestedObjects() {
        System.out.println("=== Complex Object Serialization ===");
        
        // Create courses
        Course javaCourse = new Course("CS101", "Introduction to Java", 3);
        javaCourse.addPrerequisite("CS100");
        
        Course dataStructures = new Course("CS201", "Data Structures", 4);
        dataStructures.addPrerequisite("CS101");
        dataStructures.addPrerequisite("MATH101");
        
        // Create student with contact info
        Student student = new Student("Jane Smith", 54321, 3.92);
        student.addContactInfo("email", "jane.smith@university.edu");
        student.addContactInfo("phone", "555-1234");
        student.addContactInfo("address", "123 University Ave");
        
        // Create enrollments
        Enrollment javaEnrollment = new Enrollment(student, javaCourse, "Fall 2024");
        javaEnrollment.setGrade('A');
        
        Enrollment dsEnrollment = new Enrollment(student, dataStructures, "Spring 2025");
        dsEnrollment.setGrade('B');
        
        // Add enrollments to student
        student.addEnrollment(javaEnrollment);
        student.addEnrollment(dsEnrollment);
        
        System.out.println("Original complex object:");
        System.out.println("  Student: " + student);
        System.out.println("  Enrollments:");
        for (Enrollment e : student.getEnrollments()) {
            System.out.println("    " + e);
        }
        
        // Serialize the complex object
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("data/complex_student.ser"))) {
            
            oos.writeObject(student);
            System.out.println("✓ Complex object serialized");
            
        } catch (IOException e) {
            System.err.println("✗ Complex serialization failed: " + e.getMessage());
        }
    }
    
    /**
     * Demonstrates deserializing complex nested objects
     */
    public static void demonstrateNestedDeserialization() {
        System.out.println("\n=== Complex Object Deserialization ===");
        
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("data/complex_student.ser"))) {
            
            Student student = (Student) ois.readObject();
            System.out.println("✓ Complex object deserialized:");
            System.out.println("  Student: " + student);
            System.out.println("  Contact Info: " + student.getContactInfo());
            System.out.println("  Enrollments:");
            for (Enrollment e : student.getEnrollments()) {
                System.out.println("    " + e);
            }
            
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("✗ Complex deserialization failed: " + e.getMessage());
        }
    }
    
    /**
     * Demonstrates serializing collections of complex objects
     */
    public static void demonstrateCollectionSerialization() {
        System.out.println("\n=== Collection of Complex Objects ===");
        
        List<Student> students = new ArrayList<>();
        
        // Create multiple students with enrollments
        Student alice = new Student("Alice Johnson", 1001, 3.8);
        alice.addContactInfo("email", "alice@university.edu");
        
        Student bob = new Student("Bob Wilson", 1002, 3.5);
        bob.addContactInfo("email", "bob@university.edu");
        
        // Create courses
        Course math = new Course("MATH101", "Calculus I", 4);
        Course physics = new Course("PHYS101", "Physics I", 4);
        
        // Create enrollments
        Enrollment aliceMath = new Enrollment(alice, math, "Fall 2024");
        aliceMath.setGrade('A');
        alice.addEnrollment(aliceMath);
        
        Enrollment bobPhysics = new Enrollment(bob, physics, "Fall 2024");
        bobPhysics.setGrade('B');
        bob.addEnrollment(bobPhysics);
        
        students.add(alice);
        students.add(bob);
        
        System.out.println("Original student collection:");
        for (Student s : students) {
            System.out.println("  " + s);
        }
        
        // Serialize the collection
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("data/students_collection.ser"))) {
            
            oos.writeObject(students);
            System.out.println("✓ Student collection serialized");
            
        } catch (IOException e) {
            System.err.println("✗ Collection serialization failed: " + e.getMessage());
        }
        
        // Deserialize the collection
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("data/students_collection.ser"))) {
            
            @SuppressWarnings("unchecked")
            List<Student> restoredStudents = (List<Student>) ois.readObject();
            System.out.println("✓ Student collection deserialized:");
            for (Student s : restoredStudents) {
                System.out.println("  " + s);
            }
            
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("✗ Collection deserialization failed: " + e.getMessage());
        }
    }
    
    /**
     * Demonstrates custom serialization with validation
     */
    public static class ValidatedStudent implements Serializable {
        private static final long serialVersionUID = 1L;
        
        private String name;
        private int studentId;
        private double gpa;
        private transient boolean isValid; // Computed field
        
        public ValidatedStudent(String name, int studentId, double gpa) {
            this.name = name;
            this.studentId = studentId;
            this.gpa = gpa;
            validateStudent();
        }
        
        private void validateStudent() {
            isValid = name != null && !name.trim().isEmpty() && 
                     studentId > 0 && gpa >= 0.0 && gpa <= 4.0;
        }
        
        // Custom serialization
        private void writeObject(ObjectOutputStream out) throws IOException {
            System.out.println("  Custom serialization: validating before writing");
            validateStudent();
            if (!isValid) {
                throw new IOException("Student data is invalid");
            }
            out.defaultWriteObject();
        }
        
        // Custom deserialization
        private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
            System.out.println("  Custom deserialization: validating after reading");
            in.defaultReadObject();
            validateStudent();
            if (!isValid) {
                throw new IOException("Deserialized student data is invalid");
            }
        }
        
        public boolean isValid() { return isValid; }
        
        @Override
        public String toString() {
            return String.format("ValidatedStudent{name='%s', id=%d, gpa=%.2f, valid=%s}", 
                               name, studentId, gpa, isValid);
        }
    }
    
    /**
     * Demonstrates custom serialization with validation
     */
    public static void demonstrateCustomValidation() {
        System.out.println("\n=== Custom Serialization with Validation ===");
        
        ValidatedStudent validStudent = new ValidatedStudent("Valid Student", 12345, 3.5);
        System.out.println("Valid student: " + validStudent);
        
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("data/validated_student.ser"))) {
            
            oos.writeObject(validStudent);
            System.out.println("✓ Validated student serialized");
            
        } catch (IOException e) {
            System.err.println("✗ Validation serialization failed: " + e.getMessage());
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("data/validated_student.ser"))) {
            
            ValidatedStudent restored = (ValidatedStudent) ois.readObject();
            System.out.println("✓ Validated student deserialized: " + restored);
            
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("✗ Validation deserialization failed: " + e.getMessage());
        }
    }
    
    /**
     * Main method to run the example
     */
    public static void main(String[] args) {
        System.out.println("📚 Example 2: Complex Object Serialization");
        System.out.println("===========================================");
        
        // Create data directory if it doesn't exist
        new File("data").mkdirs();
        
        demonstrateNestedObjects();
        demonstrateNestedDeserialization();
        demonstrateCollectionSerialization();
        demonstrateCustomValidation();
        
        System.out.println("\n✅ Example 2 complete!");
    }
} 