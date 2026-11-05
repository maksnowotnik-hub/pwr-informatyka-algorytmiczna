package pl.pwr.tp.lista2;

/**
 * Pojedynczy egzemplarz ksiazki. GRASP Information Expert: egzemplarz sam wie, czy jest wolny
 * (pole reader), wiec to on decyduje, czy mozna go wypozyczyc.
 */
public class Copy {
  private final int number;
  private final Book book;
  private Reader reader;

  public Copy(int number, Book book) {
    this.number = number;
    this.book = book;
  }

  public int getNumber() {
    return number;
  }

  public Book getBook() {
    return book;
  }

  public Reader getReader() {
    return reader;
  }

  public boolean isAvailable() {
    return reader == null;
  }

  /**
   * Wypozycza egzemplarz czytelnikowi. Gdy jest juz wypozyczony, rzuca IllegalStateException.
   */
  public void rent(Reader reader) {
    if (isAvailable()) {
      this.reader = reader;
    } else {
      throw new IllegalStateException("Egzemplarz jest juz wypozyczony");
    }
  }
}