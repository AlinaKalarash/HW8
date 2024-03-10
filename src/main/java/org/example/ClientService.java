package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientService {

    private static final String INSERT = "INSERT INTO client (id, name) VALUES (?, ?)";
    private static final String GET_ID = "SELECT id FROM client WHERE name = ?";
    private static final String SELECT_BY_ID = "SELECT id, name FROM client WHERE id= ?";
    private static final String SELECT_ALL = "SELECT * FROM client";
    private static final String UPDATE_NAME = "UPDATE client SET name = ? WHERE id= ?";
    private static final String DELETE_BY_ID = "DELETE FROM client WHERE id= ?";


    private Client client;
    private PreparedStatement insertStatement;
    private PreparedStatement getInsertStatement;
    private PreparedStatement selectByIdStatement;
    private PreparedStatement selectAllStatement;
    private PreparedStatement updateNameStatement;
    private PreparedStatement deleteByIdStatement;

    private Connection connection = Database.getConnection();


    public ClientService() {
        try {
            this.client = new Client();
            this.insertStatement = connection.prepareStatement(INSERT);
            this.getInsertStatement = connection.prepareStatement(GET_ID);
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
        nameValidation(name);
        client.setName(name);
        client.setId(this.client.getId()+1);
        try {
            insertStatement.setLong(1, client.getId());
            insertStatement.setString(2, name);
            insertStatement.executeUpdate();
            return client.getId();

        } catch (SQLException e) {
            System.out.println("ERROR with creation: " + e.getMessage());
        }
        return -1;
    }

    String getById(long id) {
        idValidation(id);
        try {
            selectByIdStatement.setLong(1, id);
            try(ResultSet resultSet = selectByIdStatement.executeQuery()) {
                while (resultSet.next()) {
                    client = new Client(resultSet.getLong("id"), resultSet.getString("name"));
                    System.out.println("Here is your client: "+ client);
                }
            } catch (SQLException e) {
                System.out.println("ERROR with getting client: "+e.getMessage());;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return client.getName();
    }

    void setName(long id, String name) {
        idValidation(id);
        nameValidation(name);
        try {
            updateNameStatement.setString(1, name);
            updateNameStatement.setLong(2, id);
            updateNameStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("ERROR with updating name: "+e.getMessage());
        }
    }

    void deleteById(long id){
        idValidation(id);
        try {
            deleteByIdStatement.setLong(1, id);
            deleteByIdStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("ERROR in deleting: " + e.getMessage());
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

    void idValidation(long id) {
        if(id < 1) {
            throw new RuntimeException();
        }
    }
    void nameValidation(String name) {
        if(name.length() > 100 || name.isEmpty()) {
            System.out.println("This name is not available");
            throw new RuntimeException();
        }
        if (name.toLowerCase().contains("delete") || name.toLowerCase().contains("drop") || name.toLowerCase().contains("alter")) {
            System.out.println("What do you think you doing?");
            throw new RuntimeException();
        }
    }
}
