package com.store.comehere.domain.core.customers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.time.*;

@Repository("customersRepository")
public interface ICustomersRepository extends JpaRepository<Customers, Integer>,QuerydslPredicateExecutor<Customers> {

    
}

