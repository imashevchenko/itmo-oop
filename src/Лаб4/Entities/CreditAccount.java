package Лаб4.Entities;

import Лаб4.Exceptions.BankException;

import java.util.Date;

public class CreditAccount extends Account {
    double creditLimit;
    double commission;

    CreditAccount(int id, double creditLimit, double commission, double sum) {
        super();
        this.ID = id;
        this.creditLimit = creditLimit;
        this.commission = commission;
        this.sum = sum;
    }

    @Override
    void withdraw(double amount) throws BankException {
        if (sum - amount >= creditLimit)
            super.withdraw(amount);
        else
            throw new BankException("Illegal operation");
    }

    @Override
    void transfer(double amount) throws BankException {
        if (sum - amount >= creditLimit)
            super.transfer(amount);
        else
            throw new BankException("Illegal operation");
    }

    public void takeCommission() {
        if (sum < 0)
            sum -= commission;
    }
}
