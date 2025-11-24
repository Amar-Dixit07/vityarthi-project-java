package src.test;

import src.models.Course;
import src.models.Student;
import src.services.RegistrationService;

import java.io.File;
import java.util.List;

public class RegistrationServiceTest {

    private static RegistrationService service;
    private static Student testStudent;

    public static void main(String[] args) {
        System.out.println("Running Unit Tests (No Dependencies)...\n");

        runTest("testSuccessfulRegistration", () -> {
            setUp();

            String courseId = "CS101";

            service.register(testStudent, courseId);

            List<String> registeredIds = testStudent.getRegisteredCourseIds();
            assertEquals(1, registeredIds.size(), "Student should have 1 course");
            assertEquals("CS101", registeredIds.get(0), "Course ID should match");
        });

        runTest("testPreventDuplicateRegistration", () -> {
            setUp();
            service.register(testStudent, "CS101");

            try {
                service.register(testStudent, "CS101");
                throw new RuntimeException("Test Failed: Should have thrown IllegalStateException");
            } catch (IllegalStateException e) {
                assertEquals("Already enrolled.", e.getMessage(), "Error message mismatch");
            }
        });

        runTest("testCourseFullRegistration", () -> {
            setUp();
            Course cs101 = service.getAllCourses().stream()
                    .filter(c -> c.getCourseId().equals("CS101"))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("CS101 not found"));

            int seatsToFill = cs101.getCapacity();

            for (int i = 0; i < seatsToFill; i++) {
                Student dummy = new Student("DUMMY-" + i, "Dummy", "pass");
                service.register(dummy, "CS101");
            }

            try {
                service.register(testStudent, "CS101");
                throw new RuntimeException("Test Failed: Should have thrown IllegalStateException for full course");
            } catch (IllegalStateException e) {
                assertEquals("Course is full.", e.getMessage(), "Error message mismatch");
            }
        });

        System.out.println("\nAll Tests Finished.");
    }

    private static void setUp() {
        deleteFile("courses.txt");
        deleteFile("students.txt");
        service = new RegistrationService();
        testStudent = new Student("TEST01", "Test User", "password");
    }

    private static void deleteFile(String filename) {
        File file = new File(filename);
        if (file.exists()) file.delete();
    }

    interface TestRunnable {
        void run() throws Exception;
    }

    private static void runTest(String testName, TestRunnable test) {
        System.out.print("TEST: " + testName + " ... ");
        try {
            test.run();
            System.out.println("\u001B[32mPASS\u001B[0m");
        } catch (Exception e) {
            System.out.println("\u001B[31mFAIL\u001B[0m");
            System.out.println("    Reason: " + e.getMessage());
        }
    }

    private static void assertEquals(Object expected, Object actual, String message) {
        if (!expected.equals(actual)) {
            throw new RuntimeException(message + " [Expected: " + expected + ", Actual: " + actual + "]");
        }
    }
    
    private static void assertEquals(int expected, int actual, String message) {
        if (expected != actual) {
            throw new RuntimeException(message + " [Expected: " + expected + ", Actual: " + actual + "]");
        }
    }
}