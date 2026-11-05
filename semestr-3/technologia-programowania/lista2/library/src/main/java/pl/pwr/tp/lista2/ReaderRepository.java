package pl.pwr.tp.lista2;

import java.util.List;

/**
 * Przechowywanie czytelnikow. Tak jak BookRepository: GRASP Pure Fabrication i Protected
 * Variations. Baze danych dodaje sie przez nowa implementacje tego interfejsu (np.
 * SqlReaderRepository) podmieniona w klasie Main.
 */
public interface ReaderRepository {
  void save(Reader reader);

  Reader findById(int id);

  List<Reader> findAll();
}
