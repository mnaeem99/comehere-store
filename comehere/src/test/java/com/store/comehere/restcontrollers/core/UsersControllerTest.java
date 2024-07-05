package com.store.comehere.restcontrollers.core;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
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
import javax.persistence.EntityExistsException;

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
import com.store.comehere.application.core.authorization.users.UsersAppService;
import com.store.comehere.application.core.authorization.users.dto.*;
import com.store.comehere.domain.core.authorization.users.IUsersRepository;
import com.store.comehere.domain.core.authorization.users.Users;

import com.store.comehere.application.core.authorization.userspermission.UserspermissionAppService;    
import com.store.comehere.application.core.authorization.usersrole.UsersroleAppService;    
import com.store.comehere.security.JWTAppService;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.store.comehere.application.core.authorization.usersrole.UsersroleAppService;
import com.store.comehere.application.core.authorization.userspermission.UserspermissionAppService;
import com.store.comehere.domain.core.authorization.userspreference.Userspreference;
import com.store.comehere.domain.core.authorization.userspreference.IUserspreferenceManager;
import com.store.comehere.DatabaseContainerConfig;
import com.store.comehere.domain.core.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(properties = "spring.profiles.active=test")
public class UsersControllerTest extends DatabaseContainerConfig{
	
	@Autowired
	protected SortHandlerMethodArgumentResolver sortArgumentResolver;

	@Autowired
	@Qualifier("usersRepository") 
	protected IUsersRepository users_repository;
	

	@SpyBean
	@Qualifier("usersAppService")
	protected UsersAppService usersAppService;
	
    @SpyBean
    @Qualifier("userspermissionAppService")
	protected UserspermissionAppService  userspermissionAppService;
	
    @SpyBean
    @Qualifier("usersroleAppService")
	protected UsersroleAppService  usersroleAppService;
	
    @SpyBean
	protected IUserspreferenceManager userspreferenceManager;
	
	@SpyBean
	protected JWTAppService jwtAppService;
	
	@SpyBean
    protected PasswordEncoder pEncoder;
    
	@SpyBean
	protected LoggingHelper logHelper;

	@SpyBean
	protected Environment env;

	@Mock
	protected Logger loggerMock;

	protected Users users;

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
		em.createNativeQuery("truncate table public.users CASCADE").executeUpdate();
		em.createNativeQuery("truncate table public.userspreference CASCADE").executeUpdate();
		em.getTransaction().commit();
	}
	

	public Users createEntity() {
	
		Users usersEntity = new Users();
        usersEntity.setEmailAddress("bbc@d.c");
		usersEntity.setFirstName("1");
		usersEntity.setIsActive(false);
		usersEntity.setIsEmailConfirmed(false);
		usersEntity.setLastName("1");
		usersEntity.setPassword("1");
		usersEntity.setPhoneNumber("1");
		usersEntity.setUserId(1L);
		usersEntity.setUsername("1");
		usersEntity.setVersiono(0L);
		
		return usersEntity;
	}
    public CreateUsersInput createUsersInput() {
	
	    CreateUsersInput usersInput = new CreateUsersInput();
        usersInput.setEmailAddress("pmk@d.c");
  		usersInput.setFirstName("5");
		usersInput.setIsActive(false);
		usersInput.setIsEmailConfirmed(false);
  		usersInput.setLastName("5");
  		usersInput.setPassword("5");
  		usersInput.setPhoneNumber("5");
  		usersInput.setUsername("5");
		
		return usersInput;
	}

	public Users createNewEntity() {
		Users users = new Users();
        users.setEmailAddress("bmc@d.c");
		users.setFirstName("3");
		users.setIsActive(false);
		users.setIsEmailConfirmed(false);
		users.setLastName("3");
		users.setPassword("3");
		users.setPhoneNumber("3");
		users.setUserId(3L);
		users.setUsername("3");
		
		return users;
	}
	
	public Users createUpdateEntity() {
		Users users = new Users();
        users.setEmailAddress("pmlp@d.c");
		users.setFirstName("4");
		users.setIsActive(false);
		users.setIsEmailConfirmed(false);
		users.setLastName("4");
		users.setPassword("4");
		users.setPhoneNumber("4");
		users.setUserId(4L);
		users.setUsername("4");
		
		return users;
	}
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
    
		final UsersController usersController = new UsersController(usersAppService, userspermissionAppService, usersroleAppService,
		pEncoder,jwtAppService,logHelper,env);
		when(logHelper.getLogger()).thenReturn(loggerMock);
		doNothing().when(loggerMock).error(anyString());

		this.mvc = MockMvcBuilders.standaloneSetup(usersController)
				.setCustomArgumentResolvers(sortArgumentResolver)
				.setControllerAdvice()
				.build();
	}

	@Before
	public void initTest() {

		users= createEntity();
		List<Users> list= users_repository.findAll();
		if(!list.contains(users)) {
			users=users_repository.save(users);
		}

	}
	
	@Test
	public void FindById_IdIsValid_ReturnStatusOk() throws Exception {
	
		mvc.perform(get("/users/" + users.getUserId()+"/")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}  

	@Test
	public void FindById_IdIsNotValid_ReturnStatusNotFound() {

		 org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(get("/users/999")
				.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Not found"));

	}
	@Test
	public void CreateUsers_UsersDoesNotExist_ReturnStatusOk() throws Exception {
        Mockito.doReturn(null).when(usersAppService).findByUsername(anyString());
	  
		CreateUsersInput users = createUsersInput();
		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(users);
		
		
		mvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(json))
		  .andExpect(status().isOk());
		 
		 users_repository.delete(createNewEntity());
		 
	}  
    
	@Test
	public void CreateUsers_UsersAlreadyExists_ThrowEntityExistsException() throws Exception {
	    FindUsersByUsernameOutput output= new FindUsersByUsernameOutput();
        output.setEmailAddress("bpc@g.c");
  		output.setFirstName("1");
		output.setIsActive(false);
		output.setIsEmailConfirmed(false);
  		output.setLastName("1");
  		output.setUsername("1");

        Mockito.doReturn(output).when(usersAppService).findByUsername(anyString());
	    CreateUsersInput users = createUsersInput();
	    ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
	
		String json = ow.writeValueAsString(users);
       
        org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(post("/users")
        		.contentType(MediaType.APPLICATION_JSON).content(json))
         .andExpect(status().isOk())).hasCause(new EntityExistsException("There already exists a users with Username =" + users.getUsername()));
	} 
	
	

	@Test
	public void DeleteUsers_IdIsNotValid_ThrowEntityNotFoundException() {

        doReturn(null).when(usersAppService).findById(999L);
        org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(delete("/users/999")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())).hasCause(new EntityNotFoundException("There does not exist a users with a id=999"));

	}  

	@Test
	public void Delete_IdIsValid_ReturnStatusNoContent() throws Exception {
	
	 	Users entity =  createNewEntity();
	 	entity.setVersiono(0L);
		entity = users_repository.save(entity);
		
		Userspreference userspreference = new Userspreference();
		userspreference.setUserId(entity.getUserId());
		userspreference.setUsers(entity);
		userspreference.setTheme("Abc");
		userspreference.setLanguage("abc");
	 	userspreference=userspreferenceManager.create(userspreference);

		FindUsersByIdOutput output= new FindUsersByIdOutput();
		output.setEmailAddress(entity.getEmailAddress());
		output.setFirstName(entity.getFirstName());
		output.setIsActive(entity.getIsActive());
		output.setIsEmailConfirmed(entity.getIsEmailConfirmed());
		output.setLastName(entity.getLastName());
		output.setUserId(entity.getUserId());
		output.setUsername(entity.getUsername());
		
         Mockito.doReturn(output).when(usersAppService).findById(any(Long.class));
       
      //   Mockito.when(usersAppService.findById(any(Long.class))).thenReturn(output);
        
		mvc.perform(delete("/users/" + entity.getUserId()+"/")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNoContent());
	}  


	@Test
	public void UpdateUsers_UsersDoesNotExist_ReturnStatusNotFound() throws Exception {
   
        doReturn(null).when(usersAppService).findById(999L);
        
        UpdateUsersInput users = new UpdateUsersInput();
        users.setEmailAddress("bmc@g.c");
  		users.setFirstName("999");
		users.setIsActive(false);
		users.setIsEmailConfirmed(false);
  		users.setLastName("999");
  		users.setPhoneNumber("999");
		users.setUserId(999L);
  		users.setUsername("999");

		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(users);

		 org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(
		 	put("/users/999")
		 	.contentType(MediaType.APPLICATION_JSON)
		 	.content(json))
			.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Unable to update. Users with id=999 not found."));
	}    

	@Test
	public void UpdateUsers_UsersExists_ReturnStatusOk() throws Exception {
		Users entity =  createUpdateEntity();
		entity.setVersiono(0L);
		
		entity = users_repository.save(entity);
		FindUsersWithAllFieldsByIdOutput output= new FindUsersWithAllFieldsByIdOutput();
		output.setEmailAddress(entity.getEmailAddress());
		output.setFirstName(entity.getFirstName());
		output.setIsActive(entity.getIsActive());
		output.setIsEmailConfirmed(entity.getIsEmailConfirmed());
		output.setLastName(entity.getLastName());
		output.setPassword(entity.getPassword());
		output.setPhoneNumber(entity.getPhoneNumber());
		output.setUserId(entity.getUserId());
		output.setUsername(entity.getUsername());
		output.setVersiono(entity.getVersiono());
		
		Mockito.when(usersAppService.findWithAllFieldsById(entity.getUserId())).thenReturn(output);
        
		
		UpdateUsersInput usersInput = new UpdateUsersInput();
		usersInput.setEmailAddress(entity.getEmailAddress());
		usersInput.setFirstName(entity.getFirstName());
		usersInput.setIsActive(entity.getIsActive());
		usersInput.setIsEmailConfirmed(entity.getIsEmailConfirmed());
		usersInput.setLastName(entity.getLastName());
		usersInput.setPassword(entity.getPassword());
		usersInput.setUserId(entity.getUserId());
		usersInput.setUsername(entity.getUsername());
		

		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(usersInput);
	
		mvc.perform(put("/users/" + entity.getUserId()+"/").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isOk());

		Users de = createUpdateEntity();
		de.setUserId(entity.getUserId());
		users_repository.delete(de);
		

	}    
	@Test
	public void FindAll_SearchIsNotNullAndPropertyIsValid_ReturnStatusOk() throws Exception {

		mvc.perform(get("/users?search=userId[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}
		
	
	
	@Test
	public void GetUserspermissions_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {

		Map<String,String> joinCol = new HashMap<String,String>();
		joinCol.put("userId", "1");
		
        Mockito.when(usersAppService.parseUserspermissionsJoinColumn("usersUserId")).thenReturn(joinCol);
		mvc.perform(get("/users/1/userspermissions?search=usersUserId[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isOk());
	}  
	
	@Test
	public void GetUserspermissions_searchIsNotEmpty() {
	
		Mockito.when(usersAppService.parseUserspermissionsJoinColumn(anyString())).thenReturn(null);
	 		  		    		  
	    org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(get("/users/1/userspermissions?search=usersUserId[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Invalid join column"));
	}    
	
	
	@Test
	public void GetUsersroles_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {

		Map<String,String> joinCol = new HashMap<String,String>();
		joinCol.put("userId", "1");
		
        Mockito.when(usersAppService.parseUsersrolesJoinColumn("usersUserId")).thenReturn(joinCol);
		mvc.perform(get("/users/1/usersroles?search=usersUserId[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isOk());
	}  
	
	@Test
	public void GetUsersroles_searchIsNotEmpty() {
	
		Mockito.when(usersAppService.parseUsersrolesJoinColumn(anyString())).thenReturn(null);
	 		  		    		  
	    org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(get("/users/1/usersroles?search=usersUserId[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Invalid join column"));
	}    
    
}

