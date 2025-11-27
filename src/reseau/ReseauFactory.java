package reseau;

import application.Machine;
import application.Reseau;
import application.Switch;
import java.util.ArrayList;
import java.util.List;


/**
 * Classe ReseauFactory qui a pour but de créer un Reseau.
 */
public class ReseauFactory {
  private static int nombreMachinesCreees = 0;

  /**
   * Méthode qui permet de créer un réseau standard.

   * @param nom Nom du réseau
   * @param adresseReseau Adresse IP du réseau
   * @param nbSwitchs Nombre de switchs voulus dans le réseau
   * @param nbMachinesParSwitch Nombre de machines voulu dans le réseau
   * @return Renvoie le réseau créé
   */
  public static Reseau creerReseauStandard(String nom, String adresseReseau, 
                                           int nbSwitchs, int nbMachinesParSwitch) {

    String[] parts = adresseReseau.split("\\.");
    String prefix = parts[0] + "." + parts[1] + "." + parts[2] + ".";
    String passerelle = prefix + "1";

    List<Switch> switchs = new ArrayList<>();
    List<Machine> machines = new ArrayList<>();

    int ipCourante = 2;


    for (int i = 0; i < nbSwitchs; i++) {
      Switch s = new Switch(ipCourante, true);
      switchs.add(s);
      ipCourante++;

      
      for (int j = 0; j < nbMachinesParSwitch; j++) {
        Machine m = new Machine(ipCourante, "Machine_" + i + "_" + j, s);
        machines.add(m);
        ipCourante++;
        nombreMachinesCreees++;
      }
    }


    Reseau reseau = new Reseau(
            nom,
            adresseReseau,
            passerelle,
            24,
            machines,
            switchs
    );

    return reseau;
  }

  public static int getNombreMachinesCreees() {
    return nombreMachinesCreees;
  }
}