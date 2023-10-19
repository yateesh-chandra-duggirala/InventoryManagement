package com.project.inventory.service.impl;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.project.inventory.entity.InventoryItems;
import com.project.inventory.repository.ItemRepo;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class InventoryItemServiceImplTest {
  private InventoryItemServiceImpl inventoryItemServiceImpl;
  private ItemRepo itemRepo;
 
  @BeforeEach
  void setUp() {
    itemRepo = mock(ItemRepo.class);
    inventoryItemServiceImpl = new InventoryItemServiceImpl(itemRepo);
  }

  @Test
  void testUpdateInventoryItemIsValid() {
    int itemId = 39;
    InventoryItems existingItem = new InventoryItems("Kitkat", "Food Items", 123456,
                                                     "23-01-2023", 0, null, null);
    InventoryItems updatedItem = new InventoryItems("DairyMilk", "Food Items", 123456,
                                                    "23-01-2023", 0, null, null);
    when(itemRepo.findById(itemId)).thenReturn(Optional.of(existingItem));
    when(itemRepo.save(existingItem)).thenReturn(updatedItem);
    InventoryItems result = inventoryItemServiceImpl.updateInventoryItem(itemId, updatedItem);
    assertNotNull(result);
    assertEquals(updatedItem.getItemName(), result.getItemName());
  }
  
  @Test
  void testUpdateInventoryItemIsNotValid() {
    int itemId = 1;
    InventoryItems nonexistingItem = new InventoryItems("Kitkat", "Food Items", 123456,
                                                        "23-01-2023", 0, null, null);
    when(itemRepo.findById(itemId)).thenReturn(Optional.empty());
    InventoryItems result = inventoryItemServiceImpl.updateInventoryItem(itemId, nonexistingItem);
    assertNull(result);
  }

  @Test
  void testAddInventoryItem() {
    InventoryItems item = new InventoryItems();
    item.setItemName("Kitkat");
    item.setCategory("Food Items");
    item.setDateOfPurchase("23-01-2023");
    item.setBillNumber(123456);
    itemRepo.save(item);
    InventoryItems savedItem = itemRepo.findById(item.getItemId()).orElse(null);
    InventoryItems result = inventoryItemServiceImpl.addInventoryItem(item);
    assertEquals(savedItem, result);
  }

  @Test
  void testGetInventoryItems() {
    List<InventoryItems> repoResult = itemRepo.findAll();
    assertNotNull(repoResult);
    List<InventoryItems> result = inventoryItemServiceImpl.getInventoryItems();
    assertNotNull(result);
    assertEquals(repoResult, result);
  }

  @Test
  void testFindByInventoryItemIdIfExist() {
    int itemId = 39;
    InventoryItems nonexistingItem = new InventoryItems("Kitkat", "Food Items", 123456,
                                                         "23-01-2023", 0, null, null);
    when(itemRepo.findById(itemId)).thenReturn(Optional.of(nonexistingItem));
    Optional<InventoryItems> result = inventoryItemServiceImpl.findByInventoryItemId(itemId);
    assertTrue(result.isPresent());
    assertEquals(nonexistingItem.getBillNumber(), result.get().getBillNumber());
  }
  
  @Test
  void testFindByInventoryItemIdIfNotExist() {
    int itemId = 50;
    when(itemRepo.findById(itemId)).thenReturn(Optional.empty());
    Optional<InventoryItems> result = inventoryItemServiceImpl.findByInventoryItemId(itemId);
    assertFalse(result.isPresent());
  }
  
  @Test 
  void testFindByEmpIdIsNotNull() {
    List<InventoryItems> items = itemRepo.findByEmpIdIsNotNull();
    assertNotNull(items);
    List<InventoryItems> result = inventoryItemServiceImpl.findByEmpIdIsNotNull();
    assertNotNull(result);
    assertEquals(items, result);
  }
  
  @Test 
  void testFindByEmpIdIsNull() {
    List<InventoryItems> items = itemRepo.findByEmpIdIsNull();
    assertNotNull(items);
    List<InventoryItems> result = inventoryItemServiceImpl.findByEmpIdIsNull();
    assertNotNull(result);
    assertEquals(items, result);
  }
  
  @Test
  public void testDeleteIfNotExistItemId() {
    int itemId = 1;
    when(itemRepo.existsById(itemId)).thenReturn(false);
    inventoryItemServiceImpl.deleteInventoryItem(itemId);
  }
  
  @Test
  public void testDeleteInventoryItem() {
    int itemId = 39;
    when(itemRepo.existsById(itemId)).thenReturn(true);
    inventoryItemServiceImpl.deleteInventoryItem(itemId);
  }

}
