package com.project.inventory.service;

import com.project.inventory.entity.InventoryItems;
import java.util.List;
import java.util.Optional;
/**
 * Inventory items service interface.
*/

public interface InventoryItemService {
  InventoryItems addInventoryItem(InventoryItems inventoryItems);

  InventoryItems updateInventoryItem(int id, InventoryItems inventoryItems);

  List<InventoryItems> getInventoryItems();

  Optional<InventoryItems> findByInventoryItemId(int id);

  void deleteInventoryItem(int id);

  List<InventoryItems> findByEmpIdIsNull();

  List<InventoryItems> findByEmpIdIsNotNull();
}
