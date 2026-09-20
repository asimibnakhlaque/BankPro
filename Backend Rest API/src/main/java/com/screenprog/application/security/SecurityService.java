package com.screenprog.application.security;

import org.springframework.stereotype.Service;

import com.screenprog.application.model.Customer;
import com.screenprog.application.repo.AccountRepository;
import com.screenprog.application.repo.CustomerRepository;

import java.util.Objects;
import java.util.Optional;

@Service
public class SecurityService {
    private final CustomerRepository customerRepository;

    public SecurityService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public boolean ownsAccount(String username, Long accountNumber) {
        Customer byEmail = customerRepository.findByCustomerID(Long.valueOf(username));
        return byEmail != null && byEmail.getAccount()
                .stream()
                .anyMatch(account -> account.getAccountNumber().equals(accountNumber));
    }

    public boolean ownsCustomerId(String username, Long customerId){
        return Long.valueOf(username).equals(customerId);
    }
    public boolean ownsUsername(String authUsername, String sentUsername){
        return authUsername.equals(sentUsername);
    }

}
