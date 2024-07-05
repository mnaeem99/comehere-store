package com.store.comehere.domain.core.suppliers;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.time.*;
import com.store.comehere.domain.core.inventory.Inventory;
import com.store.comehere.domain.core.abstractentity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.querydsl.core.annotations.Config;
import org.hibernate.annotations.TypeDefs;


@Entity
@Config(defaultVariableName = "suppliersEntity")
@Table(name = "suppliers")
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@TypeDefs({
}) 
public class Suppliers extends AbstractEntity {

    @Basic
    @Column(name = "address", nullable = true)
    private String address;

    @Basic
    @Column(name = "contact_email", nullable = true,length =100)
    private String contactEmail;

    @Basic
    @Column(name = "contact_name", nullable = true,length =50)
    private String contactName;

    @Basic
    @Column(name = "contact_phone", nullable = true,length =15)
    private String contactPhone;

    @Basic
    @Column(name = "created_at", nullable = true)
    private LocalDateTime createdAt;

    @Basic
    @Column(name = "name", nullable = false,length =100)
    private String name;

    @Id
    @EqualsAndHashCode.Include() 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplier_id", nullable = false)
    private Integer supplierId;
    
    @Basic
    @Column(name = "updated_at", nullable = true)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "suppliers", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Inventory> inventorysSet = new HashSet<Inventory>();
    
    public void addInventorys(Inventory inventorys) {        
    	inventorysSet.add(inventorys);

        inventorys.setSuppliers(this);
    }
    public void removeInventorys(Inventory inventorys) {
        inventorysSet.remove(inventorys);
        
        inventorys.setSuppliers(null);
    }
    

}



