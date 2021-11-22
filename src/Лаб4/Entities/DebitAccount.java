package Лаб4.Entities;

import Лаб4.Exceptions.BankException;

public class DebitAccount extends Account {

    DebitAccount(int id, double percentage, double sum){
        super();
        this.ID=id;
        this.percentage=percentage;
        this.sum=sum;
    }

    void withdraw(double amount) throws BankException {
        if (sum<amount)
            throw new BankException("Insufficient funds");
        super.withdraw(amount);
    }

    void transfer(double amount) throws BankException {
        if (sum<amount)
            throw new BankException("Insufficient funds");
        super.transfer(amount);
    }

}