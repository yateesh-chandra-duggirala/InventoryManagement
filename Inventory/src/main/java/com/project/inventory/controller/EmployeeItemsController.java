package com.project.inventory.controller;

import com.project.inventory.entity.EmployeeItems;
import com.project.inventory.request.reponse.EmployeeItemResponse;
import com.project.inventory.request.reponse.ResponseHandler;
import com.project.inventory.service.EmployeeItemsService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * Employee Controller class.
*/

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/employeeitems")
@RestController
public class EmployeeItemsController {
  /**
     * Employeeitemservice instance used for employee and assigned items.
  */
  private final EmployeeItemsService empItemsService;
  /**
   * parameter constructor.
*/
  
  public EmployeeItemsController(final EmployeeItemsService empItemsService) {
    this.empItemsService = empItemsService;
  }
  /**
   * Assigning items to employees.
  */
  
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/assignitems")
  public ResponseEntity<Object> addEmployeeItems(@RequestBody final EmployeeItems employeeItems) {
    try {
      EmployeeItems items =  empItemsService.addEmployeeItem(employeeItems);
      return ResponseHandler.generateResponse("Successfully retrieved data!",
                                               HttpStatus.OK, "employeeItems", items);
    } catch (Exception e) {
      return ResponseHandler.generateResponse(e.getMessage(),
                                              HttpStatus.MULTI_STATUS, "employeeItems", null);
    }
  }
  /**
   * get assigned items of employee.
  */ 
  
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/assignitems")
  public ResponseEntity<Object> getEmployeeItems() {
    try {
      List<EmployeeItems> items =  empItemsService.getEmployeeItems();
      return ResponseHandler.generateResponse("Successfully retrieved data!",
                                               HttpStatus.OK, "employeeItems", items);
    } catch (Exception e) {
      return ResponseHandler.generateResponse(e.getMessage(),
                                              HttpStatus.MULTI_STATUS, "employeeItems", null);
    }
  }
  /**
   * get assigned items by Employee id.
  */
  
  @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
  @GetMapping("/assignitems/{empId}")
  public ResponseEntity<Object> findByEmployeeItemId(@PathVariable final Long empId) {
    try {
      EmployeeItemResponse items =  empItemsService.findByEmployeeItemId(empId);
      return ResponseHandler.generateResponse("Successfully retrieved data!",
                                               HttpStatus.OK, "employeeItems",
                                               items.getAssignedItems());
    } catch (Exception e) {
      return ResponseHandler.generateResponse("EmpId doesn't found",
                                              HttpStatus.MULTI_STATUS, "employeeItems", null);
    }
  }
  /**
   * updating assigned items to employees.
  */
  
  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/unassignitems/{empId}")
  public ResponseEntity<Object> updateEmployeeItems(@PathVariable final Long empId, 
                                           @RequestBody final EmployeeItems employeeItems) {
    try {
      EmployeeItems items = empItemsService.updateEmployeeItem(empId, employeeItems);
      return ResponseHandler.generateResponse("Successfully retrieved data!",
                                               HttpStatus.OK, "employeeItems", items);
    } catch (Exception e) {
      return ResponseHandler.generateResponse(e.getMessage(),
                                              HttpStatus.MULTI_STATUS, "employeeItems", null);
    }
  }
  
}