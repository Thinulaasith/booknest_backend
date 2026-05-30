package com.booknest.services;

import com.booknest.entities.Customer;
import com.booknest.model.CustomerLoginRequest;
import com.booknest.model.CustomerLoginResponse;
import com.booknest.model.CustomerRequest;
import com.booknest.model.CustomerResponse;
import com.booknest.repositories.CustomerRepository;
import com.booknest.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public CustomerResponse registerCustomer(CustomerRequest request) {
        log.info("Registering new customer with email: {}", request.getEmail());

        if (customerRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException(
                    "Email is already registered: " + request.getEmail());
        }

        Customer customer = Customer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail().toLowerCase().trim())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .isActive(true)
                .build();

        Customer saved = customerRepository.save(customer);
        log.info("Customer registered successfully with id: {}", saved.getId());
        return mapToResponse(saved);
    }


    @Transactional(readOnly = true)
    public CustomerResponse getCustomerById(Long id) {
        log.info("Fetching customer by id: {}", id);
        Customer customer = findActiveCustomerById(id);
        return mapToResponse(customer);
    }

    @Transactional(readOnly = true)
    public CustomerResponse getCustomerByEmail(String email) {
        log.info("Fetching customer by email: {}", email);
        Customer customer = customerRepository.findByEmail(email.toLowerCase().trim())
                .orElseThrow(() -> new RuntimeException(
                        "Customer not found with email: " + email));
        return mapToResponse(customer);
    }

    @Transactional(readOnly = true)
    public List<CustomerResponse> getAllActiveCustomers() {
        log.info("Fetching all active customers");
        return customerRepository.findAllByIsActiveTrue()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }


    public CustomerResponse updateCustomer(Long id, CustomerRequest request) {
        log.info("Updating customer with id: {}", id);
        Customer customer = findActiveCustomerById(id);

        // If email is changing, make sure new email is not already taken
        String newEmail = request.getEmail().toLowerCase().trim();
        if (!customer.getEmail().equals(newEmail)
                && customerRepository.existsByEmail(newEmail)) {
            throw new IllegalArgumentException("Email already in use: " + newEmail);
        }

        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(newEmail);
        customer.setPhoneNumber(request.getPhoneNumber());

        // Only re-encode password if a new one is provided
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            customer.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        Customer updated = customerRepository.save(customer);
        log.info("Customer updated successfully with id: {}", updated.getId());
        return mapToResponse(updated);
    }


    public void deactivateCustomer(Long id) {
        log.info("Deactivating customer with id: {}", id);
        Customer customer = findActiveCustomerById(id);
        customer.setActive(false);
        customerRepository.save(customer);
        log.info("Customer deactivated successfully with id: {}", id);
    }


    private Customer findActiveCustomerById(Long id) {
        return customerRepository.findById(id)
                .filter(Customer::isActive)
                .orElseThrow(() -> new RuntimeException(
                        "Customer not found or inactive with id: " + id));
    }

    private CustomerResponse mapToResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .phoneNumber(customer.getPhoneNumber())
                .isActive(customer.isActive())
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .build();
    }


    public CustomerLoginResponse login(CustomerLoginRequest request) {

        Customer customer = customerRepository
                .findByEmail(request.getEmail().toLowerCase().trim())
                .orElseThrow(() ->
                        new RuntimeException("Invalid email or password"));

        if (!customer.isActive()) {
            throw new RuntimeException("Customer account is inactive");
        }

        if (!passwordEncoder.matches(
                request.getPassword(),
                customer.getPassword())) {

            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(customer.getEmail());

        return CustomerLoginResponse.builder()
                .token(token)
                .type("Bearer")
                .customerId(customer.getId())
                .email(customer.getEmail())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .build();
    }
}