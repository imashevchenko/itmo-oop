package Лаб4.Entities;

import Лаб4.Exceptions.BankException;

import java.util.Objects;

public class Account {
    protected double sum;
    protected int ID;
    protected boolean hasLimit = false;
    double limit = 50000;
    public double percentsSum = 0;
    public double percentage = 0;

    void withdraw(double amount) throws BankException {
        if (hasLimit && amount > limit)
            throw new BankException("Illegal operation");
        sum -= amount;
    }

    void replenish(double amount) {
        sum += amount;
    }


    void transfer(double amount) throws BankException {
        if (hasLimit && amount > limit)
            throw new BankException("Illegal operation");
        sum -= amount;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return ID == account.ID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }

    public int getID() {
        return ID;
    }

    public double getBalance() {
        return sum;
    }

    public void setLimit(boolean hasLimit) {
        this.hasLimit = hasLimit;
    }

    void countPercents() {
        percentsSum += sum * percentage;
    }

    void addPercents() {
        sum += percentsSum;
        percentsSum = 0;
    }
}