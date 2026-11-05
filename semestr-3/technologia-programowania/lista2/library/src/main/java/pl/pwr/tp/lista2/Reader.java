package pl.pwr.tp.lista2;

import java.util.ArrayList;
import java.util.List;

/**
 * Czytelnik biblioteki. GRASP Information Expert: czytelnik zna liste swoich wypozyczonych
 * egzemplarzy.
 */
public class Reader {
  private final int id;
  private final String name;
  private final List<Copy> rented = new ArrayList<>();

  public Reader(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public List<Copy> getRentedList() {
    return rented;
  }

  public void rent(Copy copy) {
    copy.rent(this);
    rented.add(copy);
  }
}
