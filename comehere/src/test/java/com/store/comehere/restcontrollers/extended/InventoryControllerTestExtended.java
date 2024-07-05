package com.store.comehere.restcontrollers.extended;

import com.store.comehere.DatabaseContainerConfig;
import com.store.comehere.commons.logging.LoggingHelper;
import com.store.comehere.application.extended.inventory.InventoryAppServiceExtended;
import com.store.comehere.domain.extended.inventory.IInventoryRepositoryExtended;
import com.store.comehere.domain.core.inventory.Inventory;
import com.store.comehere.domain.extended.products.IProductsRepositoryExtended;
import com.store.comehere.domain.extended.suppliers.ISuppliersRepositoryExtended;
import com.store.comehere.application.extended.products.ProductsAppServiceExtended;    
import com.store.comehere.application.extended.suppliers.SuppliersAppServiceExtended;    

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.AfterClass;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.core.env.Environment;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


/*Uncomment below annotations before running tests*/
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(properties = "spring.profiles.active=test")
public class InventoryControllerTestExtended extends DatabaseContainerConfig {
	
	@Autowired
	protected SortHandlerMethodArgumentResolver sortArgumentResolver;

	@Autowired
	@Qualifier("inventoryRepositoryExtended") 
	protected IInventoryRepositoryExtended inventory_repositoryExtended;
	
	@Autowired
	@Qualifier("productsRepositoryExtended") 
	protected IProductsRepositoryExtended productsRepositoryExtended;
	
	@Autowired
	@Qualifier("suppliersRepositoryExtended") 
	protected ISuppliersRepositoryExtended suppliersRepositoryExtended;
	

	@SpyBean
	@Qualifier("inventoryAppServiceExtended")
	protected InventoryAppServiceExtended inventoryAppServiceExtended;
	
    @SpyBean
    @Qualifier("productsAppServiceExtended")
	protected ProductsAppServiceExtended  productsAppServiceExtended;
	
    @SpyBean
    @Qualifier("suppliersAppServiceExtended")
	protected SuppliersAppServiceExtended  suppliersAppServiceExtended;
	
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
    
 	@PostConstruct
	public void init() {
	emfs = emf;
	}

	@AfterClass
	public static void cleanup() {
		EntityManager em = emfs.createEntityManager();
		//add your code you want to execute after class  
	}
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
    
		final InventoryControllerExtended inventoryController = new InventoryControllerExtended(inventoryAppServiceExtended, productsAppServiceExtended, suppliersAppServiceExtended,
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
     //add code required for initialization 
	}
		
	//Add your custom code here	
}
