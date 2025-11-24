# Amar Dixit, 24BAS10093

# University Course Registration System

## 1\. Overview

The **University Course Registration System** is a Java-based Command Line Interface (CLI) application designed to simulate the academic enrollment process. It facilitates the interaction between students and the university's course catalog, allowing for seamless course registration, schedule management, and data persistence.

This project demonstrates core Object-Oriented Programming (OOP) principles, including encapsulation, abstraction, and separation of concerns using a Service-Oriented Architecture. It uses a custom file-based database to ensure data survives even after the application is closed.

## 2\. Features

  * **User Authentication:**
      * **Sign Up:** New students can create an account with a unique ID and password.
      * **Login:** Secure access to the student dashboard using credentials.
  * **Course Catalog:**
      * View a list of available courses.
      * Real-time display of course credits, total capacity, and currently enrolled students.
  * **Registration Engine:**
      * **Enrollment:** Students can register for courses by ID.
      * **Validation:** The system prevents registering for full courses or enrolling in the same course twice.
  * **Schedule Management:**
      * View personalized class schedules.
      * **Withdrawal:** Ability to drop courses, which immediately frees up seats for other students.
  * **Data Persistence:**
      * Automatically saves all student and course data to `students.txt` and `courses.txt`.
      * Data remains available upon restarting the application.

## 3\. Technologies & Tools Used

  * **Language:** Java (JDK 17 or higher recommended)
  * **Architecture:** Model-View-Controller (MVC) / Service Layer Pattern
  * **Persistence:** Java File I/O (`BufferedReader`, `BufferedWriter`)
  * **Version Control:** Git

## 4\. Project Structure

```text
.
├── README.md
├── src/
│   ├── Main.java
│   ├── models/
│   ├── services/
│   └── test/
```

## 5\. Steps to Install & Run

Follow these steps to set up the project on your local machine.

### Prerequisites

Ensure you have Java installed. You can check by running:

```bash
java -version
```

### Installation

1.  Download or clone this repository.
2.  Open your terminal or command prompt.
3.  Navigate to the **root directory** of the project (the folder containing `src`).

### How to Run

1.  **Compile the source code:**
    ```bash
    javac src/Main.java src/models/*.java src/services/*.java
    ```
2.  **Start the application:**
    ```bash
    java src.Main
    ```
3.  **Usage:**
      * On the first run, choose **Option 2 (Sign Up)** to create a new user.
      * Once registered, use **Option 1 (Login)** to access the registration menu.

## 6\. Instructions for Testing

This project includes a custom, dependency-free unit testing suite to validate the business logic (e.g., ensuring a student cannot register for a full course).

1.  **Compile the Test Suite:**
    Make sure you are in the project root folder.

    ```bash
    javac src/test/RegistrationServiceTest.java src/models/*.java src/services/*.java
    ```

2.  **Run the Tests:**

    ```bash
    java src.test.RegistrationServiceTest
    ```

3.  **Expected Output:**
    You should see a report indicating `PASS` or `FAIL` for the following test cases:

      * `testSuccessfulRegistration`
      * `testPreventDuplicateRegistration`
      * `testCourseFullRegistration`