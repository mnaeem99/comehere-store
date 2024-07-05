package com.store.comehere.domain.core.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.time.*;

@Repository("productsRepository")
public interface IProductsRepository extends JpaRepository<Products, Integer>,QuerydslPredicateExecutor<Products> {

    
}

