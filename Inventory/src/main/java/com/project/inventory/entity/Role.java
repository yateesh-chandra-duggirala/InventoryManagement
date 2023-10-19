package com.project.inventory.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

/**
 * Role Controller class.
*/

@Entity
@Table(name = "roles")
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  
  @NotEmpty(message = "Role is required")
  @Enumerated(EnumType.STRING)
  @Column(length = 20)
  private Erole name;

  public Role() {
    super();
  }
  
  public Role(final Erole name) {
    this.name = name;
  }
  
  public Integer getId() {
    return id;
  }
  
  public void setId(final Integer id) {
    this.id = id;
  }
  
  public Erole getName() {
    return name;
  }
  
  public void setName(final Erole name) {
    this.name = name;
  }
}
