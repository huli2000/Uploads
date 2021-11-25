package com.itamar.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itamar.entities.Company;

import java.util.Optional;

public interface CompanyRepo extends JpaRepository<Company, Long>{

    Optional<Company> findByEmailAndPassword(String email, String password);
}
