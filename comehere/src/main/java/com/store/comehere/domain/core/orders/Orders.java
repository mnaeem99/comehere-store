package com.store.comehere.domain.core.orders;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.time.*;
import java.math.BigDecimal;
import com.store.comehere.domain.core.customers.Customers;
import com.store.comehere.domain.core.orderitems.OrderItems;
import com.store.comehere.domain.core.abstractentity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.querydsl.core.annotations.Config;
import org.hibernate.annotations.TypeDefs;


@Entity
@Config(defaultVariableName = "ordersEntity")
@Table(name = "orders")
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@TypeDefs({
}) 
public class Orders extends AbstractEntity {

    @Basic
    @Column(name = "order_date", nullable = true)
    private LocalDateTime orderDate;

    @Id
    @EqualsAndHashCode.Include() 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    private Integer orderId;
    
    @Basic
    @Column(name = "status", nullable = false,length =50)
    private String status;

    @Basic
    @Column(name = "total", nullable = false)
    private BigDecimal total;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customers customers;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItems> orderItemsSet = new HashSet<OrderItems>();
    
    public void addOrderItems(OrderItems orderItems) {        
    	orderItemsSet.add(orderItems);

        orderItems.setOrders(this);
    }
    public void removeOrderItems(OrderItems orderItems) {
        orderItemsSet.remove(orderItems);
        
        orderItems.setOrders(null);
    }
    

}



