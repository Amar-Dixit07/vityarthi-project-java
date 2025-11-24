Project Statement: University Course Registration System

# 1. Problem Statement

In many educational institutions, course registration is often a stressful and chaotic process. Manual systems or poorly designed digital portals can lead to confusion regarding seat availability, schedule conflicts, and lost data. Students often lack real-time visibility into which courses are full, leading to enrollment errors. This project aims to solve these issues by providing a robust, lightweight, and transparent Command Line Interface (CLI) system that automates the validation and recording of course enrollments.

# 2. Scope of the Project

The scope of this project is to develop a standalone Java application that simulates a centralized university registration platform.
The system focuses on:

* Digitalization: converting manual course lists into a queryable digital catalog.

* Automation: automatically enforcing rules such as course capacity limits and preventing duplicate registrations.

* Persistence: ensuring that student records and course availability are saved securely to local storage, surviving system restarts.

# 3. Target Users

* University Students: The primary end-users who need a reliable tool to browse courses, check availability, and finalize their semester schedules without administrative delays.

* University Administrators: Who require an automated system to maintain accurate counts of student enrollment and course loads without manual tallying.

# 4. High-Level Features

* Secure Authentication System: A user management module allowing new students to sign up and existing students to log in securely.

* Live Course Catalog: A viewing module that displays course details including credits, total capacity, and real-time remaining seats.

* ntelligent Registration Engine: A core processing unit that handles enrollment requests, automatically rejecting requests if a course is full or if the student is already enrolled.

* Schedule Management: A dashboard for students to view their confirmed registrations and withdraw from courses if needed, instantly updating seat availability for others.

* Data Persistence Layer: A custom file-handling mechanism that reads and writes data to CSV-formatted text files (courses.txt and students.txt), ensuring data integrity across sessions.