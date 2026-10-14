package pl.pwr.tp.lista1;

/** Represents an item in the shopping cart with a product and quantity. */
public class CartItem {
  private final Product product;
  private final int quantity;

  /**
   * Creates a new cart item.
   *
   * @param product  the product to add.
   * @param quantity the number of units.
   */
  public CartItem(final Product product, final int quantity) {
    if (product == null) {
      throw new IllegalArgumentException("product cannot be null");
    }
    if (quantity <= 0) {
      throw new IllegalArgumentException("Quantity must be greater than zero");
    }
    this.product = product;
    this.quantity = quantity;
  }

  public Product getProduct() {
    return product;
  }

  public int getQuantity() {
    return quantity;
  }

  /**
   * Calculates the total price for this cart item.
   *
   * @return total price.
   */
  public double getTotalPrice() {
    return product.getPrice() * quantity;
  }
}