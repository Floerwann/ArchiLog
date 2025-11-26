package visiteurs;

import java.util.List;
import application.Machine;
import application.Reseau;
import application.Switch;
import application.Systeme;

/**
 * Classe TestVisiteur.
 */
public class TestVisiteur {
  /**
   * Méthode main.

   * @param args Arguments
   */
  public static void main(String[] args) {
    Systeme sys = new Systeme();


    Switch sw1 = new Switch(1, true);
    Machine m1 = new Machine(10, "PC1", sw1);
    Machine m2 = new Machine(11, "PC2", sw1);
    List<Switch> switches = List.of(sw1);
    List<Machine> machines = List.of(m1, m2);
    Reseau r = new Reseau("R1", "192.168.1.0", "192.168.1.1", 24, machines, switches);

    sys.ajouterReseau(r);

    
    System.out.println("=== Affichage par switch ===");
    sys.afficherAvecVisiteur("R1", new VisiteurPourSwitch());


    System.out.println("\n=== Affichage toutes machines ===");
    sys.afficherAvecVisiteur("R1", new VisiteurToutesMachines());
  }
}
