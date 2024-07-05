package com.store.comehere.domain.core.orderitems;
import javax.persistence.*;
import java.time.*;
import java.math.BigDecimal;
import com.store.comehere.domain.core.orders.Orders;
import com.store.comehere.domain.core.products.Products;
import com.store.comehere.domain.core.abstractentity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.querydsl.core.annotations.Config;
import org.hibernate.annotations.TypeDefs;


@Entity
@Config(defaultVariableName = "orderItemsEntity")
@Table(name = "order_items")
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@TypeDefs({
}) 
public class OrderItems extends AbstractEntity {

    @Id
    @EqualsAndHashCode.Include() 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id", nullable = false)
    private Integer orderItemId;
    
    @Basic
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    
    @Basic
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Orders orders;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Products products;


}



