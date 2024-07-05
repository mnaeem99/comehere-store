package com.store.comehere.domain.core.inventory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.time.*;

@Repository("inventoryRepository")
public interface IInventoryRepository extends JpaRepository<Inventory, Integer>,QuerydslPredicateExecutor<Inventory> {

    
}

