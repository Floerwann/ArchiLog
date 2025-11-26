package visiteurs;

import application.Machine;
import application.Reseau;
import application.Switch;

/**
 * Classe Visiteur qui affiche simplement la liste de machine.
 */
public class VisiteurToutesMachines implements Visiteur {

  @Override
  public void agitSur(Reseau reseau) {
    System.out.println("Machines du réseau : " + reseau.getNom());
    
  }

  @Override
  public void agitSur(Switch sw) {
    
  }

  @Override
  public void agitSur(Machine machine) {
    System.out.print("Machine: " + machine.getNom() + ", IP: " + machine.getNumeroIp());
    if (machine.getLeSwitch() != null) {
      System.out.println(", Switch IP: " + machine.getLeSwitch().getNumeroIp());
    } else {
      System.out.println();
    }
    
  }
}
