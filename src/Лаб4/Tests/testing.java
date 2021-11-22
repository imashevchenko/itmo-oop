package Лаб4.Tests;

import Лаб4.Entities.*;
import Лаб4.Exceptions.BankException;

public class testing {

    public static void main(String[] args) throws BankException {
        CentralBank centralBank = new CentralBank();

        Bank imperial = centralBank.createNewBank(0.04, 0.06, 30000, 0.005);
        Client me = imperial.addClient();


        imperial.openDebitAccount(me, 60000);
        imperial.withdraw(imperial.getDebitAccounts(me).iterator().next().getID(), 5000);
        if (imperial.getDebitAccounts(me).iterator().next().getBalance() == 55000)
            System.out.println("Transaction test without exception: [OK]");
        try {
            imperial.withdraw(imperial.getDebitAccounts(me).iterator().next().getID(), 55000);
        } catch (BankException e) {
            System.out.println("Transaction test with exception (not verified client) : [OK]");
        }

        imperial.cancelTransaction(new Transaction(imperial.getDebitAccounts(me).iterator().next().getID(), 5000, TransactionType.WITHDRAWAL));
        if (imperial.getDebitAccounts(me).iterator().next().getBalance() == 60000)
            System.out.println("Transaction cancel test : [OK]");

        Client friend = imperial.addClient();
        imperial.openDebitAccount(friend, 60000);
        imperial.transfer(imperial.getDebitAccounts(me).iterator().next().getID(),imperial.getDebitAccounts(friend).iterator().next().getID(), 5000);

        if (imperial.getDebitAccounts(me).iterator().next().getBalance() == 55000
                && imperial.getDebitAccounts(friend).iterator().next().getBalance() == 65000)
            System.out.println("Transfer transaction test : [OK]");

        centralBank.sendNotification();

        if (imperial.getDebitAccounts(me).iterator().next().getBalance() == 57200)
            System.out.println("Notifications test : [OK]");
    }
}
