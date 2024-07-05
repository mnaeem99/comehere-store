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
import com.store.comehere.application.core.suppliers.SuppliersAppService;
import com.store.comehere.application.core.suppliers.dto.*;
import com.store.comehere.domain.core.suppliers.ISuppliersRepository;
import com.store.comehere.domain.core.suppliers.Suppliers;

import com.store.comehere.application.core.inventory.InventoryAppService;    
import com.store.comehere.DatabaseContainerConfig;
import com.store.comehere.domain.core.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(properties = "spring.profiles.active=test")
public class SuppliersControllerTest extends DatabaseContainerConfig{
	
	@Autowired
	protected SortHandlerMethodArgumentResolver sortArgumentResolver;

	@Autowired
	@Qualifier("suppliersRepository") 
	protected ISuppliersRepository suppliers_repository;
	

	@SpyBean
	@Qualifier("suppliersAppService")
	protected SuppliersAppService suppliersAppService;
	
    @SpyBean
    @Qualifier("inventoryAppService")
	protected InventoryAppService  inventoryAppService;
	
	@SpyBean
	protected LoggingHelper logHelper;

	@SpyBean
	protected Environment env;

	@Mock
	protected Logger loggerMock;

	protected Suppliers suppliers;

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
		em.createNativeQuery("truncate table public.suppliers CASCADE").executeUpdate();
		em.getTransaction().commit();
	}
	

	public Suppliers createEntity() {
	
		Suppliers suppliersEntity = new Suppliers();
		suppliersEntity.setAddress("1");
		suppliersEntity.setContactEmail("1");
		suppliersEntity.setContactName("1");
		suppliersEntity.setContactPhone("1");
    	suppliersEntity.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-09-01 09:15:22"));
		suppliersEntity.setName("1");
		suppliersEntity.setSupplierId(1);
    	suppliersEntity.setUpdatedAt(SearchUtils.stringToLocalDateTime("1996-09-01 09:15:22"));
		suppliersEntity.setVersiono(0L);
		
		return suppliersEntity;
	}
    public CreateSuppliersInput createSuppliersInput() {
	
	    CreateSuppliersInput suppliersInput = new CreateSuppliersInput();
  		suppliersInput.setAddress("5");
  		suppliersInput.setContactEmail("5");
  		suppliersInput.setContactName("5");
  		suppliersInput.setContactPhone("5");
    	suppliersInput.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-08-10 05:25:22"));
  		suppliersInput.setName("5");
    	suppliersInput.setUpdatedAt(SearchUtils.stringToLocalDateTime("1996-08-10 05:25:22"));
		
		return suppliersInput;
	}

	public Suppliers createNewEntity() {
		Suppliers suppliers = new Suppliers();
		suppliers.setAddress("3");
		suppliers.setContactEmail("3");
		suppliers.setContactName("3");
		suppliers.setContactPhone("3");
    	suppliers.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-08-11 05:35:22"));
		suppliers.setName("3");
		suppliers.setSupplierId(3);
    	suppliers.setUpdatedAt(SearchUtils.stringToLocalDateTime("1996-08-11 05:35:22"));
		
		return suppliers;
	}
	
	public Suppliers createUpdateEntity() {
		Suppliers suppliers = new Suppliers();
		suppliers.setAddress("4");
		suppliers.setContactEmail("4");
		suppliers.setContactName("4");
		suppliers.setContactPhone("4");
    	suppliers.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-09-09 05:45:22"));
		suppliers.setName("4");
		suppliers.setSupplierId(4);
    	suppliers.setUpdatedAt(SearchUtils.stringToLocalDateTime("1996-09-09 05:45:22"));
		
		return suppliers;
	}
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
    
		final SuppliersController suppliersController = new SuppliersController(suppliersAppService, inventoryAppService,
		logHelper,env);
		when(logHelper.getLogger()).thenReturn(loggerMock);
		doNothing().when(loggerMock).error(anyString());

		this.mvc = MockMvcBuilders.standaloneSetup(suppliersController)
				.setCustomArgumentResolvers(sortArgumentResolver)
				.setControllerAdvice()
				.build();
	}

	@Before
	public void initTest() {

		suppliers= createEntity();
		List<Suppliers> list= suppliers_repository.findAll();
		if(!list.contains(suppliers)) {
			suppliers=suppliers_repository.save(suppliers);
		}

	}
	
	@Test
	public void FindById_IdIsValid_ReturnStatusOk() throws Exception {
	
		mvc.perform(get("/suppliers/" + suppliers.getSupplierId()+"/")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}  

	@Test
	public void FindById_IdIsNotValid_ReturnStatusNotFound() {

		 org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(get("/suppliers/999")
				.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Not found"));

	}
	@Test
	public void CreateSuppliers_SuppliersDoesNotExist_ReturnStatusOk() throws Exception {
		CreateSuppliersInput suppliersInput = createSuppliersInput();	
		
		
		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
	
		String json = ow.writeValueAsString(suppliersInput);

		mvc.perform(post("/suppliers").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isOk());
	}     
	

	@Test
	public void DeleteSuppliers_IdIsNotValid_ThrowEntityNotFoundException() {

        doReturn(null).when(suppliersAppService).findById(999);
        org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(delete("/suppliers/999")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())).hasCause(new EntityNotFoundException("There does not exist a suppliers with a id=999"));

	}  

	@Test
	public void Delete_IdIsValid_ReturnStatusNoContent() throws Exception {
	
	 	Suppliers entity =  createNewEntity();
	 	entity.setVersiono(0L);
		entity = suppliers_repository.save(entity);
		

		FindSuppliersByIdOutput output= new FindSuppliersByIdOutput();
		output.setName(entity.getName());
		output.setSupplierId(entity.getSupplierId());
		
         Mockito.doReturn(output).when(suppliersAppService).findById(entity.getSupplierId());
       
    //    Mockito.when(suppliersAppService.findById(entity.getSupplierId())).thenReturn(output);
        
		mvc.perform(delete("/suppliers/" + entity.getSupplierId()+"/")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNoContent());
	}  


	@Test
	public void UpdateSuppliers_SuppliersDoesNotExist_ReturnStatusNotFound() throws Exception {
   
        doReturn(null).when(suppliersAppService).findById(999);
        
        UpdateSuppliersInput suppliers = new UpdateSuppliersInput();
  		suppliers.setAddress("999");
  		suppliers.setContactEmail("999");
  		suppliers.setContactName("999");
  		suppliers.setContactPhone("999");
		suppliers.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-09-28 07:15:22"));
  		suppliers.setName("999");
		suppliers.setSupplierId(999);
		suppliers.setUpdatedAt(SearchUtils.stringToLocalDateTime("1996-09-28 07:15:22"));

		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(suppliers);

		 org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(
		 	put("/suppliers/999")
		 	.contentType(MediaType.APPLICATION_JSON)
		 	.content(json))
			.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Unable to update. Suppliers with id=999 not found."));
	}    

	@Test
	public void UpdateSuppliers_SuppliersExists_ReturnStatusOk() throws Exception {
		Suppliers entity =  createUpdateEntity();
		entity.setVersiono(0L);
		
		entity = suppliers_repository.save(entity);
		FindSuppliersByIdOutput output= new FindSuppliersByIdOutput();
		output.setAddress(entity.getAddress());
		output.setContactEmail(entity.getContactEmail());
		output.setContactName(entity.getContactName());
		output.setContactPhone(entity.getContactPhone());
		output.setCreatedAt(entity.getCreatedAt());
		output.setName(entity.getName());
		output.setSupplierId(entity.getSupplierId());
		output.setUpdatedAt(entity.getUpdatedAt());
		output.setVersiono(entity.getVersiono());
		
        Mockito.when(suppliersAppService.findById(entity.getSupplierId())).thenReturn(output);
        
        
		
		UpdateSuppliersInput suppliersInput = new UpdateSuppliersInput();
		suppliersInput.setName(entity.getName());
		suppliersInput.setSupplierId(entity.getSupplierId());
		

		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(suppliersInput);
	
		mvc.perform(put("/suppliers/" + entity.getSupplierId()+"/").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isOk());

		Suppliers de = createUpdateEntity();
		de.setSupplierId(entity.getSupplierId());
		suppliers_repository.delete(de);
		

	}    
	@Test
	public void FindAll_SearchIsNotNullAndPropertyIsValid_ReturnStatusOk() throws Exception {

		mvc.perform(get("/suppliers?search=supplierId[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}
		
	
	
	@Test
	public void GetInventorys_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {

		Map<String,String> joinCol = new HashMap<String,String>();
		joinCol.put("supplierId", "1");
		
        Mockito.when(suppliersAppService.parseInventorysJoinColumn("supplierId")).thenReturn(joinCol);
		mvc.perform(get("/suppliers/1/inventorys?search=supplierId[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isOk());
	}  
	
	@Test
	public void GetInventorys_searchIsNotEmpty() {
	
		Mockito.when(suppliersAppService.parseInventorysJoinColumn(anyString())).thenReturn(null);
	 		  		    		  
	    org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(get("/suppliers/1/inventorys?search=supplierId[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Invalid join column"));
	}    
    
}

