package com.store.comehere.restcontrollers.extended;

import com.store.comehere.DatabaseContainerConfig;
import com.store.comehere.commons.logging.LoggingHelper;
import com.store.comehere.application.extended.products.ProductsAppServiceExtended;
import com.store.comehere.domain.extended.products.IProductsRepositoryExtended;
import com.store.comehere.domain.core.products.Products;
import com.store.comehere.application.extended.inventory.InventoryAppServiceExtended;    
import com.store.comehere.application.extended.orderitems.OrderItemsAppServiceExtended;    

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
public class ProductsControllerTestExtended extends DatabaseContainerConfig {
	
	@Autowired
	protected SortHandlerMethodArgumentResolver sortArgumentResolver;

	@Autowired
	@Qualifier("productsRepositoryExtended") 
	protected IProductsRepositoryExtended products_repositoryExtended;
	

	@SpyBean
	@Qualifier("productsAppServiceExtended")
	protected ProductsAppServiceExtended productsAppServiceExtended;
	
    @SpyBean
    @Qualifier("inventoryAppServiceExtended")
	protected InventoryAppServiceExtended  inventoryAppServiceExtended;
	
    @SpyBean
    @Qualifier("orderItemsAppServiceExtended")
	protected OrderItemsAppServiceExtended  orderItemsAppServiceExtended;
	
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
    
		final ProductsControllerExtended productsController = new ProductsControllerExtended(productsAppServiceExtended, inventoryAppServiceExtended, orderItemsAppServiceExtended,
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
     //add code required for initialization 
	}
		
	//Add your custom code here	
}
