package Лаб1.Entities;

import Лаб1.Exceptions.StoreException;

import java.util.HashMap;

public class Store {
    private int ID;
    private String name;
    private String address;
    private HashMap<Item, Double> Prices = new HashMap<>();
    private HashMap<Item, Integer> Amount = new HashMap<>();


    public Store(String name, String address) {
        this.name = name;
        this.address = address;
    }


    public void Ship(ItemShipment... shipments) {
        for (ItemShipment shipment : shipments) {
            Prices.put(shipment.getItem(), shipment.getPrice());
            if (Amount.containsKey(shipment.getItem()))
                Amount.put(shipment.getItem(), shipment.getAmount() + Amount.get(shipment.getItem()));
            else
                Amount.put(shipment.getItem(), shipment.getAmount());
        }
    }

    public void whatCouldBeBought() {
        for (Item item : Amount.keySet())
            System.out.println(item.getName() + ": " + Prices.get(item) + " " + Amount.get(item)+" items");
    }

    public Double computePrice(ItemShipment... shipments) throws StoreException {
        double sum = 0;
        for (ItemShipment shipment : shipments) {
            if (shipment.getAmount() <= Amount.get(shipment.getItem())) {
                sum += shipment.getAmount() * Prices.get(shipment.getItem());
            } else
                throw new StoreException("Out of product");
        }
        return sum;
    }

    public void buy(ItemShipment... shipments) throws StoreException {
        for (ItemShipment shipment : shipments) {
            if (shipment.getAmount() <= Amount.get(shipment.getItem())) {
                Amount.computeIfPresent(shipment.getItem(), (k, v) -> v - shipment.getAmount());
            } else
                throw new StoreException("Out of product");
        }
    }

    public void setPrice(Item item, double newPrice) {
        Prices.put(item, newPrice);
    }

    public Double getPrice(Item item) {
        return Prices.getOrDefault(item, Double.MAX_VALUE);
    }

    public Integer getAmount(Item item) {
        return Amount.getOrDefault(item, 0);
    }

    public String getName() {
        return name;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public String getAddress() {
        return address;
    }
}
