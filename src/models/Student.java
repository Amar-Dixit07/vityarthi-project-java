package src.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a student user in the system.
 */
public class Student {
    private String studentId;
    private String name;
    private String password;
    private List<String> registeredCourseIds;

    public Student(String studentId, String name, String password) {
        this.studentId = studentId;
        this.name = name;
        this.password = password;
        this.registeredCourseIds = new ArrayList<>();
    }

    public String getStudentId() { return studentId; }
    public String getName() { return name; }
    public List<String> getRegisteredCourseIds() { return registeredCourseIds; }

    public boolean validatePassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    public void addCourse(String courseId) {
        this.registeredCourseIds.add(courseId);
    }

    public void removeCourse(String courseId) {
        this.registeredCourseIds.remove(courseId);
    }

    public String toCSV() {
        String courses = String.join(";", registeredCourseIds);
        return studentId + "," + name + "," + password + "," + courses;
    }

    public static Student fromCSV(String csvLine) {
        String[] parts = csvLine.split(",", -1);
        if (parts.length < 3) return null;

        Student s = new Student(parts[0], parts[1], parts[2]);
        
        if (parts.length > 3 && !parts[3].isEmpty()) {
            String[] courseIds = parts[3].split(";");
            for (String cid : courseIds) {
                s.addCourse(cid);
            }
        }
        return s;
    }
}