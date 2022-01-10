package Лаб4.Entities;

import Лаб4.Exceptions.BankException;

import java.util.Objects;

public class TransferTransaction implements Transaction{

    Account account1;
    Account account2;
    double sum;

    public TransferTransaction(Account account1, Account account2, double sum) {
        this.account1 = account1;
        this.account2 = account2;
        this.sum = sum;
    }

    @Override
    public void conduct() throws BankException {
        account1.withdraw(sum);
        account2.replenish(sum);
    }

    @Override
    public void cancel() throws BankException {
        account1.replenish(sum);
        account2.withdraw(sum);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransferTransaction that = (TransferTransaction) o;
        return Double.compare(that.sum, sum) == 0 && Objects.equals(account1, that.account1) && Objects.equals(account2, that.account2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account1, account2, sum);
    }
}
