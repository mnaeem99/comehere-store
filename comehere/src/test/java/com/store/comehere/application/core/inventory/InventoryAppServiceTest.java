package com.store.comehere.application.core.inventory;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doNothing;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.store.comehere.domain.core.inventory.*;
import com.store.comehere.commons.search.*;
import com.store.comehere.application.core.inventory.dto.*;
import com.store.comehere.domain.core.inventory.QInventory;
import com.store.comehere.domain.core.inventory.Inventory;

import com.store.comehere.domain.core.products.Products;
import com.store.comehere.domain.core.products.IProductsRepository;
import com.store.comehere.domain.core.suppliers.Suppliers;
import com.store.comehere.domain.core.suppliers.ISuppliersRepository;
import com.store.comehere.commons.logging.LoggingHelper;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import java.time.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class InventoryAppServiceTest {

	@InjectMocks
	@Spy
	protected InventoryAppService _appService;
	@Mock
	protected IInventoryRepository _inventoryRepository;
	
    @Mock
	protected IProductsRepository _productsRepository;

    @Mock
	protected ISuppliersRepository _suppliersRepository;

	@Mock
	protected IInventoryMapper _mapper;

	@Mock
	protected Logger loggerMock;

	@Mock
	protected LoggingHelper logHelper;
	
    protected static Integer ID=15;
	 
	@Before
	public void setUp() {

		MockitoAnnotations.initMocks(_appService);
		when(logHelper.getLogger()).thenReturn(loggerMock);
		doNothing().when(loggerMock).error(anyString());
	}
	
	@Test
	public void findInventoryById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
		Optional<Inventory> nullOptional = Optional.ofNullable(null);
		Mockito.when(_inventoryRepository.findById(any(Integer.class))).thenReturn(nullOptional);
		Assertions.assertThat(_appService.findById(ID )).isEqualTo(null);
	}
	
	@Test
	public void findInventoryById_IdIsNotNullAndIdExists_ReturnInventory() {

		Inventory inventory = mock(Inventory.class);
		Optional<Inventory> inventoryOptional = Optional.of((Inventory) inventory);
		Mockito.when(_inventoryRepository.findById(any(Integer.class))).thenReturn(inventoryOptional);
		
	    Assertions.assertThat(_appService.findById(ID )).isEqualTo(_mapper.inventoryToFindInventoryByIdOutput(inventory));
	}
	
	
	@Test 
    public void createInventory_InventoryIsNotNullAndInventoryDoesNotExist_StoreInventory() { 
 
        Inventory inventoryEntity = mock(Inventory.class); 
    	CreateInventoryInput inventoryInput = new CreateInventoryInput();
		
        Products products = mock(Products.class);
		Optional<Products> productsOptional = Optional.of((Products) products);
        inventoryInput.setProductId(15);
		
        Mockito.when(_productsRepository.findById(any(Integer.class))).thenReturn(productsOptional);
        
		
        Suppliers suppliers = mock(Suppliers.class);
		Optional<Suppliers> suppliersOptional = Optional.of((Suppliers) suppliers);
        inventoryInput.setSupplierId(15);
		
        Mockito.when(_suppliersRepository.findById(any(Integer.class))).thenReturn(suppliersOptional);
        
		
        Mockito.when(_mapper.createInventoryInputToInventory(any(CreateInventoryInput.class))).thenReturn(inventoryEntity); 
        Mockito.when(_inventoryRepository.save(any(Inventory.class))).thenReturn(inventoryEntity);

	   	Assertions.assertThat(_appService.create(inventoryInput)).isEqualTo(_mapper.inventoryToCreateInventoryOutput(inventoryEntity));

    } 
	@Test
	public void createInventory_InventoryIsNotNullAndInventoryDoesNotExistAndChildIsNullAndChildIsMandatory_ReturnNull() {

		CreateInventoryInput inventory = mock(CreateInventoryInput.class);
		
		Mockito.when(_mapper.createInventoryInputToInventory(any(CreateInventoryInput.class))).thenReturn(null); 
		Assertions.assertThat(_appService.create(inventory)).isEqualTo(null);
	}
	
	@Test
	public void createInventory_InventoryIsNotNullAndInventoryDoesNotExistAndChildIsNotNullAndChildIsMandatoryAndFindByIdIsNull_ReturnNull() {

		CreateInventoryInput inventory = new CreateInventoryInput();
	    
        inventory.setProductId(15);
     
     	Optional<Products> nullOptional = Optional.ofNullable(null);
        Mockito.when(_productsRepository.findById(any(Integer.class))).thenReturn(nullOptional);
        
//		Mockito.when(_productsRepository.findById(any(Integer.class))).thenReturn(nullOptional);
		Assertions.assertThat(_appService.create(inventory)).isEqualTo(null);
    }
    
    @Test
	public void updateInventory_InventoryIsNotNullAndInventoryDoesNotExistAndChildIsNullAndChildIsMandatory_ReturnNull() {

		UpdateInventoryInput inventoryInput = mock(UpdateInventoryInput.class);
		Inventory inventory = mock(Inventory.class); 
		
     	Optional<Inventory> inventoryOptional = Optional.of((Inventory) inventory);
		Mockito.when(_inventoryRepository.findById(any(Integer.class))).thenReturn(inventoryOptional);
		
		Mockito.when(_mapper.updateInventoryInputToInventory(any(UpdateInventoryInput.class))).thenReturn(inventory); 
		Assertions.assertThat(_appService.update(ID,inventoryInput)).isEqualTo(null);
	}
	
	@Test
	public void updateInventory_InventoryIsNotNullAndInventoryDoesNotExistAndChildIsNotNullAndChildIsMandatoryAndFindByIdIsNull_ReturnNull() {
		
		UpdateInventoryInput inventoryInput = new UpdateInventoryInput();
        inventoryInput.setProductId(15);
     
    	Inventory inventory = mock(Inventory.class);
		
     	Optional<Inventory> inventoryOptional = Optional.of((Inventory) inventory);
		Mockito.when(_inventoryRepository.findById(any(Integer.class))).thenReturn(inventoryOptional);
		
		Mockito.when(_mapper.updateInventoryInputToInventory(any(UpdateInventoryInput.class))).thenReturn(inventory);
     	Optional<Products> nullOptional = Optional.ofNullable(null);
        Mockito.when(_productsRepository.findById(any(Integer.class))).thenReturn(nullOptional);
        
	//	Mockito.when(_productsRepository.findById(any(Integer.class))).thenReturn(nullOptional);
		Assertions.assertThat(_appService.update(ID,inventoryInput)).isEqualTo(null);
	}

		
	@Test
	public void updateInventory_InventoryIdIsNotNullAndIdExists_ReturnUpdatedInventory() {

		Inventory inventoryEntity = mock(Inventory.class);
		UpdateInventoryInput inventory= mock(UpdateInventoryInput.class);
		
		Optional<Inventory> inventoryOptional = Optional.of((Inventory) inventoryEntity);
		Mockito.when(_inventoryRepository.findById(any(Integer.class))).thenReturn(inventoryOptional);
	 		
		Mockito.when(_mapper.updateInventoryInputToInventory(any(UpdateInventoryInput.class))).thenReturn(inventoryEntity);
		Mockito.when(_inventoryRepository.save(any(Inventory.class))).thenReturn(inventoryEntity);
		Assertions.assertThat(_appService.update(ID,inventory)).isEqualTo(_mapper.inventoryToUpdateInventoryOutput(inventoryEntity));
	}
    
	@Test
	public void deleteInventory_InventoryIsNotNullAndInventoryExists_InventoryRemoved() {

		Inventory inventory = mock(Inventory.class);
		Optional<Inventory> inventoryOptional = Optional.of((Inventory) inventory);
		Mockito.when(_inventoryRepository.findById(any(Integer.class))).thenReturn(inventoryOptional);
 		
		_appService.delete(ID); 
		verify(_inventoryRepository).delete(inventory);
	}
	
	@Test
	public void find_ListIsEmpty_ReturnList() throws Exception {

		List<Inventory> list = new ArrayList<>();
		Page<Inventory> foundPage = new PageImpl(list);
		Pageable pageable = mock(Pageable.class);
		List<FindInventoryByIdOutput> output = new ArrayList<>();
		SearchCriteria search= new SearchCriteria();

		Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
		Mockito.when(_inventoryRepository.findAll(any(Predicate.class),any(Pageable.class))).thenReturn(foundPage);
		Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
	}
	
	@Test
	public void find_ListIsNotEmpty_ReturnList() throws Exception {

		List<Inventory> list = new ArrayList<>();
		Inventory inventory = mock(Inventory.class);
		list.add(inventory);
    	Page<Inventory> foundPage = new PageImpl(list);
		Pageable pageable = mock(Pageable.class);
		List<FindInventoryByIdOutput> output = new ArrayList<>();
        SearchCriteria search= new SearchCriteria();

		output.add(_mapper.inventoryToFindInventoryByIdOutput(inventory));
		
		Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
    	Mockito.when(_inventoryRepository.findAll(any(Predicate.class),any(Pageable.class))).thenReturn(foundPage);
		Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
	}
	
	@Test
	public void searchKeyValuePair_PropertyExists_ReturnBooleanBuilder() {
		QInventory inventory = QInventory.inventoryEntity;
	    SearchFields searchFields = new SearchFields();
		searchFields.setOperator("equals");
		searchFields.setSearchValue("xyz");
	    Map<String,SearchFields> map = new HashMap<>();
		Map<String,String> searchMap = new HashMap<>();
        searchMap.put("xyz",String.valueOf(ID));
		BooleanBuilder builder = new BooleanBuilder();
		Assertions.assertThat(_appService.searchKeyValuePair(inventory,map,searchMap)).isEqualTo(builder);
	}
	
	@Test (expected = Exception.class)
	public void checkProperties_PropertyDoesNotExist_ThrowException() throws Exception {
		List<String> list = new ArrayList<>();
		list.add("xyz");
		_appService.checkProperties(list);
	}
	
	@Test
	public void checkProperties_PropertyExists_ReturnNothing() throws Exception {
		List<String> list = new ArrayList<>();
		_appService.checkProperties(list);
	}
	
	@Test
	public void search_SearchIsNotNullAndSearchContainsCaseThree_ReturnBooleanBuilder() throws Exception {
	
		Map<String,SearchFields> map = new HashMap<>();
		QInventory inventory = QInventory.inventoryEntity;
		List<SearchFields> fieldsList= new ArrayList<>();
		SearchFields fields=new SearchFields();
		SearchCriteria search= new SearchCriteria();
		search.setType(3);
		search.setValue("xyz");
		search.setOperator("equals");
        fields.setOperator("equals");
		fields.setSearchValue("xyz");
        fieldsList.add(fields);
        search.setFields(fieldsList);
		BooleanBuilder builder = new BooleanBuilder();
        Mockito.doNothing().when(_appService).checkProperties(any(List.class));
		Mockito.doReturn(builder).when(_appService).searchKeyValuePair(any(QInventory.class), any(HashMap.class), any(HashMap.class));
        
		Assertions.assertThat(_appService.search(search)).isEqualTo(builder);
	}
	
	@Test
	public void search_StringIsNull_ReturnNull() throws Exception {

		Assertions.assertThat(_appService.search(null)).isEqualTo(null);
	}
   
    //Products
	@Test
	public void GetProducts_IfInventoryIdAndProductsIdIsNotNullAndInventoryExists_ReturnProducts() {
		Inventory inventory = mock(Inventory.class);
		Optional<Inventory> inventoryOptional = Optional.of((Inventory) inventory);
		Products productsEntity = mock(Products.class);

		Mockito.when(_inventoryRepository.findById(any(Integer.class))).thenReturn(inventoryOptional);

		Mockito.when(inventory.getProducts()).thenReturn(productsEntity);
		Assertions.assertThat(_appService.getProducts(ID)).isEqualTo(_mapper.productsToGetProductsOutput(productsEntity, inventory));
	}

	@Test 
	public void GetProducts_IfInventoryIdAndProductsIdIsNotNullAndInventoryDoesNotExist_ReturnNull() {
		Optional<Inventory> nullOptional = Optional.ofNullable(null);;
		Mockito.when(_inventoryRepository.findById(any(Integer.class))).thenReturn(nullOptional);
		Assertions.assertThat(_appService.getProducts(ID)).isEqualTo(null);
	}
   
    //Suppliers
	@Test
	public void GetSuppliers_IfInventoryIdAndSuppliersIdIsNotNullAndInventoryExists_ReturnSuppliers() {
		Inventory inventory = mock(Inventory.class);
		Optional<Inventory> inventoryOptional = Optional.of((Inventory) inventory);
		Suppliers suppliersEntity = mock(Suppliers.class);

		Mockito.when(_inventoryRepository.findById(any(Integer.class))).thenReturn(inventoryOptional);

		Mockito.when(inventory.getSuppliers()).thenReturn(suppliersEntity);
		Assertions.assertThat(_appService.getSuppliers(ID)).isEqualTo(_mapper.suppliersToGetSuppliersOutput(suppliersEntity, inventory));
	}

	@Test 
	public void GetSuppliers_IfInventoryIdAndSuppliersIdIsNotNullAndInventoryDoesNotExist_ReturnNull() {
		Optional<Inventory> nullOptional = Optional.ofNullable(null);;
		Mockito.when(_inventoryRepository.findById(any(Integer.class))).thenReturn(nullOptional);
		Assertions.assertThat(_appService.getSuppliers(ID)).isEqualTo(null);
	}

}
