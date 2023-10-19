package com.project.inventory.service.impl;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.project.inventory.entity.EmployeeItems;
import com.project.inventory.entity.InventoryItems;
import com.project.inventory.repository.EmployeeItemsRepo;
import com.project.inventory.repository.ItemRepo;
import com.project.inventory.request.reponse.EmployeeItemResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class EmployeeItemServiceImplTest {
  private EmployeeItemServiceImpl empItemServiceImpl;
  private EmployeeItemsRepo empItemRepo;
  private ItemRepo itemRepo;

  @BeforeEach
  void setUp() {
    empItemRepo = mock(EmployeeItemsRepo.class);
    itemRepo = mock(ItemRepo.class);
    empItemServiceImpl = new EmployeeItemServiceImpl(empItemRepo, itemRepo);
  }
  
  @Test
  public void testAddEmployeeItem() {
    EmployeeItems employeeItems = new EmployeeItems();
    employeeItems.setEmpId(109L);
    employeeItems.setAssignedItems(Arrays.asList(1, 2));
    InventoryItems inventoryItem1 = new InventoryItems();
    inventoryItem1.setItemId(1);
    InventoryItems inventoryItem2 = new InventoryItems();
    inventoryItem2.setItemId(2);
    when(itemRepo.findById(1)).thenReturn(Optional.of(inventoryItem1));
    when(itemRepo.findById(2)).thenReturn(Optional.of(inventoryItem2));
    when(empItemRepo.save(employeeItems)).thenReturn(employeeItems);
    EmployeeItems result = empItemServiceImpl.addEmployeeItem(employeeItems);
    verify(itemRepo, times(2)).save(any(InventoryItems.class));
    assertEquals(employeeItems, result);
  }

  @Test
  void testUpdateEmployeeItem() {
    Long id = 109L;
    EmployeeItems existingEmployeeItems = new EmployeeItems();
    existingEmployeeItems.setEmpId(id);
    existingEmployeeItems.setAssignedItems(Arrays.asList(1, 2));
    EmployeeItems updatedEmployeeItems = new EmployeeItems(id, Arrays.asList(2));
    InventoryItems inventoryItem1 = new InventoryItems();
    inventoryItem1.setItemId(1);
    InventoryItems inventoryItem2 = new InventoryItems();
    inventoryItem2.setItemId(2);
    when(empItemRepo.findById(id)).thenReturn(Optional.of(existingEmployeeItems));
    when(itemRepo.findById(1)).thenReturn(Optional.of(inventoryItem1));
    when(itemRepo.findById(2)).thenReturn(Optional.of(inventoryItem2));
    when(empItemRepo.save(updatedEmployeeItems)).thenReturn(updatedEmployeeItems);
    EmployeeItems result = empItemServiceImpl.updateEmployeeItem(id, updatedEmployeeItems);
    verify(itemRepo).save(any(InventoryItems.class));
    assertEquals(updatedEmployeeItems, result);
  }

  @Test
  void testGetEmployeeItems() {
    List<EmployeeItems> employeeItemsList = new ArrayList<>();
    EmployeeItems employeeItems1 = new EmployeeItems();
    employeeItems1.setEmpId(1L);
    EmployeeItems employeeItems2 = new EmployeeItems();
    employeeItems2.setEmpId(2L);
    employeeItemsList.add(employeeItems1);
    employeeItemsList.add(employeeItems2);
    when(empItemRepo.findAll()).thenReturn(employeeItemsList);
    List<EmployeeItems> result = empItemServiceImpl.getEmployeeItems();
    assertEquals(2, result.size());
    assertTrue(result.contains(employeeItems1));
    assertTrue(result.contains(employeeItems2));
  }

  @Test
  void testFindByEmployeeItemId() {
    Long id = 109L;
    EmployeeItems employeeItems = new EmployeeItems();
    employeeItems.setEmpId(id);
    employeeItems.setAssignedItems(Arrays.asList(1, 2));
    InventoryItems inventoryItem1 = new InventoryItems();
    inventoryItem1.setItemId(1);
    InventoryItems inventoryItem2 = new InventoryItems();
    inventoryItem2.setItemId(2);
    when(empItemRepo.findById(id)).thenReturn(Optional.of(employeeItems));
    when(itemRepo.findById(1)).thenReturn(Optional.of(inventoryItem1));
    when(itemRepo.findById(2)).thenReturn(Optional.of(inventoryItem2));
    EmployeeItemResponse result = empItemServiceImpl.findByEmployeeItemId(id);
    assertEquals(id, result.getEmpId());
    assertEquals(2, result.getAssignedItems().size());
    assertTrue(result.getAssignedItems().contains(inventoryItem1));
    assertTrue(result.getAssignedItems().contains(inventoryItem2));
  }
   
  @Test
  public void testDeleteEmployeeItem() {
    EmployeeItems employeeItem = new EmployeeItems();
    employeeItem.setEmpId(1L);
    employeeItem.setAssignedItems(Arrays.asList(1, 2, 3));
    InventoryItems item1 = new InventoryItems();
    item1.setItemId(1);
    item1.setEmpId(1L);
    InventoryItems item2 = new InventoryItems();
    item2.setItemId(2);
    item2.setEmpId(1L);
    InventoryItems item3 = new InventoryItems();
    item3.setItemId(3);
    item3.setEmpId(1L);
    when(empItemRepo.findById(1L)).thenReturn(Optional.of(employeeItem));
    when(itemRepo.findById(1)).thenReturn(Optional.of(item1));
    when(itemRepo.findById(2)).thenReturn(Optional.of(item2));
    when(itemRepo.findById(3)).thenReturn(Optional.of(item3));
    empItemServiceImpl.deleteEmployeeItem(1L);
    verify(itemRepo, times(1)).save(item1);
    verify(itemRepo, times(1)).save(item2);
    verify(itemRepo, times(1)).save(item3);
    assertNull(item1.getEmpId());
    assertNull(item2.getEmpId());
    assertNull(item3.getEmpId());
  }
}
