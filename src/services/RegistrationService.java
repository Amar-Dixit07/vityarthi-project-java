package src.services;

import src.models.Course;
import src.models.Student;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Layer: Handles business logic AND File Persistence.
 */
public class RegistrationService {
    private List<Course> courseCatalog;
    private List<Student> studentDirectory;
    
    private final String COURSE_FILE = "courses.txt";
    private final String STUDENT_FILE = "students.txt";

    public RegistrationService() {
        this.courseCatalog = new ArrayList<>();
        this.studentDirectory = new ArrayList<>();
        loadData();
    }


    public Student authenticate(String studentId, String password) {
        Optional<Student> student = studentDirectory.stream()
                .filter(s -> s.getStudentId().equals(studentId))
                .findFirst();

        if (student.isPresent() && student.get().validatePassword(password)) {
            return student.get();
        }
        return null;
    }

    public void registerNewStudent(String id, String name, String password) throws IllegalStateException {
        
        boolean exists = studentDirectory.stream().anyMatch(s -> s.getStudentId().equals(id));
        if (exists) {
            throw new IllegalStateException("Student ID already exists.");
        }
        Student newStudent = new Student(id, name, password);
        studentDirectory.add(newStudent);
        saveStudents();
    }


    public List<Course> getAllCourses() {
        return courseCatalog;
    }

    public List<Course> getStudentCourses(Student student) {
        List<Course> myCourses = new ArrayList<>();
        for (String registeredId : student.getRegisteredCourseIds()) {
            Course c = findCourseById(registeredId);
            if (c != null) myCourses.add(c);
        }
        return myCourses;
    }

    public void register(Student student, String courseId) throws IllegalStateException {
        Course course = findCourseById(courseId);

        if (course == null) throw new IllegalArgumentException("Course ID not found.");
        if (student.getRegisteredCourseIds().contains(courseId)) throw new IllegalStateException("Already enrolled.");
        if (course.isFull()) throw new IllegalStateException("Course is full.");

        course.incrementEnrollment();
        student.addCourse(courseId);
        
        saveAll();
    }

    public void withdraw(Student student, String courseId) throws IllegalArgumentException {
        Course course = findCourseById(courseId);
        
        if (!student.getRegisteredCourseIds().contains(courseId)) {
            throw new IllegalArgumentException("You are not enrolled in this course.");
        }

        if (course != null) course.decrementEnrollment();
        student.removeCourse(courseId);
        
        saveAll();
    }

    private Course findCourseById(String courseId) {
        return courseCatalog.stream()
                .filter(c -> c.getCourseId().equalsIgnoreCase(courseId))
                .findFirst()
                .orElse(null);
    }

    private void saveAll() {
        saveCourses();
        saveStudents();
    }

    private void loadData() {
        loadCourses();
        loadStudents();
        
        if (courseCatalog.isEmpty()) {
            courseCatalog.add(new Course("CS101", "Intro to Java", 4, 30, 0));
            courseCatalog.add(new Course("CS102", "Data Structures", 4, 30, 0));
            courseCatalog.add(new Course("MATH201", "Linear Algebra", 3, 25, 0));
            saveCourses();
        }
    }

    private void loadCourses() {
        try (BufferedReader br = new BufferedReader(new FileReader(COURSE_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                Course c = Course.fromCSV(line);
                if (c != null) courseCatalog.add(c);
            }
        } catch (FileNotFoundException e) {
            System.out.println("No course database found. Creating new one.");
        } catch (IOException e) {
            System.out.println("Error loading courses: " + e.getMessage());
        }
    }

    private void loadStudents() {
        try (BufferedReader br = new BufferedReader(new FileReader(STUDENT_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                Student s = Student.fromCSV(line);
                if (s != null) studentDirectory.add(s);
            }
        } catch (FileNotFoundException e) {
            System.out.println("No student database found.");
        } catch (IOException e) {
            System.out.println("Error loading students: " + e.getMessage());
        }
    }

    private void saveCourses() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(COURSE_FILE))) {
            for (Course c : courseCatalog) {
                bw.write(c.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving courses: " + e.getMessage());
        }
    }

    private void saveStudents() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(STUDENT_FILE))) {
            for (Student s : studentDirectory) {
                bw.write(s.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving students: " + e.getMessage());
        }
    }
}