package Лаб4.Entities;

import java.util.ArrayList;
import java.util.List;

public class CentralBank {

    private List<Bank> banks;

    public CentralBank(){
        banks=new ArrayList<>();
    }

    public Bank createNewBank(double debitPercentage, double depositPercentage, double creditLimit, double commission){
        Bank bank = new Bank(debitPercentage, depositPercentage, creditLimit, commission);
        banks.add(bank);
        return bank;
    }

    public void sendNotification(){
        for (Bank bank : banks)
            bank.payPercentsAndCommission();
    }

}
