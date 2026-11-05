package pl.pwr.tp.lista2;

import java.util.List;
import java.util.Scanner;

/**
 * Konsolowy interfejs uzytkownika. GRASP High Cohesion: klasa zajmuje sie tylko wczytywaniem
 * danych i wypisywaniem wynikow, cala logika jest w LibraryService.
 */
public class ConsoleUi {
  private final LibraryService service;
  private final Scanner scanner = new Scanner(System.in);

  public ConsoleUi(LibraryService service) {
    this.service = service;
  }

  /**
   * Glowna petla programu: wyswietla menu i wykonuje wybrane opcje az do wyboru 0.
   */
  public void run() {
    boolean running = true;
    while (running) {
      System.out.print("\n\n");
      printMenu();
      String choice = scanner.nextLine();
      switch (choice) {
        case "1" -> addBook();
        case "2" -> addReader();
        case "3" -> rent();
        case "4" -> showBooks();
        case "5" -> showReaders();
        case "0" -> running = false;
        default -> System.out.println("Nieznana opcja");
      }
    }
  }

  private void addBook() {
    System.out.print("Tytul: ");
    String title = scanner.nextLine();
    System.out.print("Autor: ");
    String author = scanner.nextLine();
    Copy copy = service.addBook(title, author);
    System.out.println("Dodano kopie o nr " + copy.getNumber() + " ksiazki " + title);
  }

  private void addReader() {
    System.out.print("Imie: ");
    String name = scanner.nextLine();
    Reader reader = service.addReader(name);
    System.out.println("Dodano czytelnika nr " + reader.getId());
  }

  private void rent() {

    try {
      System.out.print("Id wypozyczajacego: ");
      int id = Integer.parseInt(scanner.nextLine());
      System.out.print("Tytul: ");
      String title = scanner.nextLine();
      Copy copy = service.rent(id, title);
      System.out.println("Zostala wypozyczona kopia nr " + copy.getNumber());
    } catch (NumberFormatException e) {
      System.out.println("Id musi byc liczba");
    } catch (IllegalArgumentException | IllegalStateException e) {
      System.out.println(e.getMessage());
    }
  }

  private void showBooks() {
    List<Book> books = service.findAllBooks();
    System.out.println("Lista ksiazek: ");
    for (Book book : books) {
      System.out.print(book.getTitle());
      System.out.print(" (" + book.getAuthor() + ") - ");
      System.out.print("egzemplarzy: " + book.getCopyList().size());
      System.out.println(", wolnych: " + book.countAvailableCopies());
    }
  }

  private void showReaders() {
    List<Reader> readers = service.findAllReaders();
    System.out.println("Lista czytelnikow wraz z lista wypozyczonych przez nich ksiazek: ");
    for (Reader reader : readers) {
      System.out.println(reader.getId() + " " + reader.getName());
      List<Copy> rented = reader.getRentedList();
      for (Copy copy : rented) {
        Book book = copy.getBook();
        System.out.println("    " + copy.getNumber() + " " + book.getTitle());
      }
    }
  }

  private void printMenu() {
    System.out.println("1 - Dodaj ksiazke\n2 - Dodaj czytelnika\n3 - Wypozycz ksiazke");
    System.out.println("4 - Pokaz liste ksiazek oraz liczbe wszystkich i dostepnych egzemplarzy");
    System.out.println("5 - Pokaz liste czytelnikow oraz liste wypozyczonych przez nich ksiazek");
    System.out.println("0 - Zakoncz program");
  }
}
