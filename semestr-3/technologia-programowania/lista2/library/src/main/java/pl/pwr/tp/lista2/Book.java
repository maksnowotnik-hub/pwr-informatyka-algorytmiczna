package pl.pwr.tp.lista2;

import java.util.ArrayList;
import java.util.List;

/**
 * Tytul ksiazki wraz z jej egzemplarzami. GRASP Creator: Book tworzy obiekty Copy, bo je
 * przechowuje. GRASP Information Expert: Book zna swoje egzemplarze, wiec to ona szuka
 * wolnego egzemplarza i liczy dostepne.
 */
public class Book {
  private final String title;
  private final String author;
  private final List<Copy> copies = new ArrayList<>();

  public Book(String title, String author) {
    this.title = title;
    this.author = author;
  }

  public String getTitle() {
    return title;
  }

  public String getAuthor() {
    return author;
  }

  public List<Copy> getCopyList() {
    return copies;
  }

  /**
   * Tworzy nowy egzemplarz tej ksiazki i dodaje go do listy (GRASP Creator).
   */
  public Copy addCopy() {
    Copy copy = new Copy(copies.size() + 1, this);
    copies.add(copy);
    return copy;
  }

  /**
   * Zwraca pierwszy wolny egzemplarz albo null, gdy wszystkie sa wypozyczone (GRASP Expert).
   */
  public Copy findAvailableCopy() {
    for (Copy copy : copies) {
      if (copy.isAvailable()) {
        return copy;
      }
    }
    return null;
  }

  /**
   * Liczy wolne egzemplarze (GRASP Expert: tylko ksiazka zna swoje egzemplarze).
   */
  public int countAvailableCopies() {
    int l = 0;
    for (Copy copy : copies) {
      if (copy.isAvailable()) {
        l++;
      }
    }
    return l;
  }
}
