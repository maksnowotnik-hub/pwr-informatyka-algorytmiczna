package pl.pwr.tp.lista2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Przechowywanie ksiazek w pamieci, w mapie: tytul na ksiazke. GRASP Polymorphism: jedna z
 * wymiennych implementacji BookRepository.
 */
public class InMemoryBookRepository implements BookRepository {
  private final Map<String, Book> books = new HashMap<>();

  @Override
  public void save(Book book) {
    books.put(book.getTitle(), book);
  }

  @Override
  public Book findByTitle(String title) {
    return books.get(title);
  }

  @Override
  public List<Book> findAll() {
    List<Book> all = new ArrayList<>();
    books.forEach((key, value) -> {
      all.add(value);
    });
    return all;
  }
}
