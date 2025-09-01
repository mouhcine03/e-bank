package com.mouhcine.ebankbackend.repositories;

import com.mouhcine.ebankbackend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
