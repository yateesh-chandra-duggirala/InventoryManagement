package com.project.inventory.repository;

import com.project.inventory.entity.InventoryItems;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * Item repository interface.
*/

@Repository
public interface ItemRepo extends JpaRepository<InventoryItems, Integer> {
  List<InventoryItems> findByEmpIdIsNull();
  
  List<InventoryItems> findByEmpIdIsNotNull();
  
  List<InventoryItems> findByEmpId(Long id);
}
