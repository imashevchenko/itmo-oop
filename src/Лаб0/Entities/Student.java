package Лаб0.Entities;

import java.util.Objects;

public class Student {
    private final long id;
    private final String name;

    public Student(long id, String name) {
        this.id = id;
        this.name=name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id && name.equals(student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
