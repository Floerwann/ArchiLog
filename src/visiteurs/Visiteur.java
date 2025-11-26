package visiteurs;

import application.Machine;
import application.Reseau;
import application.Switch;

/**
 * Interface pour les Visiteurs.
 */
public interface Visiteur {
  void agitSur(Reseau reseau);
  void agitSur(Switch sw);
  void agitSur(Machine machine);
}
