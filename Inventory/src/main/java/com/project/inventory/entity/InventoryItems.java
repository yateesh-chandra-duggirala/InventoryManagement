package com.project.inventory.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
/**
 * Inventory items entity class.
*/

@Entity
public class InventoryItems {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int itemId;
  
  @NotEmpty(message = "Item Name is required")
  @Column(nullable = false, length = 128)
  private String itemName;
  
  @NotEmpty(message = "Category is required")
  @Column(nullable = false, length = 128)
  private String category;
  
  @NotNull(message = "Bill Number is required")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false, length = 30)
  private int billNumber;
  
  @NotEmpty(message = "Date of Purchase is required")
  @Column(nullable = false)
  private String dateOfPurchase;
  
  @Column(nullable = true)
  private int warranty;
  
  @Column(nullable = true)
  private String expireDate;
  
  @Column(nullable = true)
  private Long empId;
  
  public InventoryItems() {
    super();
  }
  /**
   * parameter constructor.
  */
  
  public InventoryItems(final String itemName, final String category, final int billNumber, 
                        final String dateOfPurchase, final int warranty,
                        final String expireDate, final Long empId) {
    super();
    this.itemName = itemName;
    this.category = category;
    this.billNumber = billNumber;
    this.dateOfPurchase = dateOfPurchase;
    this.warranty = warranty;
    this.expireDate = expireDate;
    this.empId = empId;
  }
  
  public int getItemId() {
    return itemId;
  }
  
  public void setItemId(final int itemId) {
    this.itemId = itemId;
  }
  
  public String getItemName() {
    return itemName;
  }
  
  public void setItemName(final String itemName) {
    this.itemName = itemName;
  }
  
  public String getCategory() {
    return category;
  }
  
  public void setCategory(final String category) {
    this.category = category;
  }
  
  public int getBillNumber() {
    return billNumber;
  }
  
  public void setBillNumber(final int billNumber) {
    this.billNumber = billNumber;
  }
  
  public String getDateOfPurchase() {
    return dateOfPurchase;
  }
  
  public void setDateOfPurchase(final String dateOfPurchase) {
    this.dateOfPurchase = dateOfPurchase;
  }
  
  public int getWarranty() {
    return warranty;
  }
  
  public void setWarranty(final int warranty) {
    this.warranty = warranty;
  }
  
  public String getExpireDate() {
    return expireDate;
  }
  
  public void setExpireDate(final String expireDate) {
    this.expireDate = expireDate;
  }
  
  public Long getEmpId() {
    return empId;
  }
  
  public void setEmpId(final Long empId) {
    this.empId = empId;
  }
}