package reseau;

import java.util.List;
import application.Reseau;

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
      // Charger le fichier JSON de test
      List<Reseau> reseaux =
          ReseauFileFactory.creerReseauDepuisFichier("donnees/reseaux_multi_test.json");

      // Afficher tous les réseaux chargés
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

    } catch (Exception e) {
      System.out.println("Erreur lors du test :");
      e.printStackTrace();
    }
  }
}

