package com.itamar.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itamar.entities.Customer;

import java.util.Optional;

public interface CustomerDao extends JpaRepository<Customer, Long>{

    Optional<Customer> findByEmailAndPassword(String email, String password);
}
