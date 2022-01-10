package Лаб4.Entities;

import Лаб4.Exceptions.BankException;

import java.util.Objects;

public class ReplenishTransaction implements Transaction{

    Account account;
    double sum;

    public ReplenishTransaction(Account account, double sum) {
        this.account = account;
        this.sum = sum;
    }

    @Override
    public void conduct() {
        account.replenish(sum);
    }

    @Override
    public void cancel() throws BankException {
        account.withdraw(sum);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReplenishTransaction that = (ReplenishTransaction) o;
        return Double.compare(that.sum, sum) == 0 && Objects.equals(account, that.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, sum);
    }
}
