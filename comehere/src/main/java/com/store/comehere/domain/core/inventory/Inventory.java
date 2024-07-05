package com.store.comehere.domain.core.inventory;
import javax.persistence.*;
import java.time.*;
import com.store.comehere.domain.core.products.Products;
import com.store.comehere.domain.core.suppliers.Suppliers;
import com.store.comehere.domain.core.abstractentity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.querydsl.core.annotations.Config;
import org.hibernate.annotations.TypeDefs;


@Entity
@Config(defaultVariableName = "inventoryEntity")
@Table(name = "inventory")
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@TypeDefs({
}) 
public class Inventory extends AbstractEntity {

    @Id
    @EqualsAndHashCode.Include() 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id", nullable = false)
    private Integer inventoryId;
    
    @Basic
    @Column(name = "last_restocked", nullable = true)
    private LocalDateTime lastRestocked;

    @Basic
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Products products;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Suppliers suppliers;


}



