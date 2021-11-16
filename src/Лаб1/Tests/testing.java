package Лаб1.Tests;

import Лаб1.Entities.Item;
import Лаб1.Entities.Person;
import Лаб1.Entities.Store;
import Лаб1.Exceptions.StoreException;
import Лаб1.Services.ShopManager;

public class testing {
    static Person person;
    static ShopManager shopManager;

    static Item item1 = new Item("Gvozd");
    static Item item2 = new Item("Shurup");
    static Item item3 = new Item("Metiz");
    static Item item4 = new Item("Gayka");
    static Item item5 = new Item("Sverlo");
    static Item item6 = new Item("Molotok");
    static Item item7 = new Item("Otvertka");
    static Item item8 = new Item("Drel");
    static Item item9 = new Item("Vint");
    static Item item10 = new Item("Ploskogubtsy");
    static Item item11 = new Item("Gaechny kluch");

    public static void main(String[] args) throws StoreException {
        setupSystem();
        ShipmentTest();
        PriceChangeTest();
        FindCheapestTest_NoException();
        FindCheapestTest_Exception();
        BuyTest_NoException();
        BuyTest_Exception();
    }

    public static void setupSystem() {
        person = new Person("Arseniy", 10000);
        shopManager = new ShopManager();
    }


    public static void ShipmentTest() throws StoreException {
        shopManager.addStore(new Store("StroyMarket", "Ivanova, 12"));
        shopManager.addStore(new Store("Vse Dlya Remonta", "Sidorova, 55"));
        shopManager.addStore(new Store("Stroitel", "Petrova, 76"));

        shopManager.ship(0,
                ShopManager.createItemShipmentSupply(item1, 100, 15),
                ShopManager.createItemShipmentSupply(item3, 50, 25),
                ShopManager.createItemShipmentSupply(item4, 60, 19),
                ShopManager.createItemShipmentSupply(item8, 1000, 5));
        shopManager.ship(1,
                ShopManager.createItemShipmentSupply(item3, 60, 26),
                ShopManager.createItemShipmentSupply(item7, 20, 107),
                ShopManager.createItemShipmentSupply(item9, 100, 25),
                ShopManager.createItemShipmentSupply(item11, 10, 210));
        shopManager.ship(2,
                ShopManager.createItemShipmentSupply(item2, 100, 20),
                ShopManager.createItemShipmentSupply(item4, 65, 23),
                ShopManager.createItemShipmentSupply(item5, 70, 50),
                ShopManager.createItemShipmentSupply(item6, 115, 20));
        shopManager.providePriceList();
    }

    public static void PriceChangeTest() throws StoreException {
        shopManager.changePrice(0, item1, 61);
        if (shopManager.getPrice(0, item1) == 61)
            System.out.println("PriceChangeTest : [OK]");
        else
            System.out.println("PriceChangeTest : [FAILED]");
    }

    public static void FindCheapestTest_NoException() throws StoreException {
        if (shopManager.findCheapest(item3, 10).getName().equals("StroyMarket"))
            System.out.println("FindCheapestTest_NoException : [OK]");
        else
            System.out.println("FindCheapestTest_NoException : [FAILED]");
    }

    public static void FindCheapestTest_Exception() {
        try {
            shopManager.findCheapest(item10, 10);
            System.out.println("FindCheapestTest_Exception : [FAILED]");
        } catch (StoreException e) {
            System.out.println("FindCheapestTest_Exception : [OK]");
        }
    }

    public static void BuyTest_NoException() throws StoreException {
        shopManager.buy(person, 0, ShopManager.createItemShipmentBuy(item8, 2));
        if (person.getBalance()==8000 && shopManager.getAmount(0, item8)==3)
            System.out.println("BuyTest_NoException : [OK]");
        else
            System.out.println("BuyTest_NoException : [FAILED]");
    }

    public static void BuyTest_Exception(){
        try{
            shopManager.buy(person, 2, ShopManager.createItemShipmentBuy(item6, 25));
            System.out.println("BuyTest_Exception : [FAILED]");
        } catch (StoreException e){
            System.out.println("BuyTest_Exception : [OK]");
        }
    }

}
