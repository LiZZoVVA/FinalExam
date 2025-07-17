package OOP.FinalExam.t2;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private String name;
    private String id;
    private List<LearningCourse> LearningCourses;

    public Student(String name, String id) {
        this.name = name;
        this.id = id;
        this.LearningCourses = new ArrayList<>();
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<LearningCourse> getLearningCourses() {
        return LearningCourses;
    }

    public void addLearningCourse(LearningCourse course) {
        this.LearningCourses.add(course);
    }
}
