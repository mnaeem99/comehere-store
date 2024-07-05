package com.store.comehere.application.core.suppliers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import com.store.comehere.application.core.suppliers.dto.*;
import com.store.comehere.domain.core.suppliers.ISuppliersRepository;
import com.store.comehere.domain.core.suppliers.QSuppliers;
import com.store.comehere.domain.core.suppliers.Suppliers;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page; 
import org.springframework.data.domain.Pageable; 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;
import com.store.comehere.commons.search.*;
import com.store.comehere.commons.logging.LoggingHelper;
import com.querydsl.core.BooleanBuilder;
import java.net.MalformedURLException;
import java.time.*;
import java.util.*;
import javax.persistence.EntityNotFoundException;

@Service("suppliersAppService")
@RequiredArgsConstructor
public class SuppliersAppService implements ISuppliersAppService {
    
	@Qualifier("suppliersRepository")
	@NonNull protected final ISuppliersRepository _suppliersRepository;

	
	@Qualifier("ISuppliersMapperImpl")
	@NonNull protected final ISuppliersMapper mapper;

	@NonNull protected final LoggingHelper logHelper;

    @Transactional(propagation = Propagation.REQUIRED)
	public CreateSuppliersOutput create(CreateSuppliersInput input) {

		Suppliers suppliers = mapper.createSuppliersInputToSuppliers(input);

		Suppliers createdSuppliers = _suppliersRepository.save(suppliers);
		return mapper.suppliersToCreateSuppliersOutput(createdSuppliers);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public UpdateSuppliersOutput update(Integer suppliersId, UpdateSuppliersInput input) {

		Suppliers existing = _suppliersRepository.findById(suppliersId).orElseThrow(() -> new EntityNotFoundException("Suppliers not found"));

		Suppliers suppliers = mapper.updateSuppliersInputToSuppliers(input);
		suppliers.setInventorysSet(existing.getInventorysSet());
		
		Suppliers updatedSuppliers = _suppliersRepository.save(suppliers);
		return mapper.suppliersToUpdateSuppliersOutput(updatedSuppliers);
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Integer suppliersId) {

		Suppliers existing = _suppliersRepository.findById(suppliersId).orElseThrow(() -> new EntityNotFoundException("Suppliers not found"));
	 	
        if(existing !=null) {
			_suppliersRepository.delete(existing);
		}
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public FindSuppliersByIdOutput findById(Integer suppliersId) {

		Suppliers foundSuppliers = _suppliersRepository.findById(suppliersId).orElse(null);
		if (foundSuppliers == null)  
			return null; 
 	   
 	    return mapper.suppliersToFindSuppliersByIdOutput(foundSuppliers);
	}

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<FindSuppliersByIdOutput> find(SearchCriteria search, Pageable pageable) throws MalformedURLException  {

		Page<Suppliers> foundSuppliers = _suppliersRepository.findAll(search(search), pageable);
		List<Suppliers> suppliersList = foundSuppliers.getContent();
		Iterator<Suppliers> suppliersIterator = suppliersList.iterator(); 
		List<FindSuppliersByIdOutput> output = new ArrayList<>();

		while (suppliersIterator.hasNext()) {
		Suppliers suppliers = suppliersIterator.next();
 	    output.add(mapper.suppliersToFindSuppliersByIdOutput(suppliers));
		}
		return output;
	}
	
	protected BooleanBuilder search(SearchCriteria search) throws MalformedURLException {

		QSuppliers suppliers= QSuppliers.suppliersEntity;
		if(search != null) {
			Map<String,SearchFields> map = new HashMap<>();
			for(SearchFields fieldDetails: search.getFields())
			{
				map.put(fieldDetails.getFieldName(),fieldDetails);
			}
			List<String> keysList = new ArrayList<String>(map.keySet());
			checkProperties(keysList);
			return searchKeyValuePair(suppliers, map,search.getJoinColumns());
		}
		return null;
	}
	
	protected void checkProperties(List<String> list) throws MalformedURLException  {
		for (int i = 0; i < list.size(); i++) {
			if(!(
				list.get(i).replace("%20","").trim().equals("address") ||
				list.get(i).replace("%20","").trim().equals("contactEmail") ||
				list.get(i).replace("%20","").trim().equals("contactName") ||
				list.get(i).replace("%20","").trim().equals("contactPhone") ||
				list.get(i).replace("%20","").trim().equals("createdAt") ||
				list.get(i).replace("%20","").trim().equals("name") ||
				list.get(i).replace("%20","").trim().equals("supplierId") ||
				list.get(i).replace("%20","").trim().equals("updatedAt")
			)) 
			{
			 throw new MalformedURLException("Wrong URL Format: Property " + list.get(i) + " not found!" );
			}
		}
	}
	
	protected BooleanBuilder searchKeyValuePair(QSuppliers suppliers, Map<String,SearchFields> map,Map<String,String> joinColumns) {
		BooleanBuilder builder = new BooleanBuilder();
        
		Iterator<Map.Entry<String, SearchFields>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, SearchFields> details = iterator.next();

            if(details.getKey().replace("%20","").trim().equals("address")) {
				if(details.getValue().getOperator().equals("contains")) {
					builder.and(suppliers.address.likeIgnoreCase("%"+ details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals")) {
					builder.and(suppliers.address.eq(details.getValue().getSearchValue()));
				} else if(details.getValue().getOperator().equals("notEqual")) {
					builder.and(suppliers.address.ne(details.getValue().getSearchValue()));
				}
			}
            if(details.getKey().replace("%20","").trim().equals("contactEmail")) {
				if(details.getValue().getOperator().equals("contains")) {
					builder.and(suppliers.contactEmail.likeIgnoreCase("%"+ details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals")) {
					builder.and(suppliers.contactEmail.eq(details.getValue().getSearchValue()));
				} else if(details.getValue().getOperator().equals("notEqual")) {
					builder.and(suppliers.contactEmail.ne(details.getValue().getSearchValue()));
				}
			}
            if(details.getKey().replace("%20","").trim().equals("contactName")) {
				if(details.getValue().getOperator().equals("contains")) {
					builder.and(suppliers.contactName.likeIgnoreCase("%"+ details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals")) {
					builder.and(suppliers.contactName.eq(details.getValue().getSearchValue()));
				} else if(details.getValue().getOperator().equals("notEqual")) {
					builder.and(suppliers.contactName.ne(details.getValue().getSearchValue()));
				}
			}
            if(details.getKey().replace("%20","").trim().equals("contactPhone")) {
				if(details.getValue().getOperator().equals("contains")) {
					builder.and(suppliers.contactPhone.likeIgnoreCase("%"+ details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals")) {
					builder.and(suppliers.contactPhone.eq(details.getValue().getSearchValue()));
				} else if(details.getValue().getOperator().equals("notEqual")) {
					builder.and(suppliers.contactPhone.ne(details.getValue().getSearchValue()));
				}
			}
			if(details.getKey().replace("%20","").trim().equals("createdAt")) {
				if(details.getValue().getOperator().equals("equals") && SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) !=null) {
					builder.and(suppliers.createdAt.eq(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) !=null) {
					builder.and(suppliers.createdAt.ne(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   LocalDateTime startLocalDateTime= SearchUtils.stringToLocalDateTime(details.getValue().getStartingValue());
				   LocalDateTime endLocalDateTime= SearchUtils.stringToLocalDateTime(details.getValue().getEndingValue());
				   if(startLocalDateTime!=null && endLocalDateTime!=null) {
					   builder.and(suppliers.createdAt.between(startLocalDateTime,endLocalDateTime));
				   } else if(endLocalDateTime!=null) {
					   builder.and(suppliers.createdAt.loe(endLocalDateTime));
                   } else if(startLocalDateTime!=null) {
                	   builder.and(suppliers.createdAt.goe(startLocalDateTime));  
                   }
                }     
			}
            if(details.getKey().replace("%20","").trim().equals("name")) {
				if(details.getValue().getOperator().equals("contains")) {
					builder.and(suppliers.name.likeIgnoreCase("%"+ details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals")) {
					builder.and(suppliers.name.eq(details.getValue().getSearchValue()));
				} else if(details.getValue().getOperator().equals("notEqual")) {
					builder.and(suppliers.name.ne(details.getValue().getSearchValue()));
				}
			}
			if(details.getKey().replace("%20","").trim().equals("supplierId")) {
				if(details.getValue().getOperator().equals("contains") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(suppliers.supplierId.like(details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(suppliers.supplierId.eq(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(suppliers.supplierId.ne(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   if(StringUtils.isNumeric(details.getValue().getStartingValue()) && StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(suppliers.supplierId.between(Integer.valueOf(details.getValue().getStartingValue()), Integer.valueOf(details.getValue().getEndingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getStartingValue())) {
                	   builder.and(suppliers.supplierId.goe(Integer.valueOf(details.getValue().getStartingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(suppliers.supplierId.loe(Integer.valueOf(details.getValue().getEndingValue())));
				   }
				}
			}
			if(details.getKey().replace("%20","").trim().equals("updatedAt")) {
				if(details.getValue().getOperator().equals("equals") && SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) !=null) {
					builder.and(suppliers.updatedAt.eq(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) !=null) {
					builder.and(suppliers.updatedAt.ne(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   LocalDateTime startLocalDateTime= SearchUtils.stringToLocalDateTime(details.getValue().getStartingValue());
				   LocalDateTime endLocalDateTime= SearchUtils.stringToLocalDateTime(details.getValue().getEndingValue());
				   if(startLocalDateTime!=null && endLocalDateTime!=null) {
					   builder.and(suppliers.updatedAt.between(startLocalDateTime,endLocalDateTime));
				   } else if(endLocalDateTime!=null) {
					   builder.and(suppliers.updatedAt.loe(endLocalDateTime));
                   } else if(startLocalDateTime!=null) {
                	   builder.and(suppliers.updatedAt.goe(startLocalDateTime));  
                   }
                }     
			}
	    
		}
		
		return builder;
	}
	
	public Map<String,String> parseInventorysJoinColumn(String keysString) {
		
		Map<String,String> joinColumnMap = new HashMap<String,String>();
		joinColumnMap.put("supplierId", keysString);
		  
		return joinColumnMap;
	}
    
}



