package systeme;

import application.Machine;
import application.Reseau;
import application.Switch;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe Main pour la classe Systeme.
 */
public class SystemeMain {
  
  /**
   * MAain de la classe.

   * @param args Arguments
   */
  public static void main(String[] args) {


    Systeme sys = new Systeme();

    // --- Création des switchs ---
    Switch switch1 = new Switch(2, true);
    Switch switch2 = new Switch(3, true);
    Switch switch3 = new Switch(256, false);

    List<Switch> lesSwitchs1 = new ArrayList<>();
    lesSwitchs1.add(switch1);
    lesSwitchs1.add(switch2);

    List<Switch> lesSwitchs2 = new ArrayList<>();
    lesSwitchs2.add(switch1);
    lesSwitchs2.add(switch3);

    // --- Création des machines ---
    Machine machine1 = new Machine(4, "Machine1", switch1);
    Machine machine2 = new Machine(5, "Machine2", switch2);
    Machine machine3 = new Machine(4, "Machine3", switch2);

    List<Machine> lesMachines1 = new ArrayList<>();
    lesMachines1.add(machine1);
    lesMachines1.add(machine2);

    List<Machine> lesMachines2 = new ArrayList<>();
    lesMachines2.add(machine1);
    lesMachines2.add(machine3);


    Reseau reseauValide = new Reseau("ReseauValide", "192.168.1.0", "192.168.1.1", 24,
                                     lesMachines1, lesSwitchs1);

    
    Reseau reseauInvalide = new Reseau("ReseauInvalide", "192.168.1.0", "192.168.1.1", 24,
                                       lesMachines2, lesSwitchs2);


    System.out.println("\n--- Ajout d'un réseau valide ---");
    sys.ajouterReseau(reseauValide);

    System.out.println("\n--- Ajout d'un réseau invalide ---");
    sys.ajouterReseau(reseauInvalide);


    System.out.println("\n--- Mise à jour réseau valide avec un réseau invalide ---");
    sys.majReseau("ReseauValide", reseauInvalide);


    System.out.println("\n--- Suppression réseau ---");
    sys.supprimerReseau("ReseauValide");
    System.out.println("Nombre de réseaux dans le système : " + sys.nombreReseaux());
  }


}


