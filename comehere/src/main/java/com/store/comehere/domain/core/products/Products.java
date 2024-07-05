package com.store.comehere.domain.core.products;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.time.*;
import java.math.BigDecimal;
import com.store.comehere.domain.core.inventory.Inventory;
import com.store.comehere.domain.core.orderitems.OrderItems;
import com.store.comehere.domain.core.abstractentity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.querydsl.core.annotations.Config;
import org.hibernate.annotations.TypeDefs;


@Entity
@Config(defaultVariableName = "productsEntity")
@Table(name = "products")
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@TypeDefs({
}) 
public class Products extends AbstractEntity {

    @Basic
    @Column(name = "category", nullable = true,length =50)
    private String category;

    @Basic
    @Column(name = "created_at", nullable = true)
    private LocalDateTime createdAt;

    @Basic
    @Column(name = "description", nullable = true)
    private String description;

    @Basic
    @Column(name = "name", nullable = false,length =100)
    private String name;

    @Basic
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    
    @Id
    @EqualsAndHashCode.Include() 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private Integer productId;
    
    @Basic
    @Column(name = "updated_at", nullable = true)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "products", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Inventory> inventorysSet = new HashSet<Inventory>();
    
    public void addInventorys(Inventory inventorys) {        
    	inventorysSet.add(inventorys);

        inventorys.setProducts(this);
    }
    public void removeInventorys(Inventory inventorys) {
        inventorysSet.remove(inventorys);
        
        inventorys.setProducts(null);
    }
    
    @OneToMany(mappedBy = "products", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItems> orderItemsSet = new HashSet<OrderItems>();
    
    public void addOrderItems(OrderItems orderItems) {        
    	orderItemsSet.add(orderItems);

        orderItems.setProducts(this);
    }
    public void removeOrderItems(OrderItems orderItems) {
        orderItemsSet.remove(orderItems);
        
        orderItems.setProducts(null);
    }
    

}



