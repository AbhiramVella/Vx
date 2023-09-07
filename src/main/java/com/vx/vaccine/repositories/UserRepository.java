package com.vx.vaccine.repositories;

import com.vx.vaccine.models.UserStatus;
import com.vx.vaccine.models.UserType;
import com.vx.vaccine.models.Users;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 *
 * @author Abhiram
 */
@Repository
public interface UserRepository extends JpaRepository<Users, Long>{
    Optional<Users> findByEmailAndPassword(String email, String password);
    Users findByEmail(String email);
    List<Users> findByUserType(UserType type);
    List<Users> findByUserTypeAndStatus(UserType type, UserStatus status);
}
