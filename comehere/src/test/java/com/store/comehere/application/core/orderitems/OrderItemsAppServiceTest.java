package com.store.comehere.application.core.orderitems;

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

import com.store.comehere.domain.core.orderitems.*;
import com.store.comehere.commons.search.*;
import com.store.comehere.application.core.orderitems.dto.*;
import com.store.comehere.domain.core.orderitems.QOrderItems;
import com.store.comehere.domain.core.orderitems.OrderItems;

import com.store.comehere.domain.core.orders.Orders;
import com.store.comehere.domain.core.orders.IOrdersRepository;
import com.store.comehere.domain.core.products.Products;
import com.store.comehere.domain.core.products.IProductsRepository;
import com.store.comehere.commons.logging.LoggingHelper;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import java.time.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class OrderItemsAppServiceTest {

	@InjectMocks
	@Spy
	protected OrderItemsAppService _appService;
	@Mock
	protected IOrderItemsRepository _orderItemsRepository;
	
    @Mock
	protected IOrdersRepository _ordersRepository;

    @Mock
	protected IProductsRepository _productsRepository;

	@Mock
	protected IOrderItemsMapper _mapper;

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
	public void findOrderItemsById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
		Optional<OrderItems> nullOptional = Optional.ofNullable(null);
		Mockito.when(_orderItemsRepository.findById(any(Integer.class))).thenReturn(nullOptional);
		Assertions.assertThat(_appService.findById(ID )).isEqualTo(null);
	}
	
	@Test
	public void findOrderItemsById_IdIsNotNullAndIdExists_ReturnOrderItems() {

		OrderItems orderItems = mock(OrderItems.class);
		Optional<OrderItems> orderItemsOptional = Optional.of((OrderItems) orderItems);
		Mockito.when(_orderItemsRepository.findById(any(Integer.class))).thenReturn(orderItemsOptional);
		
	    Assertions.assertThat(_appService.findById(ID )).isEqualTo(_mapper.orderItemsToFindOrderItemsByIdOutput(orderItems));
	}
	
	
	@Test 
    public void createOrderItems_OrderItemsIsNotNullAndOrderItemsDoesNotExist_StoreOrderItems() { 
 
        OrderItems orderItemsEntity = mock(OrderItems.class); 
    	CreateOrderItemsInput orderItemsInput = new CreateOrderItemsInput();
		
        Orders orders = mock(Orders.class);
		Optional<Orders> ordersOptional = Optional.of((Orders) orders);
        orderItemsInput.setOrderId(15);
		
        Mockito.when(_ordersRepository.findById(any(Integer.class))).thenReturn(ordersOptional);
        
		
        Products products = mock(Products.class);
		Optional<Products> productsOptional = Optional.of((Products) products);
        orderItemsInput.setProductId(15);
		
        Mockito.when(_productsRepository.findById(any(Integer.class))).thenReturn(productsOptional);
        
		
        Mockito.when(_mapper.createOrderItemsInputToOrderItems(any(CreateOrderItemsInput.class))).thenReturn(orderItemsEntity); 
        Mockito.when(_orderItemsRepository.save(any(OrderItems.class))).thenReturn(orderItemsEntity);

	   	Assertions.assertThat(_appService.create(orderItemsInput)).isEqualTo(_mapper.orderItemsToCreateOrderItemsOutput(orderItemsEntity));

    } 
	@Test
	public void createOrderItems_OrderItemsIsNotNullAndOrderItemsDoesNotExistAndChildIsNullAndChildIsMandatory_ReturnNull() {

		CreateOrderItemsInput orderItems = mock(CreateOrderItemsInput.class);
		
		Mockito.when(_mapper.createOrderItemsInputToOrderItems(any(CreateOrderItemsInput.class))).thenReturn(null); 
		Assertions.assertThat(_appService.create(orderItems)).isEqualTo(null);
	}
	
	@Test
	public void createOrderItems_OrderItemsIsNotNullAndOrderItemsDoesNotExistAndChildIsNotNullAndChildIsMandatoryAndFindByIdIsNull_ReturnNull() {

		CreateOrderItemsInput orderItems = new CreateOrderItemsInput();
	    
        orderItems.setOrderId(15);
     
     	Optional<Orders> nullOptional = Optional.ofNullable(null);
        Mockito.when(_ordersRepository.findById(any(Integer.class))).thenReturn(nullOptional);
        
//		Mockito.when(_ordersRepository.findById(any(Integer.class))).thenReturn(nullOptional);
		Assertions.assertThat(_appService.create(orderItems)).isEqualTo(null);
    }
    
    @Test
	public void updateOrderItems_OrderItemsIsNotNullAndOrderItemsDoesNotExistAndChildIsNullAndChildIsMandatory_ReturnNull() {

		UpdateOrderItemsInput orderItemsInput = mock(UpdateOrderItemsInput.class);
		OrderItems orderItems = mock(OrderItems.class); 
		
     	Optional<OrderItems> orderItemsOptional = Optional.of((OrderItems) orderItems);
		Mockito.when(_orderItemsRepository.findById(any(Integer.class))).thenReturn(orderItemsOptional);
		
		Mockito.when(_mapper.updateOrderItemsInputToOrderItems(any(UpdateOrderItemsInput.class))).thenReturn(orderItems); 
		Assertions.assertThat(_appService.update(ID,orderItemsInput)).isEqualTo(null);
	}
	
	@Test
	public void updateOrderItems_OrderItemsIsNotNullAndOrderItemsDoesNotExistAndChildIsNotNullAndChildIsMandatoryAndFindByIdIsNull_ReturnNull() {
		
		UpdateOrderItemsInput orderItemsInput = new UpdateOrderItemsInput();
        orderItemsInput.setOrderId(15);
     
    	OrderItems orderItems = mock(OrderItems.class);
		
     	Optional<OrderItems> orderItemsOptional = Optional.of((OrderItems) orderItems);
		Mockito.when(_orderItemsRepository.findById(any(Integer.class))).thenReturn(orderItemsOptional);
		
		Mockito.when(_mapper.updateOrderItemsInputToOrderItems(any(UpdateOrderItemsInput.class))).thenReturn(orderItems);
     	Optional<Orders> nullOptional = Optional.ofNullable(null);
        Mockito.when(_ordersRepository.findById(any(Integer.class))).thenReturn(nullOptional);
        
	//	Mockito.when(_ordersRepository.findById(any(Integer.class))).thenReturn(nullOptional);
		Assertions.assertThat(_appService.update(ID,orderItemsInput)).isEqualTo(null);
	}

		
	@Test
	public void updateOrderItems_OrderItemsIdIsNotNullAndIdExists_ReturnUpdatedOrderItems() {

		OrderItems orderItemsEntity = mock(OrderItems.class);
		UpdateOrderItemsInput orderItems= mock(UpdateOrderItemsInput.class);
		
		Optional<OrderItems> orderItemsOptional = Optional.of((OrderItems) orderItemsEntity);
		Mockito.when(_orderItemsRepository.findById(any(Integer.class))).thenReturn(orderItemsOptional);
	 		
		Mockito.when(_mapper.updateOrderItemsInputToOrderItems(any(UpdateOrderItemsInput.class))).thenReturn(orderItemsEntity);
		Mockito.when(_orderItemsRepository.save(any(OrderItems.class))).thenReturn(orderItemsEntity);
		Assertions.assertThat(_appService.update(ID,orderItems)).isEqualTo(_mapper.orderItemsToUpdateOrderItemsOutput(orderItemsEntity));
	}
    
	@Test
	public void deleteOrderItems_OrderItemsIsNotNullAndOrderItemsExists_OrderItemsRemoved() {

		OrderItems orderItems = mock(OrderItems.class);
		Optional<OrderItems> orderItemsOptional = Optional.of((OrderItems) orderItems);
		Mockito.when(_orderItemsRepository.findById(any(Integer.class))).thenReturn(orderItemsOptional);
 		
		_appService.delete(ID); 
		verify(_orderItemsRepository).delete(orderItems);
	}
	
	@Test
	public void find_ListIsEmpty_ReturnList() throws Exception {

		List<OrderItems> list = new ArrayList<>();
		Page<OrderItems> foundPage = new PageImpl(list);
		Pageable pageable = mock(Pageable.class);
		List<FindOrderItemsByIdOutput> output = new ArrayList<>();
		SearchCriteria search= new SearchCriteria();

		Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
		Mockito.when(_orderItemsRepository.findAll(any(Predicate.class),any(Pageable.class))).thenReturn(foundPage);
		Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
	}
	
	@Test
	public void find_ListIsNotEmpty_ReturnList() throws Exception {

		List<OrderItems> list = new ArrayList<>();
		OrderItems orderItems = mock(OrderItems.class);
		list.add(orderItems);
    	Page<OrderItems> foundPage = new PageImpl(list);
		Pageable pageable = mock(Pageable.class);
		List<FindOrderItemsByIdOutput> output = new ArrayList<>();
        SearchCriteria search= new SearchCriteria();

		output.add(_mapper.orderItemsToFindOrderItemsByIdOutput(orderItems));
		
		Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
    	Mockito.when(_orderItemsRepository.findAll(any(Predicate.class),any(Pageable.class))).thenReturn(foundPage);
		Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
	}
	
	@Test
	public void searchKeyValuePair_PropertyExists_ReturnBooleanBuilder() {
		QOrderItems orderItems = QOrderItems.orderItemsEntity;
	    SearchFields searchFields = new SearchFields();
		searchFields.setOperator("equals");
		searchFields.setSearchValue("xyz");
	    Map<String,SearchFields> map = new HashMap<>();
		Map<String,String> searchMap = new HashMap<>();
        searchMap.put("xyz",String.valueOf(ID));
		BooleanBuilder builder = new BooleanBuilder();
		Assertions.assertThat(_appService.searchKeyValuePair(orderItems,map,searchMap)).isEqualTo(builder);
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
		QOrderItems orderItems = QOrderItems.orderItemsEntity;
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
		Mockito.doReturn(builder).when(_appService).searchKeyValuePair(any(QOrderItems.class), any(HashMap.class), any(HashMap.class));
        
		Assertions.assertThat(_appService.search(search)).isEqualTo(builder);
	}
	
	@Test
	public void search_StringIsNull_ReturnNull() throws Exception {

		Assertions.assertThat(_appService.search(null)).isEqualTo(null);
	}
   
    //Orders
	@Test
	public void GetOrders_IfOrderItemsIdAndOrdersIdIsNotNullAndOrderItemsExists_ReturnOrders() {
		OrderItems orderItems = mock(OrderItems.class);
		Optional<OrderItems> orderItemsOptional = Optional.of((OrderItems) orderItems);
		Orders ordersEntity = mock(Orders.class);

		Mockito.when(_orderItemsRepository.findById(any(Integer.class))).thenReturn(orderItemsOptional);

		Mockito.when(orderItems.getOrders()).thenReturn(ordersEntity);
		Assertions.assertThat(_appService.getOrders(ID)).isEqualTo(_mapper.ordersToGetOrdersOutput(ordersEntity, orderItems));
	}

	@Test 
	public void GetOrders_IfOrderItemsIdAndOrdersIdIsNotNullAndOrderItemsDoesNotExist_ReturnNull() {
		Optional<OrderItems> nullOptional = Optional.ofNullable(null);;
		Mockito.when(_orderItemsRepository.findById(any(Integer.class))).thenReturn(nullOptional);
		Assertions.assertThat(_appService.getOrders(ID)).isEqualTo(null);
	}
   
    //Products
	@Test
	public void GetProducts_IfOrderItemsIdAndProductsIdIsNotNullAndOrderItemsExists_ReturnProducts() {
		OrderItems orderItems = mock(OrderItems.class);
		Optional<OrderItems> orderItemsOptional = Optional.of((OrderItems) orderItems);
		Products productsEntity = mock(Products.class);

		Mockito.when(_orderItemsRepository.findById(any(Integer.class))).thenReturn(orderItemsOptional);

		Mockito.when(orderItems.getProducts()).thenReturn(productsEntity);
		Assertions.assertThat(_appService.getProducts(ID)).isEqualTo(_mapper.productsToGetProductsOutput(productsEntity, orderItems));
	}

	@Test 
	public void GetProducts_IfOrderItemsIdAndProductsIdIsNotNullAndOrderItemsDoesNotExist_ReturnNull() {
		Optional<OrderItems> nullOptional = Optional.ofNullable(null);;
		Mockito.when(_orderItemsRepository.findById(any(Integer.class))).thenReturn(nullOptional);
		Assertions.assertThat(_appService.getProducts(ID)).isEqualTo(null);
	}

}
