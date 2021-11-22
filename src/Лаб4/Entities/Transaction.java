package Лаб4.Entities;

import java.util.Objects;

public class Transaction {
    int id1;
    int id2;
    double sum;
    TransactionType type;

    Transaction(int id1, int id2, double sum) {
        this.id1 = id1;
        this.id2 = id2;
        this.sum = sum;
        this.type=TransactionType.TRANSFER;
    }

    public Transaction(int id1, double sum, TransactionType type) {
        this.id1 = id1;
        this.sum = sum;
        this.type=type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;
        Transaction that = (Transaction) o;
        return id1 == that.id1 &&
                id2 == that.id2 &&
                Double.compare(that.sum, sum) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id1, id2, sum);
    }
}

