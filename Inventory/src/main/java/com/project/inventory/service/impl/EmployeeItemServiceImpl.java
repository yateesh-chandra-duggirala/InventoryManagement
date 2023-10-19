package com.project.inventory.service.impl;

import com.project.inventory.entity.EmployeeItems;
import com.project.inventory.entity.InventoryItems;
import com.project.inventory.repository.EmployeeItemsRepo;
import com.project.inventory.repository.ItemRepo;
import com.project.inventory.request.reponse.EmployeeItemResponse;
import com.project.inventory.service.EmployeeItemsService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
/**
 * Employee item service implementation class.
*/

@Service
public class EmployeeItemServiceImpl implements EmployeeItemsService {
  private final EmployeeItemsRepo employeeItemsRepo;
  private final ItemRepo itemRepo;
  
  public EmployeeItemServiceImpl(EmployeeItemsRepo employeeItemsRepo, ItemRepo itemRepo) {
    this.employeeItemsRepo = employeeItemsRepo;
    this.itemRepo = itemRepo;
  }
  
  @Override
  public EmployeeItems addEmployeeItem(EmployeeItems employeeItems) {
    for (int i = 0; i < employeeItems.getAssignedItems().size(); i++) {
      Optional<InventoryItems> inventoryItems = itemRepo
                    .findById(employeeItems.getAssignedItems().get(i));
      inventoryItems.get().setEmpId(employeeItems.getEmpId());
      itemRepo.save(inventoryItems.get());
    }
    return employeeItemsRepo.save(employeeItems);
  }

  @Override
  public EmployeeItems updateEmployeeItem(Long id, EmployeeItems employeeItems) {
    Optional<EmployeeItems> employeeItemsDb = employeeItemsRepo.findById(id);
    List<Integer> unassignedItems = new ArrayList<>(employeeItemsDb.get().getAssignedItems());
    unassignedItems.removeAll(employeeItems.getAssignedItems());
    for (int i = 0; i < unassignedItems.size(); i++) {
      Optional<InventoryItems> inventoryItems = itemRepo
             .findById(unassignedItems.get(i));
      inventoryItems.get().setEmpId(null);
      itemRepo.save(inventoryItems.get());
    }
    return employeeItemsRepo.save(employeeItems);
  }

  @Override
  public List<EmployeeItems> getEmployeeItems() {
    return employeeItemsRepo.findAll();
  }

  @Override
  public EmployeeItemResponse findByEmployeeItemId(Long id) {
    Optional<EmployeeItems> employeeItems = employeeItemsRepo.findById(id);
    System.out.println(employeeItems.get().getAssignedItems());
    EmployeeItemResponse employeeItemsResponse = new EmployeeItemResponse();
    List<InventoryItems> inventoryItemsList = new ArrayList<InventoryItems>();
    for (int i = 0; i < employeeItems.get().getAssignedItems().size(); i++) {
      Optional<InventoryItems> inventoryItems = itemRepo
              .findById(employeeItems.get().getAssignedItems().get(i));
      System.out.println(inventoryItems.get());
      inventoryItemsList.add(inventoryItems.get());
    }
    employeeItemsResponse.setEmpId(id);
    employeeItemsResponse.setAssignedItems(inventoryItemsList);
    return employeeItemsResponse;
  }

  @Override
  public void deleteEmployeeItem(Long id) {
    Optional<EmployeeItems> employeeItems = employeeItemsRepo.findById(id);
    if (employeeItems.isPresent()) {
      List<Integer> assignedItems = employeeItems.get().getAssignedItems();
      for (Integer itemId : assignedItems) {
        Optional<InventoryItems> inventoryItems = itemRepo.findById(itemId);
        if (inventoryItems.isPresent()) {
          InventoryItems item = inventoryItems.get();
          item.setEmpId(null);
          itemRepo.save(item);
        }
      }
    }
  }
}