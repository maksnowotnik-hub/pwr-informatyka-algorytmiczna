package pl.pwr.tp.lista1;

/** Main application class demonstrating the shopping cart functionality. */
@SuppressWarnings("PMD.SystemPrintln")
public final class Main {

  private Main() {}

  /**
   * Application entry point.
   *
   * @param args command line arguments.
   */
  public static void main(final String[] args) {
    final ShoppingCart cart = new ShoppingCart();

    cart.addProduct(new Product("Laptop", 3000.0), 1);
    cart.addProduct(new Product("Mouse", 150.0), 2);
    cart.addProduct(new Product("Keyboard", 300.0), 1);

    for (final CartItem item : cart.getItems()) {
      System.out.println(
          item.getProduct().getName()
              + " x"
              + item.getQuantity()
              + " = "
              + item.getTotalPrice()
              + " PLN");
    }

    System.out.println("Total: " + cart.calculateTotal() + " PLN");
  }
}