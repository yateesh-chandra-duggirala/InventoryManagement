package com.project.inventory;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class InventoryApplicationTest {

  @Test
  public void testMain() {
    assertDoesNotThrow(() -> InventoryApplication.main(new String[]{}));
  }

}
