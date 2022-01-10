package Лаб4.Entities;

import Лаб4.Exceptions.BankException;


public interface Transaction {
    void conduct() throws BankException;
    void cancel() throws BankException;
}

