package com.store.comehere.domain.core.suppliers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.time.*;

@Repository("suppliersRepository")
public interface ISuppliersRepository extends JpaRepository<Suppliers, Integer>,QuerydslPredicateExecutor<Suppliers> {

    
}

