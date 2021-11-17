package Лаб2.Entities;

import Лаб0.Exceptions.IsuException;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Objects;

public class Lesson {
    private String teacherName;
    private int auditorium;
    private Calendar startTime;
    private Calendar endTime;

    public Lesson(String teacherName, int auditorium, Calendar startTime, Calendar endTime) throws IsuException {
        if (endTime.compareTo(startTime) <= 0)
            throw new IsuException("Wrong start and end time");
        this.teacherName = teacherName;
        this.auditorium = auditorium;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lesson lesson = (Lesson) o;
        return auditorium == lesson.auditorium
                && teacherName.equals(lesson.teacherName)
                && startTime.equals(lesson.startTime)
                && endTime.equals(lesson.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teacherName, auditorium, startTime, endTime);
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }
}
