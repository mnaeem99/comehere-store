package com.store.comehere.restcontrollers.core;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.store.comehere.commons.search.SearchCriteria;
import com.store.comehere.commons.search.SearchUtils;
import com.store.comehere.commons.search.OffsetBasedPageRequest;
import com.store.comehere.application.core.authorization.users.IUsersAppService;
import com.store.comehere.application.core.authorization.users.dto.*;
import com.store.comehere.application.core.authorization.userspermission.IUserspermissionAppService;
import com.store.comehere.application.core.authorization.userspermission.dto.FindUserspermissionByIdOutput;
import com.store.comehere.application.core.authorization.usersrole.IUsersroleAppService;
import com.store.comehere.application.core.authorization.usersrole.dto.FindUsersroleByIdOutput;
import com.store.comehere.domain.core.authorization.users.Users;
import com.store.comehere.application.core.authorization.users.dto.FindUsersByIdOutput;
import com.store.comehere.security.JWTAppService;
import java.util.*;
import java.time.*;
import java.net.MalformedURLException;
import com.store.comehere.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

	@Qualifier("usersAppService")
	@NonNull protected final IUsersAppService _usersAppService;
    @Qualifier("userspermissionAppService")
	@NonNull  protected final IUserspermissionAppService  _userspermissionAppService;

    @Qualifier("usersroleAppService")
	@NonNull  protected final IUsersroleAppService  _usersroleAppService;

	@NonNull protected final PasswordEncoder pEncoder;

	@NonNull protected final JWTAppService _jwtAppService;

	@NonNull protected final LoggingHelper logHelper;

	@NonNull protected final Environment env;

    @PreAuthorize("hasAnyAuthority('USERSENTITY_CREATE')")
	@RequestMapping(method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<CreateUsersOutput> create( @RequestBody @Valid CreateUsersInput users) {
		FindUsersByUsernameOutput foundUsers = _usersAppService.findByUsername(users.getUsername());

	        if (foundUsers != null) {
	            logHelper.getLogger().error("There already exists a users with a Username='{}'", users.getUsername());
	            throw new EntityExistsException(
	                    String.format("There already exists a users with Username =%s", users.getUsername()));
	        }
	    users.setIsEmailConfirmed(true);
	    users.setPassword(pEncoder.encode(users.getPassword()));
	    foundUsers = _usersAppService.findByEmailAddress(users.getEmailAddress());

	        if (foundUsers != null) {
	            logHelper.getLogger().error("There already exists a users with a email ='{}'", users.getEmailAddress());
	            throw new EntityExistsException(
	                    String.format("There already exists a users with email =%s", users.getEmailAddress()));
	        }

		CreateUsersOutput output=_usersAppService.create(users);
		return new ResponseEntity<>(output, HttpStatus.OK);
	}

	// ------------ Delete users ------------
	@PreAuthorize("hasAnyAuthority('USERSENTITY_DELETE')")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = {"application/json"})
	public void delete(@PathVariable String id) {

    	FindUsersByIdOutput output = _usersAppService.findById(Long.valueOf(id));
    	if(output == null) {
    		throw new EntityNotFoundException(String.format("There does not exist a users with a id=%s", id));
    	}	

    	_usersAppService.delete(Long.valueOf(id));
    }

	@RequestMapping(value = "/updateProfile", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<UsersProfile> updateProfile(@RequestBody @Valid UsersProfile usersProfile) {

		Users users = _usersAppService.getUsers();

        FindUsersByUsernameOutput usersOutput;
		usersOutput = _usersAppService.findByUsername(users.getUsername());
		if(usersOutput != null &&
		usersOutput.getUserId() != users.getUserId() )
		{
			logHelper.getLogger().error("There already exists a users with user name='{}'", users.getUsername());
			throw new EntityExistsException(
					String.format("There already exists a users with user name=%s", users.getUsername()));
		}
		usersOutput = _usersAppService.findByEmailAddress(users.getEmailAddress());
		if(usersOutput != null &&
		usersOutput.getUserId() != users.getUserId() )
		{
			logHelper.getLogger().error("There already exists a users with a email='{}'", users.getEmailAddress());
			throw new EntityExistsException(
					String.format("There already exists a users with a email=%s", users.getEmailAddress()));
		}

		FindUsersWithAllFieldsByIdOutput currentUsers =  _usersAppService.findWithAllFieldsById(users.getUserId());
		return new ResponseEntity<>(_usersAppService.updateUsersProfile(currentUsers,usersProfile), HttpStatus.OK);
	}

	// ------------ Update users ------------
    @PreAuthorize("hasAnyAuthority('USERSENTITY_UPDATE')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<UpdateUsersOutput> update(@PathVariable String id,  @RequestBody @Valid UpdateUsersInput users) {

	    FindUsersWithAllFieldsByIdOutput currentUsers = _usersAppService.findWithAllFieldsById(Long.valueOf(id));
		if(currentUsers == null) {
			throw new EntityNotFoundException(String.format("Unable to update. Users with id=%s not found.", id));
		}
	    users.setPassword(pEncoder.encode(currentUsers.getPassword()));
	    if(currentUsers.getIsActive() && !users.getIsActive()) {
           _jwtAppService.deleteAllUserTokens(currentUsers.getUsername());
        }

		users.setVersiono(currentUsers.getVersiono());
	    UpdateUsersOutput output = _usersAppService.update(Long.valueOf(id),users);
		return new ResponseEntity<>(output, HttpStatus.OK);
	}
    
	@RequestMapping(value = "/getProfile",method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<UsersProfile> getProfile() {

		Users users = _usersAppService.getUsers();

		FindUsersByIdOutput currentUsers = _usersAppService.findById(users.getUserId());
		return new ResponseEntity<>(_usersAppService.getProfile(currentUsers), HttpStatus.OK);
	}

    @RequestMapping(value = "/updateTheme", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<HashMap<String,String>> updateTheme(@RequestParam @Valid String theme) {

		Users users = _usersAppService.getUsers();
		_usersAppService.updateTheme(users, theme);

		String msg = "Theme updated successfully !";
		HashMap resultMap = new HashMap<String,String>();
		resultMap.put("message", msg);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateLanguage", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<HashMap<String,String>> updateLanguage(@RequestParam @Valid String language) {

		Users users = _usersAppService.getUsers();
		_usersAppService.updateLanguage(users, language);

		String msg = "Language updated successfully !";
		HashMap resultMap = new HashMap<String,String>();
		resultMap.put("message", msg);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

    @PreAuthorize("hasAnyAuthority('USERSENTITY_READ')")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<FindUsersByIdOutput> findById(@PathVariable String id) {

    	FindUsersByIdOutput output = _usersAppService.findById(Long.valueOf(id));
        if(output == null) {
    		throw new EntityNotFoundException("Not found");
    	}
    	
		return new ResponseEntity<>(output, HttpStatus.OK);
	}
    @PreAuthorize("hasAnyAuthority('USERSENTITY_READ')")
	@RequestMapping(method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<List<FindUsersByIdOutput>> find(@RequestParam(value="search", required=false) String search, @RequestParam(value = "offset", required=false) String offset, @RequestParam(value = "limit", required=false) String limit, Sort sort) throws EntityNotFoundException, MalformedURLException {

		if (offset == null) { offset = env.getProperty("comehere.offset.default"); }
		if (limit == null) { limit = env.getProperty("comehere.limit.default"); }

		Pageable Pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);
		SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);

		return new ResponseEntity<>(_usersAppService.find(searchCriteria,Pageable), HttpStatus.OK);
	}
	
    @PreAuthorize("hasAnyAuthority('USERSENTITY_READ')")
	@RequestMapping(value = "/{id}/userspermissions", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<List<FindUserspermissionByIdOutput>> getUserspermissions(@PathVariable String id, @RequestParam(value="search", required=false) String search, @RequestParam(value = "offset", required=false) String offset, @RequestParam(value = "limit", required=false) String limit, Sort sort) throws EntityNotFoundException, MalformedURLException {
   		if (offset == null) { offset = env.getProperty("comehere.offset.default"); }
		if (limit == null) { limit = env.getProperty("comehere.limit.default"); }

		Pageable pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);

		SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);
		Map<String,String> joinColDetails=_usersAppService.parseUserspermissionsJoinColumn(id);
		if(joinColDetails == null) {
			throw new EntityNotFoundException("Invalid join column");
		}

		searchCriteria.setJoinColumns(joinColDetails);

    	List<FindUserspermissionByIdOutput> output = _userspermissionAppService.find(searchCriteria,pageable);
    	
    	if(output == null) {
			throw new EntityNotFoundException("Not found");
		}
		
		return new ResponseEntity<>(output, HttpStatus.OK);
	}
    @PreAuthorize("hasAnyAuthority('USERSENTITY_READ')")
	@RequestMapping(value = "/{id}/usersroles", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<List<FindUsersroleByIdOutput>> getUsersroles(@PathVariable String id, @RequestParam(value="search", required=false) String search, @RequestParam(value = "offset", required=false) String offset, @RequestParam(value = "limit", required=false) String limit, Sort sort) throws EntityNotFoundException, MalformedURLException {
   		if (offset == null) { offset = env.getProperty("comehere.offset.default"); }
		if (limit == null) { limit = env.getProperty("comehere.limit.default"); }

		Pageable pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);

		SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);
		Map<String,String> joinColDetails=_usersAppService.parseUsersrolesJoinColumn(id);
		if(joinColDetails == null) {
			throw new EntityNotFoundException("Invalid join column");
		}

		searchCriteria.setJoinColumns(joinColDetails);

    	List<FindUsersroleByIdOutput> output = _usersroleAppService.find(searchCriteria,pageable);
    	
    	if(output == null) {
			throw new EntityNotFoundException("Not found");
		}
		
		return new ResponseEntity<>(output, HttpStatus.OK);
	}
}


