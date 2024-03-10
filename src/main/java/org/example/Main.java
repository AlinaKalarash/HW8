package org.example;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Database database = Database.getInstance();
        Connection conn = Database.getInstance().getConnection();

        ClientService service1 = new ClientService();

        System.out.println(service1.create("Adella", 1));
        System.out.println(service1.create("Richard",2));
        System.out.println(service1.create("Anna",3));
        System.out.println(service1.create("Senior Parasolya",4));
        System.out.println(service1.create("Alla",5));
        System.out.println(service1.create("Aa",6));
        System.out.println(service1.create("David",7));
        System.out.println(service1.create("Marina",8));
        System.out.println(service1.create("Mara",8));

        System.out.println(service1.getById(2));
        service1.setName(5, "Tori");
        service1.deleteById(1);

        System.out.println(service1.listAll());

        database.closeConnection();
    }
}
