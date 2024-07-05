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
import com.store.comehere.application.core.inventory.InventoryAppService;
import com.store.comehere.application.core.inventory.dto.*;
import com.store.comehere.domain.core.inventory.IInventoryRepository;
import com.store.comehere.domain.core.inventory.Inventory;

import com.store.comehere.domain.core.products.IProductsRepository;
import com.store.comehere.domain.core.products.Products;
import com.store.comehere.domain.core.suppliers.ISuppliersRepository;
import com.store.comehere.domain.core.suppliers.Suppliers;
import com.store.comehere.application.core.products.ProductsAppService;    
import com.store.comehere.application.core.suppliers.SuppliersAppService;    
import com.store.comehere.DatabaseContainerConfig;
import com.store.comehere.domain.core.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(properties = "spring.profiles.active=test")
public class InventoryControllerTest extends DatabaseContainerConfig{
	
	@Autowired
	protected SortHandlerMethodArgumentResolver sortArgumentResolver;

	@Autowired
	@Qualifier("inventoryRepository") 
	protected IInventoryRepository inventory_repository;
	
	@Autowired
	@Qualifier("productsRepository") 
	protected IProductsRepository productsRepository;
	
	@Autowired
	@Qualifier("suppliersRepository") 
	protected ISuppliersRepository suppliersRepository;
	

	@SpyBean
	@Qualifier("inventoryAppService")
	protected InventoryAppService inventoryAppService;
	
    @SpyBean
    @Qualifier("productsAppService")
	protected ProductsAppService  productsAppService;
	
    @SpyBean
    @Qualifier("suppliersAppService")
	protected SuppliersAppService  suppliersAppService;
	
	@SpyBean
	protected LoggingHelper logHelper;

	@SpyBean
	protected Environment env;

	@Mock
	protected Logger loggerMock;

	protected Inventory inventory;

	protected MockMvc mvc;
	
	@Autowired
	EntityManagerFactory emf;
	
    static EntityManagerFactory emfs;
    
    static int relationCount = 10;
    static int yearCount = 1971;
    static int dayCount = 10;
	private BigDecimal bigdec = new BigDecimal(1.2);
    
	int countProducts = 10;
	
	int countSuppliers = 10;
	
	@PostConstruct
	public void init() {
	emfs = emf;
	}

	@AfterClass
	public static void cleanup() {
		EntityManager em = emfs.createEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("truncate table public.inventory CASCADE").executeUpdate();
		em.createNativeQuery("truncate table public.products CASCADE").executeUpdate();
		em.createNativeQuery("truncate table public.suppliers CASCADE").executeUpdate();
		em.getTransaction().commit();
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
	public Suppliers createSuppliersEntity() {
	
		if(countSuppliers>60) {
			countSuppliers = 10;
		}

		if(dayCount>=31) {
			dayCount = 10;
			yearCount++;
		}
		
		Suppliers suppliersEntity = new Suppliers();
		
  		suppliersEntity.setAddress(String.valueOf(relationCount));
  		suppliersEntity.setContactEmail(String.valueOf(relationCount));
  		suppliersEntity.setContactName(String.valueOf(relationCount));
  		suppliersEntity.setContactPhone(String.valueOf(relationCount));
		suppliersEntity.setCreatedAt(SearchUtils.stringToLocalDateTime(yearCount+"-09-"+dayCount+" 05:25:22"));
  		suppliersEntity.setName(String.valueOf(relationCount));
		suppliersEntity.setSupplierId(relationCount);
		suppliersEntity.setUpdatedAt(SearchUtils.stringToLocalDateTime(yearCount+"-09-"+dayCount+" 05:25:22"));
		suppliersEntity.setVersiono(0L);
		relationCount++;
		if(!suppliersRepository.findAll().contains(suppliersEntity))
		{
			 suppliersEntity = suppliersRepository.save(suppliersEntity);
		}
		countSuppliers++;
	    return suppliersEntity;
	}

	public Inventory createEntity() {
		Products products = createProductsEntity();
		Suppliers suppliers = createSuppliersEntity();
	
		Inventory inventoryEntity = new Inventory();
		inventoryEntity.setInventoryId(1);
    	inventoryEntity.setLastRestocked(SearchUtils.stringToLocalDateTime("1996-09-01 09:15:22"));
		inventoryEntity.setQuantity(1);
		inventoryEntity.setVersiono(0L);
		inventoryEntity.setProducts(products);
		inventoryEntity.setSuppliers(suppliers);
		
		return inventoryEntity;
	}
    public CreateInventoryInput createInventoryInput() {
	
	    CreateInventoryInput inventoryInput = new CreateInventoryInput();
    	inventoryInput.setLastRestocked(SearchUtils.stringToLocalDateTime("1996-08-10 05:25:22"));
		inventoryInput.setQuantity(5);
		
		return inventoryInput;
	}

	public Inventory createNewEntity() {
		Inventory inventory = new Inventory();
		inventory.setInventoryId(3);
    	inventory.setLastRestocked(SearchUtils.stringToLocalDateTime("1996-08-11 05:35:22"));
		inventory.setQuantity(3);
		
		return inventory;
	}
	
	public Inventory createUpdateEntity() {
		Inventory inventory = new Inventory();
		inventory.setInventoryId(4);
    	inventory.setLastRestocked(SearchUtils.stringToLocalDateTime("1996-09-09 05:45:22"));
		inventory.setQuantity(4);
		
		return inventory;
	}
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
    
		final InventoryController inventoryController = new InventoryController(inventoryAppService, productsAppService, suppliersAppService,
		logHelper,env);
		when(logHelper.getLogger()).thenReturn(loggerMock);
		doNothing().when(loggerMock).error(anyString());

		this.mvc = MockMvcBuilders.standaloneSetup(inventoryController)
				.setCustomArgumentResolvers(sortArgumentResolver)
				.setControllerAdvice()
				.build();
	}

	@Before
	public void initTest() {

		inventory= createEntity();
		List<Inventory> list= inventory_repository.findAll();
		if(!list.contains(inventory)) {
			inventory=inventory_repository.save(inventory);
		}

	}
	
	@Test
	public void FindById_IdIsValid_ReturnStatusOk() throws Exception {
	
		mvc.perform(get("/inventory/" + inventory.getInventoryId()+"/")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}  

	@Test
	public void FindById_IdIsNotValid_ReturnStatusNotFound() {

		 org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(get("/inventory/999")
				.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Not found"));

	}
	@Test
	public void CreateInventory_InventoryDoesNotExist_ReturnStatusOk() throws Exception {
		CreateInventoryInput inventoryInput = createInventoryInput();	
		
	    
		Products products =  createProductsEntity();

		inventoryInput.setProductId(Integer.parseInt(products.getProductId().toString()));
	    
		Suppliers suppliers =  createSuppliersEntity();

		inventoryInput.setSupplierId(Integer.parseInt(suppliers.getSupplierId().toString()));
		
		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
	
		String json = ow.writeValueAsString(inventoryInput);

		mvc.perform(post("/inventory").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isOk());
	}     
	
	@Test
	public void CreateInventory_productsDoesNotExists_ThrowEntityNotFoundException() throws Exception{

		CreateInventoryInput inventory = createInventoryInput();
		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
	
		String json = ow.writeValueAsString(inventory);

		org.assertj.core.api.Assertions.assertThatThrownBy(() ->
		mvc.perform(post("/inventory")
				.contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isNotFound()));

	}    
	
	@Test
	public void CreateInventory_suppliersDoesNotExists_ThrowEntityNotFoundException() throws Exception{

		CreateInventoryInput inventory = createInventoryInput();
		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
	
		String json = ow.writeValueAsString(inventory);

		org.assertj.core.api.Assertions.assertThatThrownBy(() ->
		mvc.perform(post("/inventory")
				.contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isNotFound()));

	}    

	@Test
	public void DeleteInventory_IdIsNotValid_ThrowEntityNotFoundException() {

        doReturn(null).when(inventoryAppService).findById(999);
        org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(delete("/inventory/999")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())).hasCause(new EntityNotFoundException("There does not exist a inventory with a id=999"));

	}  

	@Test
	public void Delete_IdIsValid_ReturnStatusNoContent() throws Exception {
	
	 	Inventory entity =  createNewEntity();
	 	entity.setVersiono(0L);
		Products products = createProductsEntity();
		entity.setProducts(products);
		Suppliers suppliers = createSuppliersEntity();
		entity.setSuppliers(suppliers);
		entity = inventory_repository.save(entity);
		

		FindInventoryByIdOutput output= new FindInventoryByIdOutput();
		output.setInventoryId(entity.getInventoryId());
		output.setQuantity(entity.getQuantity());
		
         Mockito.doReturn(output).when(inventoryAppService).findById(entity.getInventoryId());
       
    //    Mockito.when(inventoryAppService.findById(entity.getInventoryId())).thenReturn(output);
        
		mvc.perform(delete("/inventory/" + entity.getInventoryId()+"/")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNoContent());
	}  


	@Test
	public void UpdateInventory_InventoryDoesNotExist_ReturnStatusNotFound() throws Exception {
   
        doReturn(null).when(inventoryAppService).findById(999);
        
        UpdateInventoryInput inventory = new UpdateInventoryInput();
		inventory.setInventoryId(999);
		inventory.setLastRestocked(SearchUtils.stringToLocalDateTime("1996-09-28 07:15:22"));
		inventory.setQuantity(999);

		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(inventory);

		 org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(
		 	put("/inventory/999")
		 	.contentType(MediaType.APPLICATION_JSON)
		 	.content(json))
			.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Unable to update. Inventory with id=999 not found."));
	}    

	@Test
	public void UpdateInventory_InventoryExists_ReturnStatusOk() throws Exception {
		Inventory entity =  createUpdateEntity();
		entity.setVersiono(0L);
		
		Products products = createProductsEntity();
		entity.setProducts(products);
		Suppliers suppliers = createSuppliersEntity();
		entity.setSuppliers(suppliers);
		entity = inventory_repository.save(entity);
		FindInventoryByIdOutput output= new FindInventoryByIdOutput();
		output.setInventoryId(entity.getInventoryId());
		output.setLastRestocked(entity.getLastRestocked());
		output.setQuantity(entity.getQuantity());
		output.setVersiono(entity.getVersiono());
		
        Mockito.when(inventoryAppService.findById(entity.getInventoryId())).thenReturn(output);
        
        
		
		UpdateInventoryInput inventoryInput = new UpdateInventoryInput();
		inventoryInput.setInventoryId(entity.getInventoryId());
		inventoryInput.setQuantity(entity.getQuantity());
		
		inventoryInput.setProductId(Integer.parseInt(products.getProductId().toString()));
		inventoryInput.setSupplierId(Integer.parseInt(suppliers.getSupplierId().toString()));

		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(inventoryInput);
	
		mvc.perform(put("/inventory/" + entity.getInventoryId()+"/").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isOk());

		Inventory de = createUpdateEntity();
		de.setInventoryId(entity.getInventoryId());
		inventory_repository.delete(de);
		

	}    
	@Test
	public void FindAll_SearchIsNotNullAndPropertyIsValid_ReturnStatusOk() throws Exception {

		mvc.perform(get("/inventory?search=inventoryId[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}
		
	
	@Test
	public void GetProducts_IdIsNotEmptyAndIdDoesNotExist_ReturnNotFound() {
  
	   org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(get("/inventory/999/products")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Not found"));
	
	}    
	
	@Test
	public void GetProducts_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
	
	   mvc.perform(get("/inventory/" + inventory.getInventoryId()+ "/products")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isOk());
	}  
	
	
	@Test
	public void GetSuppliers_IdIsNotEmptyAndIdDoesNotExist_ReturnNotFound() {
  
	   org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(get("/inventory/999/suppliers")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Not found"));
	
	}    
	
	@Test
	public void GetSuppliers_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
	
	   mvc.perform(get("/inventory/" + inventory.getInventoryId()+ "/suppliers")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isOk());
	}  
	
    
}

