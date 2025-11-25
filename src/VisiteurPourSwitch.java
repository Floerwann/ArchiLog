
public class VisiteurPourSwitch implements Visiteur{

  @Override
  public void agitSur(Reseau reseau) {
    System.out.println("Réseau : " + reseau.getNom() + " (" + reseau.getAdresse() + ")");
    
  }

  @Override
  public void agitSur(Switch sw) {
    System.out.println("Switch IP : " + sw.getNumeroIp() 
                       + " (Manageable : " + sw.getEstManage() + ")");             
  }

  @Override
  public void agitSur(Machine machine) {
    System.out.println("Machine : " + machine.getNom() + ", Adresse IP : " + machine.getNumeroIp());
    if (machine.getLeSwitch() != null) {
      System.out.println(", Switch IP : " + machine.getLeSwitch().getNumeroIp());
    } else {
      System.out.println();
    }
    
  }

}
