package com.project.inventory.controller;

import com.project.inventory.entity.InventoryItems;
import com.project.inventory.request.reponse.ResponseHandler;
import com.project.inventory.service.InventoryItemService;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Inventory items controller class.
*/

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/inventory")
@RestController
public class InventoryItemsController {
  /**
    * Inventoryitemservice instance used for items.
  */
  private final InventoryItemService invItemService;
  /**
   * parameter constructor.
 */
  
  public InventoryItemsController(final InventoryItemService invItemService) {
    this.invItemService = invItemService;
  }
  /**
   * getting all items.
  */
  
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/InventoryItems")
  public ResponseEntity<Object> getInventoryItems() {
    try {
      List<InventoryItems> items =  invItemService.getInventoryItems();
      return ResponseHandler.generateResponse("Successfully retrieved data!",
                                               HttpStatus.OK, "items", items);
    } catch (Exception e) {
      return ResponseHandler.generateResponse(e.getMessage(),
                                              HttpStatus.MULTI_STATUS, "items", null);
    }
  }
  /**
   * getting item by id.
  */
  
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/InventoryItems/{itemId}")
  public ResponseEntity<Object> findByInventoryItemId(@PathVariable final int itemId) {
    try {
      Optional<InventoryItems> items =  invItemService.findByInventoryItemId(itemId);
      return ResponseHandler.generateResponse("Successfully retrieved data!",
                                               HttpStatus.OK, "items", items);
    } catch (Exception e) {
      return ResponseHandler.generateResponse("Failed to find item",
                                              HttpStatus.MULTI_STATUS, "items", null);
    }
  }
  /**
   * adding items.
  */
  
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/InventoryItems")
  public ResponseEntity<Object> addInventoryItem(@RequestBody final InventoryItems inventoryItems) {
    try {
      InventoryItems items = invItemService.addInventoryItem(inventoryItems);
      return ResponseHandler.generateResponse("Successfully Added data!",
                                               HttpStatus.OK, "items", items);
    } catch (Exception e) {
      return ResponseHandler.generateResponse(e.getMessage(),
                                              HttpStatus.MULTI_STATUS, "items", null);
    }
  }
  /**
   * updating items.
  */

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/InventoryItems/{itemId}")
  public ResponseEntity<Object> updateInventoryItem(@PathVariable final int itemId,
                                            @RequestBody final InventoryItems inventoryItems) {
    try {
      InventoryItems items =  invItemService.updateInventoryItem(itemId, inventoryItems);
      return ResponseHandler.generateResponse("Successfully Updated data!",
                                               HttpStatus.OK, "items", items);
    } catch (Exception e) {
      return ResponseHandler.generateResponse(e.getMessage(),
                                              HttpStatus.MULTI_STATUS, "items", null);
    }
  }
  /**
   * deleting items.
  */
  
  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/InventoryItems/{itemId}")
  public ResponseEntity<Object> deleteInventoryItem(@PathVariable final int itemId) {
    try {
      invItemService.deleteInventoryItem(itemId);
      return ResponseHandler.generateResponse("Successfully deleted data!",
                                               HttpStatus.OK, "items", null);
    } catch (Exception e) {
      return ResponseHandler.generateResponse(e.getMessage(),
                                              HttpStatus.MULTI_STATUS, "items", null);
    }
  }
  /**
   * getting unassigned items.
  */

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/InventoryItems/unassign")
  public ResponseEntity<Object> findByEmpIdIsNull() {
    try {
      List<InventoryItems> items = invItemService.findByEmpIdIsNull();
      return ResponseHandler.generateResponse("Successfully retrieved data!",
                                               HttpStatus.OK, "items", items);
    } catch (Exception e) {
      return ResponseHandler.generateResponse(e.getMessage(),
                                              HttpStatus.MULTI_STATUS, "items", null);
    }
  }
  /**
   * getting assigned items.
  */

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/InventoryItems/assign")
  public ResponseEntity<Object> findByEmpIdIsNotNull() {
    try {
      List<InventoryItems> items =  invItemService.findByEmpIdIsNotNull();
      return ResponseHandler.generateResponse("Successfully retrieved data!",
                                               HttpStatus.OK, "items", items);
    } catch (Exception e) {
      return ResponseHandler.generateResponse(e.getMessage(),
                                              HttpStatus.MULTI_STATUS, "items", null);
    }
  }

}