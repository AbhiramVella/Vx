package com.vx.vaccine.configs;

import com.vx.vaccine.models.Users;
import com.vx.vaccine.services.UserService;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 *
 * @author Abhiram
 */
@Component
@Scope("session")
public class MySessionInfo {
    
    private Users user;
    private UserService userService;
    
    protected Users getCurrentUser(){
        if(user == null){
            user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        }
        
        return user;
    }
}
