package Лаб6;

import Лаб6.BLL.Employee;

import java.util.Date;

public class Diff<T> {

    private String fieldName;
    private T oldValue;
    private T newValue;
    private Date date;

    private Employee author;

    public Diff(String fieldName, T oldValue, T newValue, Employee author) {
        this.fieldName = fieldName;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.date = new Date();
        this.author = author;
    }

    public String getFieldName() {
        return fieldName;
    }

    public T getOldValue() {
        return oldValue;
    }

    public T getNewValue() {
        return newValue;
    }

    public Date getDate() {
        return date;
    }

    public Employee getAuthor() {
        return author;
    }
}
