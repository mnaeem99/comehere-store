package com.store.comehere.domain.core.orderitems;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.time.*;

@Repository("orderItemsRepository")
public interface IOrderItemsRepository extends JpaRepository<OrderItems, Integer>,QuerydslPredicateExecutor<OrderItems> {

    
}

