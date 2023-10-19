package com.project.inventory.controller;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.project.inventory.entity.InventoryItems;
import com.project.inventory.service.InventoryItemService;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;



class InventoryItemsControllerTest {
  @Mock
  private InventoryItemService inventoryItemService;

  @InjectMocks
  private InventoryItemsController inventoryItemsController;
  
  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }
  
  @Test
  void testGetInventoryItems() {
    List<InventoryItems> items = List.of(new InventoryItems(), new InventoryItems());
    when(inventoryItemService.getInventoryItems()).thenReturn(items);
    ResponseEntity<Object> response = inventoryItemsController.getInventoryItems();
    assertEquals(HttpStatus.OK, response.getStatusCode());
    @SuppressWarnings("unchecked")
    Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
    assertEquals("Successfully retrieved data!", responseBody.get("message"));
    assertNotNull(responseBody.get("items"));
    assertEquals(items, responseBody.get("items"));
  }

  @Test
  public void testGetInventoryItems_WhenServiceThrowsException() {
    String errorMessage = "Failed to retrieve data";
    Mockito.when(inventoryItemService.getInventoryItems()).thenThrow(
              new RuntimeException(errorMessage));
    ResponseEntity<Object> response = inventoryItemsController.getInventoryItems();
    assertEquals(HttpStatus.MULTI_STATUS, response.getStatusCode());
    @SuppressWarnings("unchecked")
    Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
    assertEquals(errorMessage, responseBody.get("message"));
    assertNull(responseBody.get("items"));
  }

  @Test
  void testFindByInventoryItemId() {
    int itemId = 1;
    Optional<InventoryItems> item = Optional.of(new InventoryItems());
    when(inventoryItemService.findByInventoryItemId(itemId)).thenReturn(item);
    ResponseEntity<Object> response = inventoryItemsController.findByInventoryItemId(itemId);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    @SuppressWarnings("unchecked")
    Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
    assertEquals("Successfully retrieved data!", responseBody.get("message"));
    assertNotNull(responseBody.get("items"));
    assertEquals(item, responseBody.get("items"));
  }
  
  @Test
  void testFindByInventoryItemIdIfFailed() {
    int itemId = 1;
    String errorMessage = "Failed to find item";
    when(inventoryItemService.findByInventoryItemId(itemId))
                .thenThrow(new RuntimeException(errorMessage));
    ResponseEntity<Object> response = inventoryItemsController.findByInventoryItemId(itemId);
    assertEquals(HttpStatus.MULTI_STATUS, response.getStatusCode());
    @SuppressWarnings("unchecked")
    Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
    assertEquals("Failed to find item", responseBody.get("message"));
    assertNull(responseBody.get("items"));
    verify(inventoryItemService, times(1)).findByInventoryItemId(itemId);
  }
  
  @Test
  void testAddInventoryItem() {
    InventoryItems newItem = new InventoryItems();
    when(inventoryItemService.addInventoryItem(any(InventoryItems.class))).thenReturn(newItem);
    ResponseEntity<Object> response = inventoryItemsController.addInventoryItem(newItem);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    @SuppressWarnings("unchecked")
    Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
    assertEquals("Successfully Added data!", responseBody.get("message"));
    assertNotNull(responseBody.get("items"));
    assertEquals(newItem, responseBody.get("items"));
  }
  
  @Test
  void testAddInventoryItemIfFailed() {
    InventoryItems item = new InventoryItems();
    String errorMessage = "Failed to add inventory item";
    when(inventoryItemService.addInventoryItem(item)).thenThrow(new RuntimeException(errorMessage));
    ResponseEntity<Object> response = inventoryItemsController.addInventoryItem(item);
    assertEquals(HttpStatus.MULTI_STATUS, response.getStatusCode());
    @SuppressWarnings("unchecked")
    Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
    assertEquals("Failed to add inventory item", responseBody.get("message"));
    assertNull(responseBody.get("items"));
    verify(inventoryItemService, times(1)).addInventoryItem(item);
  }

  @Test
  void testUpdateInventoryItem() {
    int itemId = 1;
    InventoryItems updatedItem = new InventoryItems();
    when(inventoryItemService.updateInventoryItem(eq(itemId), any(InventoryItems.class)))
          .thenReturn(updatedItem);
    ResponseEntity<Object> response = inventoryItemsController.updateInventoryItem(itemId, 
                                          updatedItem);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    @SuppressWarnings("unchecked")
    Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
    assertEquals("Successfully Updated data!", responseBody.get("message"));
    assertNotNull(responseBody.get("items"));
    assertEquals(updatedItem, responseBody.get("items"));
  }
  
  @Test 
  void testupdateInventoryItemIfFailed() {
    int itemId = 1;
    InventoryItems item = new InventoryItems();
    String errorMessage = "Failed to update inventory item";
    when(inventoryItemService.updateInventoryItem(itemId, item))
                        .thenThrow(new RuntimeException(errorMessage));
    ResponseEntity<Object> response = inventoryItemsController.updateInventoryItem(itemId, item);
    assertEquals(HttpStatus.MULTI_STATUS, response.getStatusCode());
    @SuppressWarnings("unchecked")
    Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
    assertEquals("Failed to update inventory item", responseBody.get("message"));
    assertNull(responseBody.get("items"));
    verify(inventoryItemService, times(1)).updateInventoryItem(itemId, item);
  }
  
  @Test
  void testDeleteInventoryItem() {
    ResponseEntity<Object> response = inventoryItemsController.deleteInventoryItem(anyInt());
    assertEquals(HttpStatus.OK, response.getStatusCode());
    @SuppressWarnings("unchecked")
    Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
    assertEquals("Successfully deleted data!", responseBody.get("message"));
    assertNull(responseBody.get("items"));
  }

  @Test
  void testFindByEmpIdIsNull() {
    List<InventoryItems> unassignedItems = List.of(new InventoryItems(), new InventoryItems());
    when(inventoryItemService.findByEmpIdIsNull()).thenReturn(unassignedItems);
    ResponseEntity<Object> response = inventoryItemsController.findByEmpIdIsNull();
    assertEquals(HttpStatus.OK, response.getStatusCode());
    @SuppressWarnings("unchecked")
    Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
    assertEquals("Successfully retrieved data!", responseBody.get("message"));
    assertNotNull(responseBody.get("items"));
    assertEquals(unassignedItems, responseBody.get("items"));
  }

  @Test
  void testFindByEmpIdIsNotNull() {
    List<InventoryItems> assignedItems = List.of(new InventoryItems(), new InventoryItems());
    when(inventoryItemService.findByEmpIdIsNotNull()).thenReturn(assignedItems);
    ResponseEntity<Object> response = inventoryItemsController.findByEmpIdIsNotNull();
    assertEquals(HttpStatus.OK, response.getStatusCode());
    @SuppressWarnings("unchecked")
    Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
    assertEquals("Successfully retrieved data!", responseBody.get("message"));
    assertNotNull(responseBody.get("items"));
    assertEquals(assignedItems, responseBody.get("items"));
  }
}
