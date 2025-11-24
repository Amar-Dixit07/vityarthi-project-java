package src.models;

/**
 * Represents a specific subject offered by the university.
 */
public class Course {
    private String courseId;
    private String courseName;
    private int credits;
    private int capacity;
    private int enrolledCount;

    public Course(String courseId, String courseName, int credits, int capacity, int enrolledCount) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.credits = credits;
        this.capacity = capacity;
        this.enrolledCount = enrolledCount;
    }

    public String getCourseId() { return courseId; }
    public String getCourseName() { return courseName; }
    public int getCredits() { return credits; }
    public int getCapacity() { return capacity; }
    public int getEnrolledCount() { return enrolledCount; }

    public boolean isFull() {
        return enrolledCount >= capacity;
    }

    public void incrementEnrollment() {
        if (!isFull()) this.enrolledCount++;
    }

    public void decrementEnrollment() {
        if (this.enrolledCount > 0) this.enrolledCount--;
    }

    public String toCSV() {
        return courseId + "," + courseName + "," + credits + "," + capacity + "," + enrolledCount;
    }

    public static Course fromCSV(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length < 5) return null;
        return new Course(
            parts[0], 
            parts[1], 
            Integer.parseInt(parts[2]), 
            Integer.parseInt(parts[3]), 
            Integer.parseInt(parts[4])
        );
    }

    @Override
    public String toString() {
        return String.format("| %-8s | %-25s | Credits: %d | Seats: %d/%d |", 
            courseId, courseName, credits, enrolledCount, capacity);
    }
}