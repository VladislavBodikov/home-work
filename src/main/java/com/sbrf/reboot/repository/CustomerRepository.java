package com.sbrf.reboot.repository;

import com.sbrf.reboot.dto.Customer;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {

    boolean createCustomer(@NonNull String userName, @NonNull String eMail);

    boolean createCustomerAll(@NonNull List<Customer> customers);

    boolean removeCustomer(@NonNull String userName, @NonNull String eMail);

    List<Customer> getAll();

    Optional<Customer> findCustomer(@NonNull String name,@NonNull String email);

}
