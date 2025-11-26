package application;

/**
 * Classe qui permet de vérifier le fonctionnement des Vues.
 */
public class TestVues {
  /**
   * Main de la classe.

   * @param args Arguments
   */
  public static void main(String[] args) {
    Systeme systeme = new Systeme();
    VueListeReseaux vue = new VueListeReseaux(systeme);

    vue.interaction();
  }
}

