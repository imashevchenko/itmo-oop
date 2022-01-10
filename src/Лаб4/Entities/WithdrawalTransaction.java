package Лаб4.Entities;

import Лаб4.Exceptions.BankException;

import java.util.Objects;

public class WithdrawalTransaction implements Transaction{

    Account account;
    double sum;

    public WithdrawalTransaction(Account account, double sum) {
        this.account = account;
        this.sum = sum;
    }

    @Override
    public void conduct() throws BankException {
        account.withdraw(sum);
    }

    @Override
    public void cancel(){
        account.replenish(sum);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WithdrawalTransaction that = (WithdrawalTransaction) o;
        return Double.compare(that.sum, sum) == 0 && Objects.equals(account, that.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, sum);
    }
}
