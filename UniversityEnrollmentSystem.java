import java.util.HashMap;
import java.util.Scanner;

class CourseFullException extends Exception {
    public CourseFullException(String message) {
        super(message);
    }
}

class PrerequisiteNotMetException extends Exception {
    public PrerequisiteNotMetException(String message) {
        super(message);
    }
}

class Course {
    private String name;
    private int capacity;
    private String prerequisite;
    private int enrolledStudents;

    public Course(String name, int capacity, String prerequisite) {
        this.name = name;
        this.capacity = capacity;
        this.prerequisite = prerequisite;
        this.enrolledStudents = 0;
    }

    public String getName() {
        return name;
    }

    public String getPrerequisite() {
        return prerequisite;
    }

    public void enrollStudent(boolean prerequisiteCompleted) throws CourseFullException, PrerequisiteNotMetException {
        if (enrolledStudents >= capacity) {
            throw new CourseFullException("Error: Course " + name + " is full.");
        }
        if (!prerequisiteCompleted && prerequisite != null) {
            throw new PrerequisiteNotMetException("Error: Complete " + prerequisite + " before enrolling in " + name + ".");
        }
        enrolledStudents++;
        System.out.println("Successfully enrolled in " + name + ".");
    }
}

public class UniversityEnrollmentSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        HashMap<String, Boolean> completedCourses = new HashMap<>();
        completedCourses.put("Core Java", false);
        
        Course advancedJava = new Course("Advanced Java", 2, "Core Java");
        Course dataStructures = new Course("Data Structures", 3, null);
        Course algorithms = new Course("Algorithms", 2, "Data Structures");
        
        HashMap<String, Course> courses = new HashMap<>();
        courses.put("Advanced Java", advancedJava);
        courses.put("Data Structures", dataStructures);
        courses.put("Algorithms", algorithms);
        
        System.out.println("Available courses:");
        for (String course : courses.keySet()) {
            System.out.println("- " + course);
        }
        
        try {
            System.out.print("Enroll in Course: ");
            String courseName = scanner.nextLine();
            
            if (courses.containsKey(courseName)) {
                Course selectedCourse = courses.get(courseName);
                boolean prerequisiteCompleted = completedCourses.getOrDefault(selectedCourse.getPrerequisite(), true);
                selectedCourse.enrollStudent(prerequisiteCompleted);
            } else {
                System.out.println("Error: Course not found.");
            }
        } catch (CourseFullException | PrerequisiteNotMetException e) {
            System.out.println(e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
