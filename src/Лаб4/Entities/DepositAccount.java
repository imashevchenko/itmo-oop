package Лаб4.Entities;

import Лаб4.Exceptions.BankException;

import java.util.Calendar;
import java.util.Date;

public class DepositAccount extends Account {
    Calendar dateOfExpire;

    DepositAccount(int id, double percentage, double sum) {
        super();
        this.ID = id;
        this.sum=sum;
        if (sum < 50000)
            this.percentage = percentage/30;
        else if (sum < 100000)
            this.percentage = (percentage + 0.005)/30;
        else if (sum < 200000)
            this.percentage = (percentage + 0.01)/30;
        else this.percentage = (percentage + 0.015)/30;
    }

    void withdraw(double amount, Calendar date) throws BankException {
        if (date.before(dateOfExpire))
            throw new BankException("Impossible operation");
        else if (amount<=sum)
            super.withdraw(amount);
        else
            throw new BankException("Insufficient funds");
    }

    void transfer(int id, double amount, Calendar date) throws BankException {
        if (date.before(dateOfExpire))
            throw new BankException("Impossible operation");
        else if (amount<=sum)
            super.transfer(amount);
        else
            throw new BankException("Insufficient funds");
    }

    public void countPercents() {
        percentsSum += percentage * sum;
    }

    public void addPercents(){
        sum+=percentsSum;
        percentsSum=0;
    }
}
