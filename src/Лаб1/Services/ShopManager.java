package Лаб1.Services;

import Лаб1.Entities.Item;
import Лаб1.Entities.ItemShipment;
import Лаб1.Entities.Person;
import Лаб1.Entities.Store;
import Лаб1.Exceptions.StoreException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ShopManager {
    private static int ID;
    private List<Store> stores;

    public ShopManager() {
        ID = 0;
        stores=new ArrayList<>();
    }

    public void addStore(Store store) {
        store.setID(ID++);
        stores.add(store);
    }

    public void ship(int storeID, ItemShipment... shipments) throws StoreException {
        Store store = stores.stream().filter(store1 -> store1.getID() == storeID)
                .findFirst().orElse(null);
        if (store == null)
            throw new StoreException("No such store");
        store.Ship(shipments);
    }

    public static ItemShipment createItemShipmentSupply(Item item, int price, int amount) {
        return new ItemShipment(item, price, amount);
    }

    public static ItemShipment createItemShipmentBuy(Item item, int amount) {
        return new ItemShipment(item, amount);
    }

    public void buy(Person person, int storeID, ItemShipment... shipments) throws StoreException {
        Store store = stores.stream().filter(store1 -> store1.getID() == storeID)
                .findFirst().orElse(null);
        if (store == null)
            throw new StoreException("No such store");
        double price = store.computePrice(shipments);
        person.pay(price);
        store.buy(shipments);
    }

    public void changePrice(int storeID, Item item, double newPrice) throws StoreException {
        Store store = stores.stream().filter(store1 -> store1.getID() == storeID)
                .findFirst().orElse(null);
        if (store == null)
            throw new StoreException("No such store");
        store.setPrice(item, newPrice);
    }

    public void providePriceList(){
        for (Store store : stores) {
            System.out.printf("%s %s \n", store.getName(), store.getAddress());
            store.whatCouldBeBought();
            System.out.println("----------");
        }
    }

    public Store findCheapest(Item item, int amount) throws StoreException {
        List<Store> storesHavingItem = stores.stream()
                .filter(store -> amount <= store.getAmount(item)).collect(Collectors.toList());
        if (storesHavingItem.isEmpty())
            throw new StoreException("No stores having this amount of item");
        return storesHavingItem.stream()
                .min(Comparator.comparingDouble(store -> amount * store.getPrice(item)))
                .get();
    }

    public double getPrice(int storeID, Item item) throws StoreException {
        Store store = stores.stream().filter(store1 -> store1.getID() == storeID)
                .findFirst().orElse(null);
        if (store == null)
            throw new StoreException("No such store");
        return store.getPrice(item);
    }

    public int getAmount(int storeID, Item item) throws StoreException {
        Store store = stores.stream().filter(store1 -> store1.getID() == storeID)
                .findFirst().orElse(null);
        if (store == null)
            throw new StoreException("No such store");
        return store.getAmount(item);
    }
}
