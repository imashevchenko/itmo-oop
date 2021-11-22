package Лаб4.Entities;

public class ClientBuilder {
    private String name;
    private String surname;
    private String address;
    private Integer passportNumber;
    private int id;

    ClientBuilder(int id){
        this.id=id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPassportNumber(int passportNumber) {
        this.passportNumber = passportNumber;
    }
    public Client create(){
        if (address==null || passportNumber==null)
            return new Client(id, name,surname,ClientType.NotVerified);
        return new Client(id, name,surname,address,passportNumber,ClientType.Verified);
    }
}