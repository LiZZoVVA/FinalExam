package OOP.FinalExam.t2;

import java.util.ArrayList;
import java.util.List;

public class UMS {
    private List<Student> Students;

    public UMS() {
        this.Students = new ArrayList<>();
    }

    public void addStudent(Student student) {
        this.Students.add(student);
    }

    public void printStudentData(Student student) {
        System.out.println("Student Information:");
        System.out.println("Name: " + student.getName());
        System.out.println("ID: " + student.getId());
        System.out.println("\nLearning Courses:");

        // Check if the student is myself (exact match required)
        if (student.getName().equals("Elizaveta Darsalia") && student.getId().equals("198376490")) {
            LearningCourse course1 = new LearningCourse();
            course1.setTitle("Object Oriented Programming (ENG)");
            course1.setAcceptancePrerequisites(" Introduction to Programming");
            course1.setMajorTopics("Java syntax and data structures; Procedural programming; Classes; Encapsulation, polymorphism, inheritance; Packages; Working with the network; Work with files; Working with text data; Work with the terminal.");

            LearningCourse course2 = new LearningCourse();
            course2.setTitle("Calculus II (ENG)");
            course2.setAcceptancePrerequisites("Calculus I");
            course2.setMajorTopics("Lecture; Seminars; Verbal method; Demonstration method; Discussion; Practical work.");

            LearningCourse course3 = new LearningCourse();
            course3.setTitle("Computer Organization (ENG)");
            course3.setAcceptancePrerequisites(" Introduction to Programming");
            course3.setMajorTopics("Representing and manipulating information; Machine-level representations of programs; Optimizing program performance. The memory hierarchy.");
            
            LearningCourse course4 = new LearningCourse();
            course4.setTitle("Mathematical Foundation of Computing (ENG)");
            course4.setAcceptancePrerequisites(" Calculus I");
            course4.setMajorTopics("Mathematical Logic; Elements of Discrete Mathematics; Elements of Sets Theory; Elements of Graph Theory; Elements of Combinatorics; Elements of Digital Systems;");
           
            LearningCourse course5 = new LearningCourse();
            course5.setTitle("English Language Course C1-2 (ENG)");
            course5.setAcceptancePrerequisites("English Language Course C1-1 (ENG)");
            course5.setMajorTopics("Education, Systems, Communication, Order, Intelligence, Change");

            student.getLearningCourses().clear();
            student.addLearningCourse(course1);
            student.addLearningCourse(course2);
            student.addLearningCourse(course3);
            student.addLearningCourse(course4);
            student.addLearningCourse(course5);
        }

        if (student.getLearningCourses().isEmpty()) {
            System.out.println("NO COURSES");
        } else {
            for (LearningCourse course : student.getLearningCourses()) {
                System.out.println("\nCourse Title: " + course.getTitle());
                System.out.println("Acceptance Prerequisites: " + course.getAcceptancePrerequisites());
                System.out.println("Major Topics: " + course.getMajorTopics());
            }
        }
        System.out.println("----------------------------------");
    }

    public static void main(String[] args) {
        UMS ums = new UMS();
        
        // Exact match required (case-sensitive)
        Student me = new Student("Elizaveta Darsalia", "198376490");
        ums.addStudent(me);
        
        ums.printStudentData(me);
    }
}