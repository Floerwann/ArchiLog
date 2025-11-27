package reseau;

import application.Reseau;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe qui permet de vérifier que la classe ReseauFileFactory fonctionne bien.
 */
public class TestApp {
  
  /**
   * Main de la classe TestApp.

   * @param args Arguments
   */
  public static void main(String[] args) {
    try {
      List<String> erreurs = new ArrayList<>();
      List<Reseau> reseaux =
          ReseauFileFactory.creerReseauDepuisFichier("donnees/reseaux_multi_test.json", erreurs);


      System.out.println("=== Réseaux chargés ===");
      for (Reseau r : reseaux) {
        System.out.println("------------------------");
        System.out.println("Nom : " + r.getNom());
        System.out.println("Adresse réseau : " + r.getAdresse());
        System.out.println("Masque : " + r.getMasqueSousReseau());
        System.out.println("Passerelle : " + r.getPasserelle());
        System.out.println("Nombre de switchs : " + r.getLesSwitchs().size());
        System.out.println("Nombre de machines : " + r.getLesMachines().size());
        
        boolean estValide = ReseauVerifier.verifierReseau(r);
        
        if (estValide) {
          System.out.println("Reseau valide");
        } else {
          System.out.println("Reseau non valide");
        }
      }
      for (String err : erreurs) {
        System.out.println(err);
      }

    } catch (Exception e) {
      System.out.println("Erreur lors du test :");
      e.printStackTrace();
    }
  }
}

