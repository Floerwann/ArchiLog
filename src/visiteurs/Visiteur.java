package visiteurs;

import application.Machine;
import application.Reseau;
import application.Switch;

/**
 * Interface pour les Visiteurs.
 */
public interface Visiteur {
  /**
   * Méthode pour permettre au visiteur d'agir sur un réseau.

   * @param reseau Le reseau sur lequel agir
   */
  void agitSur(Reseau reseau);
  
  /**
   * Méthode pour permettre au visiteur d'agir sur un switch.

   * @param sw Le switch sur lequel agir
   */
  void agitSur(Switch sw);
  
  /**
   * Méthode pour permettre au visiteur d'agir sur une machine.

   * @param machine La Machine sur laquelle agir
   */
  void agitSur(Machine machine);
}
