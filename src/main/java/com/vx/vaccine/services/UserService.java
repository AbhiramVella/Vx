package com.vx.vaccine.services;

import com.vx.vaccine.models.UserStatus;
import com.vx.vaccine.models.UserType;
import com.vx.vaccine.models.Users;
import org.springframework.stereotype.Service;
import com.vx.vaccine.repositories.UserRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author Abhiram
 */
@Service
public class UserService {
    
    private final UserRepository userRepository;
    
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPassword(),
            // Add user authorities/roles here if needed
            Collections.emptyList()
        );
    }
    
    public Users getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }
    
    public List<Users> getUserByType(UserType type){
        return userRepository.findByUserType(type);
    }
    
    public List<Users> getUserByTypeAndStatus(UserType type, UserStatus status){
        return userRepository.findByUserTypeAndStatus(type, status);
    }
    
    public Optional<Users> getUserById(Long id) {
        return userRepository.findById(id);
    }
    
    public Users createUser(Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    
    public Users updateUser(Users user) {
        return userRepository.save(user);
    }
    
    public void deleteUser(Users user) {
        userRepository.delete(user);
    }
    
}
