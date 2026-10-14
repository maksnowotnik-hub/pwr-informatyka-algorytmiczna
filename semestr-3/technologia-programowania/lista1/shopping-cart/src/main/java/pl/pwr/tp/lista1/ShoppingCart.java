package pl.pwr.tp.lista1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Represents a shopping cart containing multiple cart items. */
public class ShoppingCart {
  private final List<CartItem> items = new ArrayList<>();

  /**
   * Adds a product with the specified quantity to the cart.
   *
   * @param product  the product to add.
   * @param quantity the number of units.
   */

  public void addProduct(final Product product, final int quantity) {
    items.add(new CartItem(product, quantity));
  }

  /**
   * Returns an unmodifiable view of the items in the cart.
   *
   * @return list of cart items.
   */
  public List<CartItem> getItems() {
    return Collections.unmodifiableList(items);
  }

  /**
   * Calculates the total cost of all items in the cart.
   *
   * @return total cart value.
   */
  public double calculateTotal() {
    double total = 0;
    for (final CartItem item : items) {
      total += item.getTotalPrice();
    }
    return total;
  }
}
