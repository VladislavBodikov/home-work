package com.sbrf.reboot.repository.impl;

import com.sbrf.reboot.dto.Customer;
import com.sbrf.reboot.repository.CustomerRepository;
import lombok.NonNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerH2Repository implements CustomerRepository {

    private final String JDBC_DRIVER = "org.h2.Driver";
    private final String DB_URL = "jdbc:h2:~/my_db";

    private final String USER = "sa";
    private final String PASS = "";

    private int nextAvailableId;

    public CustomerH2Repository() {
        dropTableIfExist();
        createTableIfNotExist();
        nextAvailableId = getNextId();
    }

    private boolean dropTableIfExist() {
        String sql = "DROP TABLE IF EXISTS CUSTOMER;";
        try (Connection connection = getConnection().orElseThrow(SQLException::new)) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.executeUpdate();
                connection.commit();
                return true;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    private boolean createTableIfNotExist() {
        String sql = "CREATE TABLE IF NOT EXISTS " +
                "CUSTOMER" +
                "( id INT not NULL, " +
                "name VARCHAR(255)," +
                "email VARCHAR(255)," +
                "PRIMARY KEY ( id )) ;";
        try (Connection connection = getConnection().orElseThrow(SQLException::new)) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.executeUpdate();
                connection.commit();
                return true;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    private int getNextId() {
        String sql = "SELECT id FROM CUSTOMER ORDER BY id DESC LIMIT 1;";
        int lastActualId = 0;
        try (Connection connection = getConnection().orElseThrow(SQLException::new)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.first()) {
                        lastActualId = resultSet.getInt("id");
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return ++lastActualId;
    }

    @Override
    public boolean createCustomer(String name, String eMail) {

        if (isCustomerExist(name, eMail)) {
            return false;
        }

        String sql = "INSERT INTO CUSTOMER VALUES(?,?,?);";
        int rows = 0;
        try (Connection connection = getConnection().orElseThrow(SQLException::new)) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, nextAvailableId++);
                preparedStatement.setString(2, name);
                preparedStatement.setString(3, eMail);

                rows = preparedStatement.executeUpdate();

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return rows > 0;
    }

    @Override
    public boolean createCustomerAll(@NonNull List<Customer> customers) {
        // check if some customer(s) already exists
        long notExistsCustomers = customers
                .stream()
                .filter((x)->!isCustomerExist(x.getName(),x.getEMail()))
                .count();
        if (notExistsCustomers != customers.size()){
            return false;
        }

        String sql = "INSERT INTO CUSTOMER VALUES(?,?,?);";
        boolean isSuccess = false;

        try (Connection connection = getConnection().orElseThrow(SQLException::new)) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                for (Customer customer : customers){
                    preparedStatement.setInt(1, nextAvailableId++);
                    preparedStatement.setString(2, customer.getName());
                    preparedStatement.setString(3, customer.getEMail());

                    preparedStatement.addBatch();
                }
                int[] rows = preparedStatement.executeBatch();
                isSuccess = rows.length == customers.size();

                if (isSuccess){
                    connection.commit();
                }
                else {
                    connection.rollback();
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return isSuccess;
    }

    @Override
    public boolean removeCustomer(String name, String email) {

        if (!isCustomerExist(name, email)) {
            return false;
        }

        int rows = 0;
        String sql = "DELETE FROM CUSTOMER WHERE name = '" + name + "' AND email = '" + email + "'";

        try (Connection connection = getConnection().orElseThrow(SQLException::new)) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                rows = preparedStatement.executeUpdate();
                connection.commit();
            }
            catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return rows > 0;
    }

    @Override
    public List<Customer> getAll() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT id,name,eMail FROM CUSTOMER;";

        try (Connection connection = getConnection().orElseThrow(SQLException::new)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Customer customer = new Customer();
                        customer.setId(resultSet.getLong("id"));
                        customer.setName(resultSet.getString("name"));
                        customer.setEMail(resultSet.getString("eMail"));

                        customers.add(customer);
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return customers;
    }

    @Override
    public Optional<Customer> findCustomer(String name, String email) {
        Customer customer = null;
        String sql = "SELECT id, name, email " +
                "FROM CUSTOMER WHERE name = '" + name + "' AND email = '" + email + "' LIMIT 1";
        try (Connection connection = getConnection().orElseThrow(SQLException::new)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        customer = new Customer();
                        customer.setId(resultSet.getLong("id"));
                        customer.setName(resultSet.getString("name"));
                        customer.setEMail(resultSet.getString("email"));
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return Optional.ofNullable(customer);
    }

    public boolean isCustomerExist(String name, String email) {
        Optional<Customer> optionalCustomer = findCustomer(name, email);
        return optionalCustomer.isPresent();
    }

    private Optional<Connection> getConnection() {
        try {
            return Optional.ofNullable(DriverManager.getConnection(DB_URL, USER, PASS));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

}


