package pl.pwr.tp.lista2;

import java.util.List;

/**
 * Przechowywanie ksiazek. GRASP Pure Fabrication: klasa spoza dziedziny, ktora zdejmuje z
 * obiektow domenowych odpowiedzialnosc za zapis danych. GRASP Protected Variations: sposob
 * przechowywania moze sie zmienic, wiec ukrywamy go za stabilnym interfejsem. Aby zapisywac
 * dane w bazie, wystarczy dodac klase implementujaca ten interfejs (np. SqlBookRepository)
 * i podmienic ja w klasie Main. LibraryService pozostaje wtedy bez zmian.
 */
public interface BookRepository {
  void save(Book book);

  Book findByTitle(String title);

  List<Book> findAll();
}