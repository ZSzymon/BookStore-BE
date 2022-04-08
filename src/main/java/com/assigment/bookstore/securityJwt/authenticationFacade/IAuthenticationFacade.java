package com.assigment.bookstore.securityJwt.authenticationFacade;

import com.assigment.bookstore.securityJwt.security.services.UserDetailsImpl;
import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
    Authentication getAuthentication();

    UserDetailsImpl getUserDetailsImpl();
}
