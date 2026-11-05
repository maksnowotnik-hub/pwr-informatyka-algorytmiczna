package pl.pwr.tp.lista2;

/**
 * Punkt startowy aplikacji, ktory sklada wszystkie obiekty razem.
 */
public class Main {

  /**
   * Tworzy repozytoria, serwis i interfejs uzytkownika. To jedyne miejsce, w ktorym wybieramy
   * implementacje repozytoriow, wiec przejscie na baze danych wymaga zmiany tylko tutaj.
   */
  public static void main(String[] args) {
    BookRepository bookRepo = new InMemoryBookRepository();
    ReaderRepository readerRepo = new InMemoryReaderRepository();
    LibraryService service = new LibraryService(bookRepo, readerRepo);
    ConsoleUi console = new ConsoleUi(service);
    console.run();
  }
}