package com.assigment.bookstore.securityJwt.authenticationFacade;

import com.assigment.bookstore.exceptions.NotFoundAuthenticationExecution;
import com.assigment.bookstore.person.models.Person;
import com.assigment.bookstore.securityJwt.security.services.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade implements IAuthenticationFacade {

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static Authentication getAuthenticationStatic() {
        return SecurityContextHolder.getContext().getAuthentication();
    }


    @Override
    public UserDetailsImpl getUserDetailsImpl() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetailsImpl userDetails;
        if (principal instanceof UserDetails) {
            userDetails = ((UserDetailsImpl)principal);
        } else {
            throw new NotFoundAuthenticationExecution();
        }
        return userDetails;
    }

    public static boolean isModifingOwnData(Person person, Authentication auth) {
        try{
            return auth != null && ((UserDetailsImpl) auth.getPrincipal()).getEmail().equals(person.getEmail());
        }catch (ClassCastException classCastException){
            return false;
        }
    }
    public boolean isModifingOwnData(String personString) {
        try{
            Authentication auth = getAuthentication();
            return auth != null && ((UserDetailsImpl) auth.getPrincipal()).getEmail().equals(personString);
        }catch (ClassCastException classCastException){
            return false;
        }
    }

    public static boolean isAdminStatic(){
        Authentication authentication = AuthenticationFacade.getAuthenticationStatic();
        return AuthenticationFacade.isAdminStatic(authentication);
    }
    public boolean isAdmin(){
        Authentication auth = getAuthentication();
        return auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
    public static boolean isAdminStatic(Authentication auth) {
        return auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
    public boolean isUser(Authentication auth) {

        return auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"));
    }
}