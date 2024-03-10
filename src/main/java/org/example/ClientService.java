package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientService {

//      long create(String name) - додає нового клієнта з іменем name. Повертає ідентифікатор щойно створеного клієнта.
//            String getById(long id) - повертає назву клієнта з ідентифікатором id
//      void setName(long id, String name) - встановлює нове ім'я name для клієнта з ідентифікатором id
//      void deleteById(long id) - видаляє клієнта з ідентифікатором id
//      List<Clients> listAll() - повертає всіх клієнтів з БД у вигляді колекції об'єктів типу Clients
//            (цей клас створи сам, у ньому має бути 2 поля - id та name)
//    TODO:
//      Передбач валідацію вхідних даних в методах класу ClientService. Якщо якісь вхідні дані некоректні
//      (наприклад, ми намагаємось створити клієнта з занадто коротким чи довгим іменем) - відповідний метод має викидати Exception.

    private static final String INSERT = "INSERT INTO client (id, name) VALUES (?, ?)";
    private static final String SELECT_BY_ID = "SELECT id, name FROM client WHERE id= ?";
    private static final String SELECT_ALL = "SELECT * FROM client";
    private static final String UPDATE_NAME = "UPDATE client SET name = ? WHERE id= ?";
    private static final String DELETE_BY_ID = "DELETE FROM client WHERE id= ?";


    private PreparedStatement insertStatement;
    private PreparedStatement selectByIdStatement;
    private PreparedStatement selectAllStatement;
    private PreparedStatement updateNameStatement;
    private PreparedStatement deleteByIdStatement;

    private Connection connection = Database.getConnection();


    public ClientService() {
        try {
            this.insertStatement = connection.prepareStatement(INSERT);
            this.selectByIdStatement = connection.prepareStatement(SELECT_BY_ID);
            this.selectAllStatement = connection.prepareStatement(SELECT_ALL);
            this.updateNameStatement = connection.prepareStatement(UPDATE_NAME);
            this.deleteByIdStatement = connection.prepareStatement(DELETE_BY_ID);
        } catch (SQLException e) {
            System.out.println("Problem with preparedStatement: " + e.getMessage());
        }
    }

//   TODO:
//    зробити так, щоб айдішнік вибирався автоматично
    long create(String name, long id) {

        try {
            insertStatement.setLong(1, id);
            insertStatement.setString(2, name);
            return insertStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("ERROR with creation: " + e.getMessage());
        }
        return -1;
    }

    String getById(long id) {
        Client client1 = null;
        try {
            selectByIdStatement.setLong(1, id);
            ResultSet resultSet = selectByIdStatement.executeQuery();
            while (resultSet.next()) {
                client1 = new Client(resultSet.getLong("id"), resultSet.getString("name"));
                System.out.println("Here is your client: "+ client1);
            }
        } catch (SQLException e) {
            System.out.println("ERROR with getting client: "+e.getMessage());;
        }
        return client1.getName();
    }

    void setName(long id, String name) {
        try {
            updateNameStatement.setString(1, name);
            updateNameStatement.setLong(2, id);
            updateNameStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("ERROR with updating name: "+e.getMessage());
        }
    }

    void deleteById(long id){
        try {
            deleteByIdStatement.setLong(1, id);
            deleteByIdStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("ERROR in deleting: "+e.getMessage());
        }

    }

    List<Client> listAll() {
        List<Client> clients = new ArrayList<>();
        try (ResultSet resultSet = selectAllStatement.executeQuery()) {
            while (resultSet.next()) {
                Client client = new Client(resultSet.getLong("id"), resultSet.getString("name"));
                clients.add(client);
            }
        } catch (SQLException e) {
            System.out.println("ERROR with listing all motherfuckers: " + e.getMessage());
        }

//        for (Client client : clients) {
//            System.out.println("ID: " + client.getId() + ", Name: " + client.getName());
//        }
        return clients;
    }
}
