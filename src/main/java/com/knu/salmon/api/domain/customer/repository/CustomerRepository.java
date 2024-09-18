package com.knu.salmon.api.domain.customer.repository;

import com.knu.salmon.api.domain.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
