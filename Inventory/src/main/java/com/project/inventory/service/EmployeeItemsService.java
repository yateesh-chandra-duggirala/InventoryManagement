package com.project.inventory.service;

import com.project.inventory.entity.EmployeeItems;
import com.project.inventory.request.reponse.EmployeeItemResponse;
import java.util.List;
/**
 * Employee item service interface.
*/

public interface EmployeeItemsService {
  EmployeeItems addEmployeeItem(EmployeeItems employeeItems);
  
  EmployeeItems updateEmployeeItem(Long id, EmployeeItems employeeItems);
   
  List<EmployeeItems> getEmployeeItems();
  
  EmployeeItemResponse findByEmployeeItemId(Long id);
  
  void deleteEmployeeItem(Long id);
}
