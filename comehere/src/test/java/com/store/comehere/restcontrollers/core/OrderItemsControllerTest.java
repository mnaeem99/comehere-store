package com.store.comehere.restcontrollers.core;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.*;
import java.time.*;
import java.math.BigDecimal;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import org.springframework.core.env.Environment;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.store.comehere.commons.logging.LoggingHelper;
import com.store.comehere.commons.search.SearchUtils;
import com.store.comehere.application.core.orderitems.OrderItemsAppService;
import com.store.comehere.application.core.orderitems.dto.*;
import com.store.comehere.domain.core.orderitems.IOrderItemsRepository;
import com.store.comehere.domain.core.orderitems.OrderItems;

import com.store.comehere.domain.core.orders.IOrdersRepository;
import com.store.comehere.domain.core.orders.Orders;
import com.store.comehere.domain.core.products.IProductsRepository;
import com.store.comehere.domain.core.products.Products;
import com.store.comehere.domain.core.customers.ICustomersRepository;
import com.store.comehere.domain.core.customers.Customers;
import com.store.comehere.application.core.orders.OrdersAppService;    
import com.store.comehere.application.core.products.ProductsAppService;    
import com.store.comehere.DatabaseContainerConfig;
import com.store.comehere.domain.core.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(properties = "spring.profiles.active=test")
public class OrderItemsControllerTest extends DatabaseContainerConfig{
	
	@Autowired
	protected SortHandlerMethodArgumentResolver sortArgumentResolver;

	@Autowired
	@Qualifier("orderItemsRepository") 
	protected IOrderItemsRepository orderItems_repository;
	
	@Autowired
	@Qualifier("ordersRepository") 
	protected IOrdersRepository ordersRepository;
	
	@Autowired
	@Qualifier("productsRepository") 
	protected IProductsRepository productsRepository;
	
	@Autowired
	@Qualifier("customersRepository") 
	protected ICustomersRepository customersRepository;
	

	@SpyBean
	@Qualifier("orderItemsAppService")
	protected OrderItemsAppService orderItemsAppService;
	
    @SpyBean
    @Qualifier("ordersAppService")
	protected OrdersAppService  ordersAppService;
	
    @SpyBean
    @Qualifier("productsAppService")
	protected ProductsAppService  productsAppService;
	
	@SpyBean
	protected LoggingHelper logHelper;

	@SpyBean
	protected Environment env;

	@Mock
	protected Logger loggerMock;

	protected OrderItems orderItems;

	protected MockMvc mvc;
	
	@Autowired
	EntityManagerFactory emf;
	
    static EntityManagerFactory emfs;
    
    static int relationCount = 10;
    static int yearCount = 1971;
    static int dayCount = 10;
	private BigDecimal bigdec = new BigDecimal(1.2);
    
	int countOrders = 10;
	
	int countProducts = 10;
	
	int countCustomers = 10;
	
	@PostConstruct
	public void init() {
	emfs = emf;
	}

	@AfterClass
	public static void cleanup() {
		EntityManager em = emfs.createEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("truncate table public.order_items CASCADE").executeUpdate();
		em.createNativeQuery("truncate table public.orders CASCADE").executeUpdate();
		em.createNativeQuery("truncate table public.products CASCADE").executeUpdate();
		em.createNativeQuery("truncate table public.customers CASCADE").executeUpdate();
		em.getTransaction().commit();
	}
	
	public Orders createOrdersEntity() {
	
		if(countOrders>60) {
			countOrders = 10;
		}

		if(dayCount>=31) {
			dayCount = 10;
			yearCount++;
		}
		
		Orders ordersEntity = new Orders();
		
		ordersEntity.setOrderDate(SearchUtils.stringToLocalDateTime(yearCount+"-09-"+dayCount+" 05:25:22"));
		ordersEntity.setOrderId(relationCount);
  		ordersEntity.setStatus(String.valueOf(relationCount));
		ordersEntity.setTotal(bigdec);
		bigdec = bigdec.add(BigDecimal.valueOf(1.2D));
		ordersEntity.setVersiono(0L);
		relationCount++;
		Customers customers= createCustomersEntity();
		ordersEntity.setCustomers(customers);
		if(!ordersRepository.findAll().contains(ordersEntity))
		{
			 ordersEntity = ordersRepository.save(ordersEntity);
		}
		countOrders++;
	    return ordersEntity;
	}
	public Products createProductsEntity() {
	
		if(countProducts>60) {
			countProducts = 10;
		}

		if(dayCount>=31) {
			dayCount = 10;
			yearCount++;
		}
		
		Products productsEntity = new Products();
		
  		productsEntity.setCategory(String.valueOf(relationCount));
		productsEntity.setCreatedAt(SearchUtils.stringToLocalDateTime(yearCount+"-09-"+dayCount+" 05:25:22"));
  		productsEntity.setDescription(String.valueOf(relationCount));
  		productsEntity.setName(String.valueOf(relationCount));
		productsEntity.setPrice(bigdec);
		bigdec = bigdec.add(BigDecimal.valueOf(1.2D));
		productsEntity.setProductId(relationCount);
		productsEntity.setUpdatedAt(SearchUtils.stringToLocalDateTime(yearCount+"-09-"+dayCount+" 05:25:22"));
		productsEntity.setVersiono(0L);
		relationCount++;
		if(!productsRepository.findAll().contains(productsEntity))
		{
			 productsEntity = productsRepository.save(productsEntity);
		}
		countProducts++;
	    return productsEntity;
	}
	public Customers createCustomersEntity() {
	
		if(countCustomers>60) {
			countCustomers = 10;
		}

		if(dayCount>=31) {
			dayCount = 10;
			yearCount++;
		}
		
		Customers customersEntity = new Customers();
		
  		customersEntity.setAddress(String.valueOf(relationCount));
		customersEntity.setCreatedAt(SearchUtils.stringToLocalDateTime(yearCount+"-09-"+dayCount+" 05:25:22"));
		customersEntity.setCustomerId(relationCount);
  		customersEntity.setEmail(String.valueOf(relationCount));
  		customersEntity.setFirstName(String.valueOf(relationCount));
  		customersEntity.setLastName(String.valueOf(relationCount));
  		customersEntity.setPhone(String.valueOf(relationCount));
		customersEntity.setUpdatedAt(SearchUtils.stringToLocalDateTime(yearCount+"-09-"+dayCount+" 05:25:22"));
		customersEntity.setVersiono(0L);
		relationCount++;
		if(!customersRepository.findAll().contains(customersEntity))
		{
			 customersEntity = customersRepository.save(customersEntity);
		}
		countCustomers++;
	    return customersEntity;
	}

	public OrderItems createEntity() {
		Orders orders = createOrdersEntity();
		Products products = createProductsEntity();
	
		OrderItems orderItemsEntity = new OrderItems();
		orderItemsEntity.setOrderItemId(1);
    	orderItemsEntity.setPrice(new BigDecimal("1.1"));
		orderItemsEntity.setQuantity(1);
		orderItemsEntity.setVersiono(0L);
		orderItemsEntity.setOrders(orders);
		orderItemsEntity.setProducts(products);
		
		return orderItemsEntity;
	}
    public CreateOrderItemsInput createOrderItemsInput() {
	
	    CreateOrderItemsInput orderItemsInput = new CreateOrderItemsInput();
    	orderItemsInput.setPrice(new BigDecimal("5.8"));
		orderItemsInput.setQuantity(5);
		
		return orderItemsInput;
	}

	public OrderItems createNewEntity() {
		OrderItems orderItems = new OrderItems();
		orderItems.setOrderItemId(3);
    	orderItems.setPrice(new BigDecimal("3.3"));
		orderItems.setQuantity(3);
		
		return orderItems;
	}
	
	public OrderItems createUpdateEntity() {
		OrderItems orderItems = new OrderItems();
		orderItems.setOrderItemId(4);
    	orderItems.setPrice(new BigDecimal("3.3"));
		orderItems.setQuantity(4);
		
		return orderItems;
	}
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
    
		final OrderItemsController orderItemsController = new OrderItemsController(orderItemsAppService, ordersAppService, productsAppService,
		logHelper,env);
		when(logHelper.getLogger()).thenReturn(loggerMock);
		doNothing().when(loggerMock).error(anyString());

		this.mvc = MockMvcBuilders.standaloneSetup(orderItemsController)
				.setCustomArgumentResolvers(sortArgumentResolver)
				.setControllerAdvice()
				.build();
	}

	@Before
	public void initTest() {

		orderItems= createEntity();
		List<OrderItems> list= orderItems_repository.findAll();
		if(!list.contains(orderItems)) {
			orderItems=orderItems_repository.save(orderItems);
		}

	}
	
	@Test
	public void FindById_IdIsValid_ReturnStatusOk() throws Exception {
	
		mvc.perform(get("/orderItems/" + orderItems.getOrderItemId()+"/")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}  

	@Test
	public void FindById_IdIsNotValid_ReturnStatusNotFound() {

		 org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(get("/orderItems/999")
				.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Not found"));

	}
	@Test
	public void CreateOrderItems_OrderItemsDoesNotExist_ReturnStatusOk() throws Exception {
		CreateOrderItemsInput orderItemsInput = createOrderItemsInput();	
		
	    
		Orders orders =  createOrdersEntity();

		orderItemsInput.setOrderId(Integer.parseInt(orders.getOrderId().toString()));
	    
		Products products =  createProductsEntity();

		orderItemsInput.setProductId(Integer.parseInt(products.getProductId().toString()));
		
		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
	
		String json = ow.writeValueAsString(orderItemsInput);

		mvc.perform(post("/orderItems").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isOk());
	}     
	
	@Test
	public void CreateOrderItems_ordersDoesNotExists_ThrowEntityNotFoundException() throws Exception{

		CreateOrderItemsInput orderItems = createOrderItemsInput();
		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
	
		String json = ow.writeValueAsString(orderItems);

		org.assertj.core.api.Assertions.assertThatThrownBy(() ->
		mvc.perform(post("/orderItems")
				.contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isNotFound()));

	}    
	
	@Test
	public void CreateOrderItems_productsDoesNotExists_ThrowEntityNotFoundException() throws Exception{

		CreateOrderItemsInput orderItems = createOrderItemsInput();
		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
	
		String json = ow.writeValueAsString(orderItems);

		org.assertj.core.api.Assertions.assertThatThrownBy(() ->
		mvc.perform(post("/orderItems")
				.contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isNotFound()));

	}    

	@Test
	public void DeleteOrderItems_IdIsNotValid_ThrowEntityNotFoundException() {

        doReturn(null).when(orderItemsAppService).findById(999);
        org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(delete("/orderItems/999")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())).hasCause(new EntityNotFoundException("There does not exist a orderItems with a id=999"));

	}  

	@Test
	public void Delete_IdIsValid_ReturnStatusNoContent() throws Exception {
	
	 	OrderItems entity =  createNewEntity();
	 	entity.setVersiono(0L);
		Orders orders = createOrdersEntity();
		entity.setOrders(orders);
		Products products = createProductsEntity();
		entity.setProducts(products);
		entity = orderItems_repository.save(entity);
		

		FindOrderItemsByIdOutput output= new FindOrderItemsByIdOutput();
		output.setOrderItemId(entity.getOrderItemId());
		output.setPrice(entity.getPrice());
		output.setQuantity(entity.getQuantity());
		
         Mockito.doReturn(output).when(orderItemsAppService).findById(entity.getOrderItemId());
       
    //    Mockito.when(orderItemsAppService.findById(entity.getOrderItemId())).thenReturn(output);
        
		mvc.perform(delete("/orderItems/" + entity.getOrderItemId()+"/")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNoContent());
	}  


	@Test
	public void UpdateOrderItems_OrderItemsDoesNotExist_ReturnStatusNotFound() throws Exception {
   
        doReturn(null).when(orderItemsAppService).findById(999);
        
        UpdateOrderItemsInput orderItems = new UpdateOrderItemsInput();
		orderItems.setOrderItemId(999);
		orderItems.setPrice(new BigDecimal("999"));
		orderItems.setQuantity(999);

		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(orderItems);

		 org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(
		 	put("/orderItems/999")
		 	.contentType(MediaType.APPLICATION_JSON)
		 	.content(json))
			.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Unable to update. OrderItems with id=999 not found."));
	}    

	@Test
	public void UpdateOrderItems_OrderItemsExists_ReturnStatusOk() throws Exception {
		OrderItems entity =  createUpdateEntity();
		entity.setVersiono(0L);
		
		Orders orders = createOrdersEntity();
		entity.setOrders(orders);
		Products products = createProductsEntity();
		entity.setProducts(products);
		entity = orderItems_repository.save(entity);
		FindOrderItemsByIdOutput output= new FindOrderItemsByIdOutput();
		output.setOrderItemId(entity.getOrderItemId());
		output.setPrice(entity.getPrice());
		output.setQuantity(entity.getQuantity());
		output.setVersiono(entity.getVersiono());
		
        Mockito.when(orderItemsAppService.findById(entity.getOrderItemId())).thenReturn(output);
        
        
		
		UpdateOrderItemsInput orderItemsInput = new UpdateOrderItemsInput();
		orderItemsInput.setOrderItemId(entity.getOrderItemId());
		orderItemsInput.setPrice(entity.getPrice());
		orderItemsInput.setQuantity(entity.getQuantity());
		
		orderItemsInput.setOrderId(Integer.parseInt(orders.getOrderId().toString()));
		orderItemsInput.setProductId(Integer.parseInt(products.getProductId().toString()));

		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(orderItemsInput);
	
		mvc.perform(put("/orderItems/" + entity.getOrderItemId()+"/").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isOk());

		OrderItems de = createUpdateEntity();
		de.setOrderItemId(entity.getOrderItemId());
		orderItems_repository.delete(de);
		

	}    
	@Test
	public void FindAll_SearchIsNotNullAndPropertyIsValid_ReturnStatusOk() throws Exception {

		mvc.perform(get("/orderItems?search=orderItemId[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}
		
	
	@Test
	public void GetOrders_IdIsNotEmptyAndIdDoesNotExist_ReturnNotFound() {
  
	   org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(get("/orderItems/999/orders")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Not found"));
	
	}    
	
	@Test
	public void GetOrders_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
	
	   mvc.perform(get("/orderItems/" + orderItems.getOrderItemId()+ "/orders")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isOk());
	}  
	
	
	@Test
	public void GetProducts_IdIsNotEmptyAndIdDoesNotExist_ReturnNotFound() {
  
	   org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(get("/orderItems/999/products")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Not found"));
	
	}    
	
	@Test
	public void GetProducts_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
	
	   mvc.perform(get("/orderItems/" + orderItems.getOrderItemId()+ "/products")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isOk());
	}  
	
    
}

