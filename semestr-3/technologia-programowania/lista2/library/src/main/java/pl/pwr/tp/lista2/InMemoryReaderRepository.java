package pl.pwr.tp.lista2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Przechowywanie czytelnikow w pamieci, w mapie: id na czytelnika. GRASP Polymorphism: jedna
 * z wymiennych implementacji ReaderRepository.
 */
public class InMemoryReaderRepository implements ReaderRepository {
  private final Map<Integer, Reader> readers = new HashMap<>();

  @Override
  public void save(Reader reader) {
    readers.put(reader.getId(), reader);
  }

  @Override
  public Reader findById(int id) {
    return readers.get(id);
  }

  @Override
  public List<Reader> findAll() {
    List<Reader> all = new ArrayList<>();
    readers.forEach((key, value) -> {
      all.add(value);
    });
    return all;
  }

}
