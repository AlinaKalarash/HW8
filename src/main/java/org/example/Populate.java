package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Populate {
    public static void main(String[] args) throws FileNotFoundException {

        StringBuilder builder = new Populate().fileReader("populate_db.sql");

//        File file = new File("src/main/resources/sql/populate_db.sql");
//        Scanner scanner = new Scanner(file);
//        StringBuilder builder = new StringBuilder();
//
//        while (scanner.hasNext()) {
//            builder.append(scanner.nextLine()).append("\n");
//        }

        Database database = Database.getInstance();

        try {
            database.executeUpdate("DELETE FROM worker");
            database.executeUpdate("DELETE FROM client");
            database.executeUpdate("DELETE FROM project");
            database.executeUpdate("DELETE FROM project_worker");
        } catch (Exception e) {
            System.out.println("Cannot delete");
        }


        try {
            database.executeUpdate(builder.toString());
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    public StringBuilder fileReader(String filesName) throws FileNotFoundException {
        File file = new File("src/main/resources/sql/" + filesName);
        Scanner scanner = new Scanner(file);
        StringBuilder builder = new StringBuilder();
        while (scanner.hasNext()) {
            builder.append(scanner.nextLine()).append("\n");
        }

        return builder;
    }
}
