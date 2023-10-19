package com.project.inventory.repository;

import com.project.inventory.entity.Users;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * user repository interface.
*/

@Repository
public interface UserRepo extends JpaRepository<Users, Long> {
  Optional<Users> findByEmail(String email);
  
  Boolean existsByEmail(String email);

}
