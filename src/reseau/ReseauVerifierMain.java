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



    Switch switch1 = new Switch(2, true);
    Switch switch2 = new Switch(3, true);
    Switch switch3 = new Switch(777, false); 
    List<Switch> lesSwitchs = new ArrayList<>();
    lesSwitchs.add(switch1);
    lesSwitchs.add(switch2);
    lesSwitchs.add(switch3);


    Machine machine1 = new Machine(4, "Machine1", switch1);
    Machine machine2 = new Machine(5, "Machine2", switch2);
    Machine machine3 = new Machine(5, "Machine3", switch2);
    List<Machine> lesMachines = new ArrayList<>();
    lesMachines.add(machine1);
    lesMachines.add(machine2);
    lesMachines.add(machine3);


    Reseau reseau = new Reseau(
        "Reseau1",
        "192.168.1.0",
        "192.168.1.1",
        25,
        lesMachines,
        lesSwitchs
    );


    boolean valide = ReseauVerifier.verifierReseau(reseau);


    List<String> erreurs = ReseauVerifier.getErreurs();
    System.out.println("Le réseau est valide ? " + valide);
    System.out.println("Rapport d'erreurs :");
    for (String e : erreurs) {
      System.out.println(e);
    }
  }
}
