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
import com.store.comehere.application.core.customers.CustomersAppService;
import com.store.comehere.application.core.customers.dto.*;
import com.store.comehere.domain.core.customers.ICustomersRepository;
import com.store.comehere.domain.core.customers.Customers;

import com.store.comehere.domain.core.orders.IOrdersRepository;
import com.store.comehere.domain.core.orders.Orders;
import com.store.comehere.domain.core.customers.ICustomersRepository;
import com.store.comehere.domain.core.customers.Customers;
import com.store.comehere.application.core.orders.OrdersAppService;    
import com.store.comehere.DatabaseContainerConfig;
import com.store.comehere.domain.core.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(properties = "spring.profiles.active=test")
public class CustomersControllerTest extends DatabaseContainerConfig{
	
	@Autowired
	protected SortHandlerMethodArgumentResolver sortArgumentResolver;

	@Autowired
	@Qualifier("customersRepository") 
	protected ICustomersRepository customers_repository;
	
	@Autowired
	@Qualifier("ordersRepository") 
	protected IOrdersRepository ordersRepository;
	
	@Autowired
	@Qualifier("customersRepository") 
	protected ICustomersRepository customersRepository;
	

	@SpyBean
	@Qualifier("customersAppService")
	protected CustomersAppService customersAppService;
	
    @SpyBean
    @Qualifier("ordersAppService")
	protected OrdersAppService  ordersAppService;
	
	@SpyBean
	protected LoggingHelper logHelper;

	@SpyBean
	protected Environment env;

	@Mock
	protected Logger loggerMock;

	protected Customers customers;

	protected MockMvc mvc;
	
	@Autowired
	EntityManagerFactory emf;
	
    static EntityManagerFactory emfs;
    
    static int relationCount = 10;
    static int yearCount = 1971;
    static int dayCount = 10;
	private BigDecimal bigdec = new BigDecimal(1.2);
    
	int countOrders = 10;
	
	int countCustomers = 10;
	
	@PostConstruct
	public void init() {
	emfs = emf;
	}

	@AfterClass
	public static void cleanup() {
		EntityManager em = emfs.createEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("truncate table public.customers CASCADE").executeUpdate();
		em.createNativeQuery("truncate table public.orders CASCADE").executeUpdate();
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

	public Customers createEntity() {
	
		Customers customersEntity = new Customers();
		customersEntity.setAddress("1");
    	customersEntity.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-09-01 09:15:22"));
		customersEntity.setCustomerId(1);
		customersEntity.setEmail("1");
		customersEntity.setFirstName("1");
		customersEntity.setLastName("1");
		customersEntity.setPhone("1");
    	customersEntity.setUpdatedAt(SearchUtils.stringToLocalDateTime("1996-09-01 09:15:22"));
		customersEntity.setVersiono(0L);
		
		return customersEntity;
	}
    public CreateCustomersInput createCustomersInput() {
	
	    CreateCustomersInput customersInput = new CreateCustomersInput();
  		customersInput.setAddress("5");
    	customersInput.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-08-10 05:25:22"));
  		customersInput.setEmail("5");
  		customersInput.setFirstName("5");
  		customersInput.setLastName("5");
  		customersInput.setPhone("5");
    	customersInput.setUpdatedAt(SearchUtils.stringToLocalDateTime("1996-08-10 05:25:22"));
		
		return customersInput;
	}

	public Customers createNewEntity() {
		Customers customers = new Customers();
		customers.setAddress("3");
    	customers.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-08-11 05:35:22"));
		customers.setCustomerId(3);
		customers.setEmail("3");
		customers.setFirstName("3");
		customers.setLastName("3");
		customers.setPhone("3");
    	customers.setUpdatedAt(SearchUtils.stringToLocalDateTime("1996-08-11 05:35:22"));
		
		return customers;
	}
	
	public Customers createUpdateEntity() {
		Customers customers = new Customers();
		customers.setAddress("4");
    	customers.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-09-09 05:45:22"));
		customers.setCustomerId(4);
		customers.setEmail("4");
		customers.setFirstName("4");
		customers.setLastName("4");
		customers.setPhone("4");
    	customers.setUpdatedAt(SearchUtils.stringToLocalDateTime("1996-09-09 05:45:22"));
		
		return customers;
	}
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
    
		final CustomersController customersController = new CustomersController(customersAppService, ordersAppService,
		logHelper,env);
		when(logHelper.getLogger()).thenReturn(loggerMock);
		doNothing().when(loggerMock).error(anyString());

		this.mvc = MockMvcBuilders.standaloneSetup(customersController)
				.setCustomArgumentResolvers(sortArgumentResolver)
				.setControllerAdvice()
				.build();
	}

	@Before
	public void initTest() {

		customers= createEntity();
		List<Customers> list= customers_repository.findAll();
		if(!list.contains(customers)) {
			customers=customers_repository.save(customers);
		}

	}
	
	@Test
	public void FindById_IdIsValid_ReturnStatusOk() throws Exception {
	
		mvc.perform(get("/customers/" + customers.getCustomerId()+"/")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}  

	@Test
	public void FindById_IdIsNotValid_ReturnStatusNotFound() {

		 org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(get("/customers/999")
				.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Not found"));

	}
	@Test
	public void CreateCustomers_CustomersDoesNotExist_ReturnStatusOk() throws Exception {
		CreateCustomersInput customersInput = createCustomersInput();	
		
		
		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
	
		String json = ow.writeValueAsString(customersInput);

		mvc.perform(post("/customers").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isOk());
	}     
	

	@Test
	public void DeleteCustomers_IdIsNotValid_ThrowEntityNotFoundException() {

        doReturn(null).when(customersAppService).findById(999);
        org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(delete("/customers/999")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())).hasCause(new EntityNotFoundException("There does not exist a customers with a id=999"));

	}  

	@Test
	public void Delete_IdIsValid_ReturnStatusNoContent() throws Exception {
	
	 	Customers entity =  createNewEntity();
	 	entity.setVersiono(0L);
		entity = customers_repository.save(entity);
		

		FindCustomersByIdOutput output= new FindCustomersByIdOutput();
		output.setCustomerId(entity.getCustomerId());
		output.setEmail(entity.getEmail());
		output.setFirstName(entity.getFirstName());
		output.setLastName(entity.getLastName());
		
         Mockito.doReturn(output).when(customersAppService).findById(entity.getCustomerId());
       
    //    Mockito.when(customersAppService.findById(entity.getCustomerId())).thenReturn(output);
        
		mvc.perform(delete("/customers/" + entity.getCustomerId()+"/")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNoContent());
	}  


	@Test
	public void UpdateCustomers_CustomersDoesNotExist_ReturnStatusNotFound() throws Exception {
   
        doReturn(null).when(customersAppService).findById(999);
        
        UpdateCustomersInput customers = new UpdateCustomersInput();
  		customers.setAddress("999");
		customers.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-09-28 07:15:22"));
		customers.setCustomerId(999);
  		customers.setEmail("999");
  		customers.setFirstName("999");
  		customers.setLastName("999");
  		customers.setPhone("999");
		customers.setUpdatedAt(SearchUtils.stringToLocalDateTime("1996-09-28 07:15:22"));

		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(customers);

		 org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(
		 	put("/customers/999")
		 	.contentType(MediaType.APPLICATION_JSON)
		 	.content(json))
			.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Unable to update. Customers with id=999 not found."));
	}    

	@Test
	public void UpdateCustomers_CustomersExists_ReturnStatusOk() throws Exception {
		Customers entity =  createUpdateEntity();
		entity.setVersiono(0L);
		
		entity = customers_repository.save(entity);
		FindCustomersByIdOutput output= new FindCustomersByIdOutput();
		output.setAddress(entity.getAddress());
		output.setCreatedAt(entity.getCreatedAt());
		output.setCustomerId(entity.getCustomerId());
		output.setEmail(entity.getEmail());
		output.setFirstName(entity.getFirstName());
		output.setLastName(entity.getLastName());
		output.setPhone(entity.getPhone());
		output.setUpdatedAt(entity.getUpdatedAt());
		output.setVersiono(entity.getVersiono());
		
        Mockito.when(customersAppService.findById(entity.getCustomerId())).thenReturn(output);
        
        
		
		UpdateCustomersInput customersInput = new UpdateCustomersInput();
		customersInput.setCustomerId(entity.getCustomerId());
		customersInput.setEmail(entity.getEmail());
		customersInput.setFirstName(entity.getFirstName());
		customersInput.setLastName(entity.getLastName());
		

		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(customersInput);
	
		mvc.perform(put("/customers/" + entity.getCustomerId()+"/").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isOk());

		Customers de = createUpdateEntity();
		de.setCustomerId(entity.getCustomerId());
		customers_repository.delete(de);
		

	}    
	@Test
	public void FindAll_SearchIsNotNullAndPropertyIsValid_ReturnStatusOk() throws Exception {

		mvc.perform(get("/customers?search=customerId[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}
		
	
	
	@Test
	public void GetOrders_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {

		Map<String,String> joinCol = new HashMap<String,String>();
		joinCol.put("customerId", "1");
		
        Mockito.when(customersAppService.parseOrdersJoinColumn("customerId")).thenReturn(joinCol);
		mvc.perform(get("/customers/1/orders?search=customerId[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isOk());
	}  
	
	@Test
	public void GetOrders_searchIsNotEmpty() {
	
		Mockito.when(customersAppService.parseOrdersJoinColumn(anyString())).thenReturn(null);
	 		  		    		  
	    org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(get("/customers/1/orders?search=customerId[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Invalid join column"));
	}    
    
}

