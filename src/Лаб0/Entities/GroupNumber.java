package Лаб0.Entities;

import Лаб0.Exceptions.IsuException;

import java.util.Objects;

public class GroupNumber {
    private int number;

    public GroupNumber(int number) throws IsuException {
        if (number<=0 || number>12)
            throw new IsuException(String.format("Invalid group number - %d", number));
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupNumber that = (GroupNumber) o;
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
