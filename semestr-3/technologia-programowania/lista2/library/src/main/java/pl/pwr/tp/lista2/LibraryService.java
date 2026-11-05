package pl.pwr.tp.lista2;

import java.util.List;

/**
 * Logika biblioteki: dodawanie egzemplarzy i czytelnikow oraz wypozyczanie. GRASP Controller:
 * przyjmuje operacje z interfejsu uzytkownika i deleguje prace do obiektow domenowych. GRASP
 * Low Coupling i Dependency Inversion: zalezy tylko od interfejsow repozytoriow, ktore dostaje
 * w konstruktorze.
 */
public class LibraryService {
  private final BookRepository books;
  private final ReaderRepository readers;
  private int nextReaderId = 1;

  public LibraryService(BookRepository books, ReaderRepository readers) {
    this.books = books;
    this.readers = readers;
  }

  /**
   * Dodaje egzemplarz ksiazki. Jesli tytulu jeszcze nie ma, najpierw tworzy i zapisuje ksiazke.
   */
  public Copy addBook(String title, String author) {
    Book book = books.findByTitle(title);
    if (book == null) {
      book = new Book(title, author);
      books.save(book);
    }
    return book.addCopy();
  }

  /**
   * Tworzy czytelnika z kolejnym numerem id i zapisuje go w repozytorium.
   */
  public Reader addReader(String name) {
    Reader reader = new Reader(nextReaderId, name);
    nextReaderId++;
    readers.save(reader);
    return reader;
  }

  /**
   * Wypozycza wolny egzemplarz czytelnikowi. Metoda tylko koordynuje, a sama zmiana stanu
   * odbywa sie w Reader i Copy (GRASP Controller, Information Expert).
   */
  public Copy rent(int readerId, String title) {

    Reader reader = readers.findById(readerId);
    Book book = books.findByTitle(title);

    if (reader == null) {
      throw new IllegalArgumentException("Nie ma czytelnika o id: " + readerId);
    }
    if (book == null) {
      throw new IllegalArgumentException("Nie ma ksiazki o tytule: " + title);
    }
    Copy copy = book.findAvailableCopy();
    if (copy == null) {
      throw new IllegalStateException("Brak wolnych egzemplarzy");
    }
    reader.rent(copy);
    return copy;
  }

  public List<Book> findAllBooks() {
    return books.findAll();
  }

  public List<Reader> findAllReaders() {
    return readers.findAll();
  }
}