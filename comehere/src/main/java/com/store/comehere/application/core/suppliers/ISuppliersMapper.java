package com.store.comehere.application.core.suppliers;

import org.mapstruct.Mapper;
import com.store.comehere.application.core.suppliers.dto.*;
import com.store.comehere.domain.core.suppliers.Suppliers;
import java.time.*;

@Mapper(componentModel = "spring")
public interface ISuppliersMapper {
   Suppliers createSuppliersInputToSuppliers(CreateSuppliersInput suppliersDto);
   CreateSuppliersOutput suppliersToCreateSuppliersOutput(Suppliers entity);
   
    Suppliers updateSuppliersInputToSuppliers(UpdateSuppliersInput suppliersDto);
    
   	UpdateSuppliersOutput suppliersToUpdateSuppliersOutput(Suppliers entity);
   	FindSuppliersByIdOutput suppliersToFindSuppliersByIdOutput(Suppliers entity);


}

