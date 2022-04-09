package com.assigment.bookstore.securityJwt.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.assigment.bookstore.cart.Cart;
import com.assigment.bookstore.cart.CartRepository;
import com.assigment.bookstore.exceptions.NotFoundException;
import com.assigment.bookstore.person.models.Person;
import com.assigment.bookstore.person.PersonRepository;
import com.assigment.bookstore.securityJwt.payload.request.LoginRequest;
import com.assigment.bookstore.securityJwt.payload.request.SignupRequest;
import com.assigment.bookstore.securityJwt.payload.response.JwtResponse;
import com.assigment.bookstore.securityJwt.payload.response.MessageResponse;
import com.assigment.bookstore.securityJwt.security.jwt.JwtUtils;
import com.assigment.bookstore.securityJwt.security.services.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assigment.bookstore.securityJwt.models.ERole;
import com.assigment.bookstore.securityJwt.models.Role;
import com.assigment.bookstore.securityJwt.models.User;
import com.assigment.bookstore.securityJwt.repository.RoleRepository;
import com.assigment.bookstore.securityJwt.repository.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	PersonRepository personRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	CartRepository cartRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt,
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail(), 
												 roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getUsername(), 
							 signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword()));

		Set<Role> roles = new HashSet<>();
		//By default user get only user role.
		Optional<Role> userRole = roleRepository.findByName(ERole.ROLE_USER);
		Role role = userRole.get();
		if(userRole.isEmpty()){
			log.info("Role not exist. Creating new one.");
			role = roleRepository.insert(new Role(ERole.ROLE_USER));
		}
		roles.add(role);

//		if (strRoles == null) {
//			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//			roles.add(userRole);
//		} else {
//			strRoles.forEach(role -> {
//				switch (role) {
//				case "admin":
//					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
//							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//					roles.add(adminRole);
//
//					break;
//				case "mod":
//					Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
//							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//					roles.add(modRole);
//
//					break;
//				default:
//					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//					roles.add(userRole);
//				}
//			});
//		}


		user.setRoles(roles);
		userRepository.save(user);

		Person person = new Person(user.getEmail());
		personRepository.save(person);
		createOneToOneUserPersonRelation(user.getEmail());
		createOneToOnePersonCartRelation(user.getEmail());

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

	private void createOneToOnePersonCartRelation(String email) {
		Person p = personRepository.findByEmail(email).orElseThrow(()->new NotFoundException("Person", email));
		Cart c = cartRepository.findByPersonEmail(email).orElseGet(()->cartRepository.save(cartRepository.save(new Cart(email))));
		p.setCart(c);
		personRepository.save(p);
	}


	private void addPersonToUser(String email){
		Person p = personRepository.findByEmail(email).orElseThrow(()->new NotFoundException("Person", email));
		User u = userRepository.findByEmail(email).orElseThrow(()->new NotFoundException("User", email));
		u.setPerson(p);
		userRepository.save(u);
	}
	private void addUserToPerson(String email){
		Person p = personRepository.findByEmail(email).orElseThrow(()->new NotFoundException("Person", email));
		User u = userRepository.findByEmail(email).orElseThrow(()->new NotFoundException("User", email));
		p.setUser(u);
		personRepository.save(p);
	}
	private void createOneToOneUserPersonRelation(String email) {
		addPersonToUser(email);
		addUserToPerson(email);
	}
}
