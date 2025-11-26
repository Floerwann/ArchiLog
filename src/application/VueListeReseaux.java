package application;

import java.util.Scanner;

/**
 * Classe de la Vue de la liste des réseaux.
 */
public class VueListeReseaux implements Observer {

  private Systeme systeme;

  /**
   * Constructeur de la Vue.

   * @param systeme Systeme
   */
  public VueListeReseaux(Systeme systeme) {
    this.systeme = systeme;
    systeme.ajouterObserver(this);
  }

  @Override
  public void mettreAJour() {
    afficher();
  }

  /**
   * Méthode d'affichage de la Vue.
   */
  public void afficher() {
    System.out.println("\n===== LISTE DES RÉSEAUX =====");

    if (systeme.getLesReseaux().isEmpty()) {
      System.out.println("Aucun réseau pour le moment.");
    } else {
      for (Reseau r : systeme.getLesReseaux()) {
        System.out.println("- " + r.getNom() + " (" + r.getAdresse() + ")");
      }
    }

    System.out.println("==============================\n");
  }

  /**
   * Méthode pour les interaction avec l'utilisateur.
   */
  public void interaction() {
    Scanner sc = new Scanner(System.in);

    while (true) {
      System.out.println("1. Ajouter réseau standard");
      System.out.println("2. Supprimer un réseau");
      System.out.println("3. Ouvrir un réseau");
      System.out.println("4. Quitter");
      System.out.print("Choix : ");

      int c = sc.nextInt();
      sc.nextLine();

      switch (c) {

        case 1:
          System.out.print("Nom réseau : ");
          String nom = sc.nextLine();
          System.out.print("Adresse réseau : ");
          String adr = sc.nextLine();
          System.out.print("Nb switchs : ");
          int nbS = sc.nextInt();
          System.out.print("Nb machines/switch : ");
          int nbM = sc.nextInt();
          sc.nextLine();

          Reseau r = ReseauFactory.creerReseauStandard(nom, adr, nbS, nbM);
          systeme.ajouterReseau(r);
          break;

        case 2:
          System.out.print("Nom réseau à supprimer : ");
          systeme.supprimerReseau(sc.nextLine());
          break;

        case 3:
          System.out.print("Nom du réseau à ouvrir : ");
          Reseau res = systeme.getReseau(sc.nextLine());

          if (res != null) {
            VueReseau vueR = new VueReseau(systeme, res);
            vueR.interaction();
          } else {
            System.out.println("Réseau introuvable.");
          }
          break;

        case 4:
          return;
      }
    }
  }
}


