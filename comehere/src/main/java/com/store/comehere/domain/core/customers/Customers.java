package com.store.comehere.domain.core.customers;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.time.*;
import com.store.comehere.domain.core.orders.Orders;
import com.store.comehere.domain.core.abstractentity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.querydsl.core.annotations.Config;
import org.hibernate.annotations.TypeDefs;


@Entity
@Config(defaultVariableName = "customersEntity")
@Table(name = "customers")
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@TypeDefs({
}) 
public class Customers extends AbstractEntity {

    @Basic
    @Column(name = "address", nullable = true)
    private String address;

    @Basic
    @Column(name = "created_at", nullable = true)
    private LocalDateTime createdAt;

    @Id
    @EqualsAndHashCode.Include() 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id", nullable = false)
    private Integer customerId;
    
    @Basic
    @Column(name = "email", nullable = false,length =100)
    private String email;

    @Basic
    @Column(name = "first_name", nullable = false,length =50)
    private String firstName;

    @Basic
    @Column(name = "last_name", nullable = false,length =50)
    private String lastName;

    @Basic
    @Column(name = "phone", nullable = true,length =15)
    private String phone;

    @Basic
    @Column(name = "updated_at", nullable = true)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "customers", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Orders> ordersSet = new HashSet<Orders>();
    
    public void addOrders(Orders orders) {        
    	ordersSet.add(orders);

        orders.setCustomers(this);
    }
    public void removeOrders(Orders orders) {
        ordersSet.remove(orders);
        
        orders.setCustomers(null);
    }
    

}



