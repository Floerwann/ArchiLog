package reseau;

import application.Machine;
import application.Reseau;
import application.Switch;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe pour verifier le fonctionnement de ReseauVerifier.
 */
public class ReseauVerifierMain {
  
  /**
   * Main de la classe.

   * @param args Arguments
   */
  public static void main(String[] args) {


    // Création des switchs
    Switch switch1 = new Switch(2, true);
    Switch switch2 = new Switch(3, true);
    Switch switch3 = new Switch(256, false); // IP invalide volontaire
    List<Switch> lesSwitchs = new ArrayList<>();
    lesSwitchs.add(switch1);
    lesSwitchs.add(switch2);
    lesSwitchs.add(switch3);

    // Création des machines
    Machine machine1 = new Machine(4, "Machine1", switch1);
    Machine machine2 = new Machine(5, "Machine2", switch2);
    Machine machine3 = new Machine(3, "Machine3", switch2); // IP dupliquée volontaire
    List<Machine> lesMachines = new ArrayList<>();
    lesMachines.add(machine1);
    lesMachines.add(machine2);
    lesMachines.add(machine3);

    // Création du réseau
    Reseau reseau = new Reseau(
        "Reseau1",
        "192.168.1.0",
        "192.168.1.1",
        25,
        lesMachines,
        lesSwitchs
    );

    // Vérification du réseau
    boolean valide = ReseauVerifier.verifierReseau(reseau);

    // Affichage du rapport
    List<String> erreurs = ReseauVerifier.getErreurs();
    System.out.println("Le réseau est valide ? " + valide);
    System.out.println("Rapport d'erreurs :");
    for (String e : erreurs) {
      System.out.println(e);
    }
  }
}
