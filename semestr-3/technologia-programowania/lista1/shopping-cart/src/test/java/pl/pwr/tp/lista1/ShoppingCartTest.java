package pl.pwr.tp.lista1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/** Unit tests for the shopping cart application. */
class ShoppingCartTest {

  @Test
  void shouldCalculateTotalForMultipleProducts() {
    final Product laptop = new Product("Laptop", 3000.0);
    final Product mouse = new Product("Mouse", 150.0);
    final ShoppingCart cart = new ShoppingCart();

    cart.addProduct(laptop, 1);
    cart.addProduct(mouse, 2);

    assertEquals(3300.0, cart.calculateTotal(), 0.001);
    assertEquals(2, cart.getItems().size());
  }

  @Test
  void shouldThrowExceptionWhenPriceIsNegative() {
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          new Product("Invalid", -10.0);
        });
  }
}