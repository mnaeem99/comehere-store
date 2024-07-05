package com.store.comehere.domain.core.orders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.time.*;

@Repository("ordersRepository")
public interface IOrdersRepository extends JpaRepository<Orders, Integer>,QuerydslPredicateExecutor<Orders> {

    
}

