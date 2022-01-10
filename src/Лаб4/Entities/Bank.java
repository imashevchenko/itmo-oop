package Лаб4.Entities;

import Лаб4.Exceptions.BankException;

import java.util.*;
import java.util.stream.Collectors;

public class Bank {
    private HashMap<Client, HashSet<Account>> clientAccountHashMap;
    private ArrayList<Client> clients;
    private ArrayList<Account> accounts;
    private double debitPercentage;
    private double depositPercentage;
    private double creditLimit;
    private double commission;
    private int accountId = 0;
    private int clientID = 0;
    protected Set<Transaction> transactionSet = new HashSet<>();

    public Bank(double debitPercentage, double depositPercentage, double creditLimit, double commission) {
        clientAccountHashMap=new HashMap<>();
        clients = new ArrayList<>();
        accounts = new ArrayList<>();
        this.debitPercentage = debitPercentage;
        this.depositPercentage = depositPercentage;
        this.creditLimit = creditLimit;
        this.commission = commission;
    }


    public Client addClient() {
        ClientBuilder clientBuilder = new ClientBuilder(clientID++);
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter name");
        clientBuilder.setName(sc.nextLine());
        System.out.println("Enter surname");
        clientBuilder.setSurname(sc.nextLine());
        System.out.println("Do you want to add your adress and passport [y/n]");
        if (Objects.equals(sc.nextLine(), "y")) {
            System.out.println("Enter address");
            clientBuilder.setAddress(sc.nextLine());
            System.out.println("Enter passport number");
            clientBuilder.setPassportNumber(sc.nextInt());
        }
        Client client = clientBuilder.create();
        clients.add(client);
        clientAccountHashMap.put(client, null);
        return client;
    }

    public void changeCreditLimit(double newLimit) {
        this.creditLimit = newLimit;

        for (Client client : clients) {
            if (clientAccountHashMap.get(client).stream().anyMatch(account -> account instanceof CreditAccount))
                sendNotification(client, "Limit changed");
        }
    }

    public void changePercents(double newPercents) {
        this.depositPercentage = newPercents;

        for (Client client : clients) {
            if (clientAccountHashMap.get(client).stream().anyMatch(account -> account instanceof DepositAccount))
                sendNotification(client, "Percents changed");
        }
    }


    public void openDebitAccount(Client client, double sum) {
        DebitAccount debitAccount = new DebitAccount(accountId++, debitPercentage, sum);
        if (client.getClientType() == ClientType.NotVerified)
            debitAccount.setLimit(true);
        HashSet<Account> accountHashSet;
        if (clientAccountHashMap.get(client) != null)
            accountHashSet = clientAccountHashMap.get(client);
        else
            accountHashSet = new HashSet<>();
        accountHashSet.add(debitAccount);
        clientAccountHashMap.put(client, accountHashSet);
        accounts.add(debitAccount);
    }

    public void openDepositAccount(Client client, double sum) {
        DepositAccount depositAccount = new DepositAccount(accountId++, depositPercentage, sum);
        if (client.getClientType() == ClientType.NotVerified)
            depositAccount.setLimit(true);
        HashSet<Account> accountHashSet;
        if (clientAccountHashMap.get(client) != null)
            accountHashSet = clientAccountHashMap.get(client);
        else
            accountHashSet = new HashSet<>();
        accountHashSet.add(depositAccount);
        clientAccountHashMap.put(client, accountHashSet);
        accounts.add(depositAccount);
    }

    public void openCreditAccount(Client client, double sum) {
        CreditAccount creditAccount = new CreditAccount(accountId++, creditLimit, commission, sum);
        if (client.getClientType() == ClientType.NotVerified)
            creditAccount.setLimit(true);
        HashSet<Account> accountHashSet;
        if (clientAccountHashMap.get(client) != null)
            accountHashSet = clientAccountHashMap.get(client);
        else
            accountHashSet = new HashSet<>();
        accountHashSet.add(creditAccount);
        clientAccountHashMap.put(client, accountHashSet);
        accounts.add(creditAccount);
    }

    public void transfer(int id1, int id2, double amount) throws BankException {
        Account acc1 = accounts.stream().filter(account -> account.getID() == id1).findFirst().orElse(null);
        Account acc2 = accounts.stream().filter(account -> account.getID() == id2).findFirst().orElse(null);
        if (acc1 == null || acc2 == null)
            throw new BankException("No such accounts");
        Transaction transaction = new TransferTransaction(acc1, acc2, amount);
        transaction.conduct();
        transactionSet.add(transaction);
    }

    public void withdraw(int accountId, double amount) throws BankException {
        Account acc = accounts.stream().filter(account -> account.getID() == accountId).findFirst().orElse(null);
        if (acc == null)
            throw new BankException("No such account");
        Transaction transaction = new WithdrawalTransaction(acc, amount);
        transaction.conduct();
        transactionSet.add(transaction);
    }

    public void replenish(int accountId, double amount) throws BankException {
        Account acc = accounts.stream().filter(account -> account.getID() == accountId).findFirst().orElse(null);
        if (acc == null)
            throw new BankException("No such account");
        Transaction transaction = new ReplenishTransaction(acc, amount);
        transaction.conduct();
        transactionSet.add(transaction);
    }

    public void cancelTransaction(Transaction transaction) throws BankException {
        transaction.cancel();
        transactionSet.remove(transaction);
    }


    void payPercentsAndCommission() {
        clientAccountHashMap.values().forEach(t -> t.forEach(a ->
        {
            if (a instanceof CreditAccount)
                ((CreditAccount) a).takeCommission();
            if (a instanceof DepositAccount) {
                a.countPercents();
                a.addPercents();
            }
            if (a instanceof DebitAccount) {
                a.countPercents();
                a.addPercents();
            }
        }));
    }

    public HashSet<Account> getAccounts(Client client) {
        return clientAccountHashMap.get(client);
    }

    public Set<Account> getDebitAccounts(Client client) {
        return clientAccountHashMap.get(client).stream().filter(account -> account instanceof DebitAccount).collect(Collectors.toSet());
    }

    public Set<Account> getDepositAccounts(Client client) {
        return clientAccountHashMap.get(client).stream().filter(account -> account instanceof DepositAccount).collect(Collectors.toSet());
    }

    public Set<Account> getCreditAccounts(Client client) {
        return clientAccountHashMap.get(client).stream().filter(account -> account instanceof CreditAccount).collect(Collectors.toSet());
    }


    public double getCommission() {
        return commission;
    }

    public double getDebitPercentage() {
        return debitPercentage;
    }

    public void sendNotification(Client client, String message) {

    }
}
