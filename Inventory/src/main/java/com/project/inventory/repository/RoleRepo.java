package com.project.inventory.repository;

import com.project.inventory.entity.Erole;
import com.project.inventory.entity.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * Role repository interface.
*/

@Repository
public interface RoleRepo extends JpaRepository<Role, Integer> {
  Optional<Role> findByName(Erole name);
}
