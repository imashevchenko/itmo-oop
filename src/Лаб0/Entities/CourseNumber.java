package Лаб0.Entities;

import Лаб0.Exceptions.IsuException;

import java.util.Objects;

public class CourseNumber {
    private int number;

    public CourseNumber(int number) throws IsuException {
        if (number<=0 || number>4)
            throw new IsuException(String.format("Invalid course number - %d", number));
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseNumber that = (CourseNumber) o;
        return number == that.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    public int getNumber() {
        return number;
    }
}
