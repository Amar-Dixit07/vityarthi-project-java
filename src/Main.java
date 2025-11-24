package src;

import src.models.Course;
import src.models.Student;
import src.services.RegistrationService;
import java.util.Scanner;
import java.util.List; // Fixed the error from before

public class Main {
    private static RegistrationService service = new RegistrationService();
    private static Scanner scanner = new Scanner(System.in);
    private static Student currentUser = null;

    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("  Welcome to VITyarthi Course Registration");
        System.out.println("==========================================");

        while (true) {
            if (currentUser == null) {
                handleAuthMenu();
            } else {
                handleStudentMenu();
            }
        }
    }

    // New Menu for Login vs Sign Up
    private static void handleAuthMenu() {
        System.out.println("\n--- WELCOME ---");
        System.out.println("1. Login");
        System.out.println("2. Sign Up (New Student)");
        System.out.println("3. Exit");
        System.out.print("Select an option: ");
        
        String choice = scanner.nextLine();
        
        switch (choice) {
            case "1":
                handleLogin();
                break;
            case "2":
                handleSignUp();
                break;
            case "3":
                System.out.println("Goodbye!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private static void handleSignUp() {
        System.out.println("\n--- SIGN UP ---");
        System.out.print("Choose a Student ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter your Name: ");
        String name = scanner.nextLine();
        System.out.print("Create a Password: ");
        String pass = scanner.nextLine();

        try {
            service.registerNewStudent(id, name, pass);
            System.out.println("Account created successfully! Please login.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void handleLogin() {
        System.out.println("\n--- LOGIN ---");
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Password: ");
        String pass = scanner.nextLine();

        currentUser = service.authenticate(id, pass);

        if (currentUser != null) {
            System.out.println("Login Successful! Welcome, " + currentUser.getName());
        } else {
            System.out.println("Invalid credentials. Try again.");
        }
    }

    private static void handleStudentMenu() {
        System.out.println("\n--- MAIN MENU ---");
        System.out.println("1. View Available Courses");
        System.out.println("2. Register for a Course");
        System.out.println("3. View My Schedule");
        System.out.println("4. Withdraw from Course");
        System.out.println("5. Logout");
        System.out.println("6. Exit App");
        System.out.print("Select an option: ");

        String choice = scanner.nextLine();

        try {
            switch (choice) {
                case "1":
                    printCatalog();
                    break;
                case "2":
                    System.out.print("Enter Course ID to register: ");
                    String regId = scanner.nextLine();
                    service.register(currentUser, regId);
                    System.out.println("Success! You have been enrolled.");
                    break;
                case "3":
                    printSchedule();
                    break;
                case "4":
                    System.out.print("Enter Course ID to withdraw: ");
                    String dropId = scanner.nextLine();
                    service.withdraw(currentUser, dropId);
                    System.out.println("Success! You have dropped the course.");
                    break;
                case "5":
                    currentUser = null;
                    System.out.println("Logged out.");
                    break;
                case "6":
                    System.out.println("Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid option.");
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private static void printCatalog() {
        System.out.println("\n--- COURSE CATALOG ---");
        for (Course c : service.getAllCourses()) {
            System.out.println(c);
        }
    }

    private static void printSchedule() {
        System.out.println("\n--- MY SCHEDULE ---");
        // We use the service to get actual Course objects from the IDs
        List<Course> myCourses = service.getStudentCourses(currentUser);
        
        if (myCourses.isEmpty()) {
            System.out.println("You are not registered for any courses.");
        } else {
            for (Course c : myCourses) {
                System.out.println(c.getCourseId() + ": " + c.getCourseName());
            }
        }
    }
}