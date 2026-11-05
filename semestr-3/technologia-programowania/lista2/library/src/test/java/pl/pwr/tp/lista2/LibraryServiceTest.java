package pl.pwr.tp.lista2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LibraryServiceTest {
  private LibraryService service;

  @BeforeEach
  void setUp() {
    service = new LibraryService(new InMemoryBookRepository(), new InMemoryReaderRepository());
  }

  @Test
  void addingSameTitleTwiceCreatesOneBookWithTwoCopies() {
    service.addBook("Wiedzmin", "Sapkowski");
    service.addBook("Wiedzmin", "Sapkowski");

    assertEquals(1, service.findAllBooks().size());
    assertEquals(2, service.findAllBooks().get(0).getCopyList().size());
  }

  @Test
  void addingTwoReadersGivesThemDifferentIds() {
    Reader first = service.addReader("Krzysiu");
    Reader second = service.addReader("Maciek");
    assertEquals(2, service.findAllReaders().size());
    assertEquals(1, first.getId());
    assertEquals(2, second.getId());
  }

  @Test
  void rentingMarksCopyAsTakenAndAddsItToReader() {
    service.addBook("Wiedzmin", "Sapkowski");
    Reader reader = service.addReader("Krzysiu");

    Copy copy = service.rent(reader.getId(), "Wiedzmin");

    assertFalse(copy.isAvailable());
    assertTrue(reader.getRentedList().contains(copy));
  }

  @Test
  void rentingWhenNoFreeCopiesThrows() {
    service.addBook("Wiedzmin", "Sapkowski");
    Reader first = service.addReader("Krzysiu");
    Reader second = service.addReader("Maciek");
    service.rent(first.getId(), "Wiedzmin");

    assertThrows(IllegalStateException.class, () -> service.rent(second.getId(), "Wiedzmin"));
  }

  @Test
  void rentingByUnknownReaderThrows() {
    service.addBook("Wiedzmin", "Sapkowski");

    assertThrows(IllegalArgumentException.class, () -> service.rent(99, "Wiedzmin"));
  }

  @Test
  void rentingUnknownTitleThrows() {
    Reader reader = service.addReader("Krzysiu");

    assertThrows(IllegalArgumentException.class, () -> service.rent(reader.getId(), "Brak"));
  }

  @Test
  void countsOnlyAvailableCopies() {
    service.addBook("Wiedzmin", "Sapkowski");
    service.addBook("Wiedzmin", "Sapkowski");
    Reader reader = service.addReader("Krzysiu");
    service.rent(reader.getId(), "Wiedzmin");

    Book book = service.findAllBooks().get(0);
    assertEquals(2, book.getCopyList().size());
    assertEquals(1, book.countAvailableCopies());
  }
}