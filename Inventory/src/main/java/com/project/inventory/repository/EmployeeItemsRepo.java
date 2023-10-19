package com.project.inventory.repository;

import com.project.inventory.entity.EmployeeItems;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Employee and assigned items for employee repository interface.
*/

@Repository
public interface EmployeeItemsRepo extends JpaRepository<EmployeeItems, Long> {
  /**
   * delete employee by id.
  */
  @Modifying
  @Transactional
  @Query("DELETE FROM EmployeeItems e WHERE e.empId = :empId")
  void deleteByEmpId(@Param("empId") Long empId);
}
