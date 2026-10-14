package pl.pwr.tp.lista1;

/** Represents a single product wtih a name and price. */
public class Product {
  private final String name;
  private final double price;

  /**
   * Creates a new product.
   *
   * @param name  product name
   * @param price product unit price
   */
  public Product(final String name, final double price) {
    if (price < 0) {
      throw new IllegalArgumentException("PRice cannot be negative");
    }
    this.name = name;
    this.price = price;
  }

  public String getName() {
    return name;
  }

  public double getPrice() {
    return price;
  }
}