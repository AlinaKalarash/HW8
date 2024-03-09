package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.Scanner;

public class Init {
    public static void main(String[] args) throws FileNotFoundException {

        StringBuilder builder = new Init().fileReader("init_db.sql");

//        File file = new File("src/main/resources/sql/init_db.sql");
//        Scanner scanner = new Scanner(file);
//        StringBuilder builder = new StringBuilder();
//        while (scanner.hasNext()) {
//            builder.append(scanner.nextLine()).append("\n");
//        }

        Database database = Database.getInstance();


        try {
            database.executeUpdate(builder.toString());
        } catch (Exception e) {
            System.out.println("Error");
        }

        Connection conn = Database.getInstance().getConnection();

        database.closeConnection();
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
