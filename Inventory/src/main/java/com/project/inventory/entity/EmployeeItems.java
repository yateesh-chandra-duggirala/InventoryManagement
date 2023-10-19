package com.project.inventory.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.List;
/**
 * Employee and their assigned items Controller class.
*/

@Entity
public class EmployeeItems {
  /**
   * empid attribute.
  */
  @Column(nullable = false)
  @Id
  private Long empId;
  /**
   * list of assigned items.
  */
  @Column(nullable = false)
  private List<Integer> assignedItems;
  /**
   * constructor.
  */
  
  public EmployeeItems() {
    super();
  }
  /**
   * parameter constructor.
  */
  
  public EmployeeItems(final Long empId, final List<Integer> assignedItems) {
    super();
    this.empId = empId;
    this.assignedItems = assignedItems;
  }
  
  public Long getEmpId() {
    return empId;
  }
  
  public void setEmpId(final Long empId) {
    this.empId = empId;
  }
  
  public List<Integer> getAssignedItems() {
    return assignedItems;
  }
  
  public void setAssignedItems(final List<Integer> assignedItems) {
    this.assignedItems = assignedItems;
  }
}
