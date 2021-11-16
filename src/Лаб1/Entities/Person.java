package Лаб1.Entities;

import Лаб1.Exceptions.StoreException;

public class Person {
    private String name;
    private double balance;

    public Person(String name, int balance) {
        this.name = name;
        this.balance = balance;
    }

    public void pay(double amount) throws StoreException {
        if (balance - amount < 0)
            throw new StoreException("Insufficient funds");
        balance -= amount;
    }

    public double getBalance() {
        return balance;
    }
}
