package Лаб1.Entities;

public class ItemShipment {
    private Item item;
    private int amount;
    private double price;

    public ItemShipment(Item item, int price, int amount){
        this.item=item;
        this.amount=amount;
        this.price=price;
    }

    public ItemShipment(Item item, int amount){
        this.item=item;
        this.amount=amount;
    }

    public int getAmount(){
        return amount;
    }
    double getPrice(){
        return price;
    }
    public Item getItem(){
        return item;
    }
}

