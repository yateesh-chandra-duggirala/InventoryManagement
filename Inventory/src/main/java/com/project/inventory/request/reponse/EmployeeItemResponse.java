package com.project.inventory.request.reponse;

import com.project.inventory.entity.InventoryItems;
import java.util.List;
/**
 * Employee item response class.
*/

public class EmployeeItemResponse {
  private Long empId;
  private List<InventoryItems> assignedItems;
  
  public EmployeeItemResponse() {
    super();
  }
  /**
   * parameter constructor.
  */
  
  public EmployeeItemResponse(Long empId, List<InventoryItems> assignedItems) {
    super();
    this.empId = empId;
    this.assignedItems = assignedItems;
  }

  public Long getEmpId() {
    return empId;
  }
  
  public void setEmpId(Long empId) {
    this.empId = empId;
  }
  
  public List<InventoryItems> getAssignedItems() {
    return assignedItems;
  }
  
  public void setAssignedItems(List<InventoryItems> assignedItems) {
    this.assignedItems = assignedItems;
  }
}
