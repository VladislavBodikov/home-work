package com.sbrf.reboot.repository.impl;

import com.sbrf.reboot.dto.Customer;
import com.sbrf.reboot.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CustomerH2RepositoryTest {

    private static CustomerRepository customerRepository;

    @BeforeAll
    public static void before() {
        customerRepository = new CustomerH2Repository();
    }

    @Test
    void getAll() {
        boolean tomCreated = customerRepository.createCustomer("Tom", "tom@ya.ru");

        List<Customer> all = customerRepository.getAll();

        assertTrue(all.size() != 0);
    }

    @Test
    void createCustomer() {

        boolean mariaCreated = customerRepository.createCustomer("Maria", "maria98@ya.ru");
        boolean mariaRemove = customerRepository.removeCustomer("Maria", "maria98@ya.ru");
        assertAll(
                () -> assertTrue(mariaCreated),
                () -> assertTrue(mariaRemove)
        );
    }

    @Test
    void createCustomerDuplicate() {

        boolean mariaCreatedOne = customerRepository.createCustomer("Maria", "maria98@ya.ru");
        boolean mariaCreatedTwo = customerRepository.createCustomer("Maria", "maria98@ya.ru");
        boolean mariaRemove = customerRepository.removeCustomer("Maria", "maria98@ya.ru");
        assertAll(
                () -> assertTrue(mariaCreatedOne),
                () -> assertFalse(mariaCreatedTwo)
        );

    }

    @Test
    void findCustomerSuccess() {
        boolean mariaCreated = customerRepository.createCustomer("Maria", "maria98@ya.ru");
        Optional<Customer> optionalCustomer = customerRepository.findCustomer("Maria", "maria98@ya.ru");

        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            boolean mariaRemove = customerRepository.removeCustomer("Maria", "maria98@ya.ru");
            assertEquals(customer.getEMail(), "maria98@ya.ru");
        }
    }

    @Test
    void findCustomerFailure() {
        Optional<Customer> optionalCustomer = customerRepository.findCustomer("Vlad", "k@ya.ru");

        assertFalse(optionalCustomer.isPresent());
    }

    @Test
    void createCustomerAllTest() {
        Customer alex = new Customer();
        alex.setName("alex");
        alex.setEMail("alex@ya.ru");

        Customer john = new Customer();
        john.setName("john");
        john.setEMail("john@mail.ru");

        Customer german = new Customer();
        german.setName("german");
        german.setEMail("german@gmail.ru");


        List<Customer> customers = new ArrayList<>();
        customers.add(alex);
        customers.add(john);
        customers.add(german);

        boolean isSuccess = customerRepository.createCustomerAll(customers);
        assertTrue(isSuccess);

    }
}