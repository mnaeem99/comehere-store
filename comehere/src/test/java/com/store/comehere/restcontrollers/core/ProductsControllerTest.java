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
import com.store.comehere.application.core.products.ProductsAppService;
import com.store.comehere.application.core.products.dto.*;
import com.store.comehere.domain.core.products.IProductsRepository;
import com.store.comehere.domain.core.products.Products;

import com.store.comehere.application.core.inventory.InventoryAppService;    
import com.store.comehere.application.core.orderitems.OrderItemsAppService;    
import com.store.comehere.DatabaseContainerConfig;
import com.store.comehere.domain.core.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(properties = "spring.profiles.active=test")
public class ProductsControllerTest extends DatabaseContainerConfig{
	
	@Autowired
	protected SortHandlerMethodArgumentResolver sortArgumentResolver;

	@Autowired
	@Qualifier("productsRepository") 
	protected IProductsRepository products_repository;
	

	@SpyBean
	@Qualifier("productsAppService")
	protected ProductsAppService productsAppService;
	
    @SpyBean
    @Qualifier("inventoryAppService")
	protected InventoryAppService  inventoryAppService;
	
    @SpyBean
    @Qualifier("orderItemsAppService")
	protected OrderItemsAppService  orderItemsAppService;
	
	@SpyBean
	protected LoggingHelper logHelper;

	@SpyBean
	protected Environment env;

	@Mock
	protected Logger loggerMock;

	protected Products products;

	protected MockMvc mvc;
	
	@Autowired
	EntityManagerFactory emf;
	
    static EntityManagerFactory emfs;
    
    static int relationCount = 10;
    static int yearCount = 1971;
    static int dayCount = 10;
	private BigDecimal bigdec = new BigDecimal(1.2);
    
	@PostConstruct
	public void init() {
	emfs = emf;
	}

	@AfterClass
	public static void cleanup() {
		EntityManager em = emfs.createEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("truncate table public.products CASCADE").executeUpdate();
		em.getTransaction().commit();
	}
	

	public Products createEntity() {
	
		Products productsEntity = new Products();
		productsEntity.setCategory("1");
    	productsEntity.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-09-01 09:15:22"));
		productsEntity.setDescription("1");
		productsEntity.setName("1");
    	productsEntity.setPrice(new BigDecimal("1.1"));
		productsEntity.setProductId(1);
    	productsEntity.setUpdatedAt(SearchUtils.stringToLocalDateTime("1996-09-01 09:15:22"));
		productsEntity.setVersiono(0L);
		
		return productsEntity;
	}
    public CreateProductsInput createProductsInput() {
	
	    CreateProductsInput productsInput = new CreateProductsInput();
  		productsInput.setCategory("5");
    	productsInput.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-08-10 05:25:22"));
  		productsInput.setDescription("5");
  		productsInput.setName("5");
    	productsInput.setPrice(new BigDecimal("5.8"));
    	productsInput.setUpdatedAt(SearchUtils.stringToLocalDateTime("1996-08-10 05:25:22"));
		
		return productsInput;
	}

	public Products createNewEntity() {
		Products products = new Products();
		products.setCategory("3");
    	products.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-08-11 05:35:22"));
		products.setDescription("3");
		products.setName("3");
    	products.setPrice(new BigDecimal("3.3"));
		products.setProductId(3);
    	products.setUpdatedAt(SearchUtils.stringToLocalDateTime("1996-08-11 05:35:22"));
		
		return products;
	}
	
	public Products createUpdateEntity() {
		Products products = new Products();
		products.setCategory("4");
    	products.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-09-09 05:45:22"));
		products.setDescription("4");
		products.setName("4");
    	products.setPrice(new BigDecimal("3.3"));
		products.setProductId(4);
    	products.setUpdatedAt(SearchUtils.stringToLocalDateTime("1996-09-09 05:45:22"));
		
		return products;
	}
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
    
		final ProductsController productsController = new ProductsController(productsAppService, inventoryAppService, orderItemsAppService,
		logHelper,env);
		when(logHelper.getLogger()).thenReturn(loggerMock);
		doNothing().when(loggerMock).error(anyString());

		this.mvc = MockMvcBuilders.standaloneSetup(productsController)
				.setCustomArgumentResolvers(sortArgumentResolver)
				.setControllerAdvice()
				.build();
	}

	@Before
	public void initTest() {

		products= createEntity();
		List<Products> list= products_repository.findAll();
		if(!list.contains(products)) {
			products=products_repository.save(products);
		}

	}
	
	@Test
	public void FindById_IdIsValid_ReturnStatusOk() throws Exception {
	
		mvc.perform(get("/products/" + products.getProductId()+"/")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}  

	@Test
	public void FindById_IdIsNotValid_ReturnStatusNotFound() {

		 org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(get("/products/999")
				.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Not found"));

	}
	@Test
	public void CreateProducts_ProductsDoesNotExist_ReturnStatusOk() throws Exception {
		CreateProductsInput productsInput = createProductsInput();	
		
		
		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
	
		String json = ow.writeValueAsString(productsInput);

		mvc.perform(post("/products").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isOk());
	}     
	
	

	@Test
	public void DeleteProducts_IdIsNotValid_ThrowEntityNotFoundException() {

        doReturn(null).when(productsAppService).findById(999);
        org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(delete("/products/999")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())).hasCause(new EntityNotFoundException("There does not exist a products with a id=999"));

	}  

	@Test
	public void Delete_IdIsValid_ReturnStatusNoContent() throws Exception {
	
	 	Products entity =  createNewEntity();
	 	entity.setVersiono(0L);
		entity = products_repository.save(entity);
		

		FindProductsByIdOutput output= new FindProductsByIdOutput();
		output.setName(entity.getName());
		output.setPrice(entity.getPrice());
		output.setProductId(entity.getProductId());
		
         Mockito.doReturn(output).when(productsAppService).findById(entity.getProductId());
       
    //    Mockito.when(productsAppService.findById(entity.getProductId())).thenReturn(output);
        
		mvc.perform(delete("/products/" + entity.getProductId()+"/")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNoContent());
	}  


	@Test
	public void UpdateProducts_ProductsDoesNotExist_ReturnStatusNotFound() throws Exception {
   
        doReturn(null).when(productsAppService).findById(999);
        
        UpdateProductsInput products = new UpdateProductsInput();
  		products.setCategory("999");
		products.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-09-28 07:15:22"));
  		products.setDescription("999");
  		products.setName("999");
		products.setPrice(new BigDecimal("999"));
		products.setProductId(999);
		products.setUpdatedAt(SearchUtils.stringToLocalDateTime("1996-09-28 07:15:22"));

		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(products);

		 org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(
		 	put("/products/999")
		 	.contentType(MediaType.APPLICATION_JSON)
		 	.content(json))
			.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Unable to update. Products with id=999 not found."));
	}    

	@Test
	public void UpdateProducts_ProductsExists_ReturnStatusOk() throws Exception {
		Products entity =  createUpdateEntity();
		entity.setVersiono(0L);
		
		entity = products_repository.save(entity);
		FindProductsByIdOutput output= new FindProductsByIdOutput();
		output.setCategory(entity.getCategory());
		output.setCreatedAt(entity.getCreatedAt());
		output.setDescription(entity.getDescription());
		output.setName(entity.getName());
		output.setPrice(entity.getPrice());
		output.setProductId(entity.getProductId());
		output.setUpdatedAt(entity.getUpdatedAt());
		output.setVersiono(entity.getVersiono());
		
        Mockito.when(productsAppService.findById(entity.getProductId())).thenReturn(output);
        
        
		
		UpdateProductsInput productsInput = new UpdateProductsInput();
		productsInput.setName(entity.getName());
		productsInput.setPrice(entity.getPrice());
		productsInput.setProductId(entity.getProductId());
		

		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(productsInput);
	
		mvc.perform(put("/products/" + entity.getProductId()+"/").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isOk());

		Products de = createUpdateEntity();
		de.setProductId(entity.getProductId());
		products_repository.delete(de);
		

	}    
	@Test
	public void FindAll_SearchIsNotNullAndPropertyIsValid_ReturnStatusOk() throws Exception {

		mvc.perform(get("/products?search=productId[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}
		
	
	
	@Test
	public void GetInventorys_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {

		Map<String,String> joinCol = new HashMap<String,String>();
		joinCol.put("productId", "1");
		
        Mockito.when(productsAppService.parseInventorysJoinColumn("productId")).thenReturn(joinCol);
		mvc.perform(get("/products/1/inventorys?search=productId[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isOk());
	}  
	
	@Test
	public void GetInventorys_searchIsNotEmpty() {
	
		Mockito.when(productsAppService.parseInventorysJoinColumn(anyString())).thenReturn(null);
	 		  		    		  
	    org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(get("/products/1/inventorys?search=productId[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Invalid join column"));
	}    
	
	
	@Test
	public void GetOrderItems_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {

		Map<String,String> joinCol = new HashMap<String,String>();
		joinCol.put("productId", "1");
		
        Mockito.when(productsAppService.parseOrderItemsJoinColumn("productId")).thenReturn(joinCol);
		mvc.perform(get("/products/1/orderItems?search=productId[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isOk());
	}  
	
	@Test
	public void GetOrderItems_searchIsNotEmpty() {
	
		Mockito.when(productsAppService.parseOrderItemsJoinColumn(anyString())).thenReturn(null);
	 		  		    		  
	    org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(get("/products/1/orderItems?search=productId[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Invalid join column"));
	}    
    
}

