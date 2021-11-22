package Лаб4.Entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Client {
    private int id;
    private String name;
    private String surname;
    private String address;
    private int passportNumber;
    private ClientType clientType;

    public Client(int id, String name, String surname, String address, Integer passportNumber, ClientType clientType){
        this.id=id;
        this.name=name;
        this.surname=surname;
        this.address=address;
        this.passportNumber=passportNumber;
        this. clientType=clientType;
    }

    public Client(int id, String name, String surname, ClientType clientType){
        this.id=id;
        this.name=name;
        this.surname=surname;
        this. clientType=clientType;
    }

    public void addAddressAndPassportNumber(String _address, int _passportNumber){
        address=_address;
        passportNumber=_passportNumber;
        clientType=ClientType.Verified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id == client.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public ClientType getClientType() {
        return clientType;
    }
}

