package Лаб2.Entities;

import java.util.ArrayList;
import java.util.List;

public class GroupOGNP {
    private List<StudentExtra>  students;
    private List<Lesson> lessons;
    private long id;

    public GroupOGNP(long id) {
        this.students = new ArrayList<>();
        this.lessons = new ArrayList<>();
        this.id = id;
    }

    public void addStudent(StudentExtra student){
        students.add(student);
    }

    public void removeStudent(StudentExtra student){
        students.remove(student);
    }

    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
    }
    public List<StudentExtra> getStudents() {
        return students;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public long getId() {
        return id;
    }
}
