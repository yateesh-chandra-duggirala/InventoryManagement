package com.project.inventory.service.impl;

import com.project.inventory.entity.InventoryItems;
import com.project.inventory.repository.ItemRepo;
import com.project.inventory.service.InventoryItemService;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
/**
 * Inventory item service implementation.
*/

@Service
public class InventoryItemServiceImpl implements InventoryItemService {
  private final ItemRepo inventoryItemRepo;

  public InventoryItemServiceImpl(ItemRepo inventoryItemRepo) {
    this.inventoryItemRepo = inventoryItemRepo;
  }
  
  @Override
  public InventoryItems updateInventoryItem(int id, InventoryItems inventoryItems) {
    Optional<InventoryItems> inventoryPayload = inventoryItemRepo.findById(id);
    if (inventoryPayload.isPresent()) {
      inventoryPayload.get().setItemName(inventoryItems.getItemName());
      inventoryPayload.get().setCategory(inventoryItems.getCategory());
      inventoryPayload.get().setExpireDate(inventoryItems.getExpireDate());
      inventoryPayload.get().setWarranty(inventoryItems.getWarranty());
      inventoryPayload.get().setBillNumber(inventoryItems.getBillNumber());
      inventoryPayload.get().setDateOfPurchase(inventoryItems.getDateOfPurchase());
      inventoryPayload.get().setEmpId(inventoryItems.getEmpId());
      return inventoryItemRepo.save(inventoryPayload.get());
    } else {
      return null;
    }
  }

  @Override
  public InventoryItems addInventoryItem(InventoryItems inventoryItems) {
    return inventoryItemRepo.save(inventoryItems);
  }
       
  @Override
  public List<InventoryItems> getInventoryItems() {
    return inventoryItemRepo.findAll();
  }

  @Override
  public Optional<InventoryItems> findByInventoryItemId(@PathVariable int id) {
    return inventoryItemRepo.findById(id);
  }

  @Override
  public void deleteInventoryItem(@PathVariable int id) {
    inventoryItemRepo.deleteById(id);
  }
   
  @Override
  public List<InventoryItems> findByEmpIdIsNull() {
    return inventoryItemRepo.findByEmpIdIsNull();
  }
   
  @Override
  public List<InventoryItems> findByEmpIdIsNotNull() {
    return inventoryItemRepo.findByEmpIdIsNotNull();
  }
}