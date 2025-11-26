package vues;

import application.Machine;
import application.Observer;
import application.Reseau;
import application.Switch;
import application.Systeme;
import java.util.Scanner;

/**
 * Vue Reseau.
 */
public class VueReseau implements Observer {

  private Systeme systeme;
  private Reseau reseau;

  /**
   * Constructeur.

   * @param systeme Systeme
   * @param reseau Reseau
   */
  public VueReseau(Systeme systeme, Reseau reseau) {
    this.systeme = systeme;
    this.reseau = reseau;
    systeme.ajouterObserver(this);
  }

  @Override
  public void mettreAJour() {
    afficher();
  }

  /**
   * Méthode d'affichage.
   */
  public void afficher() {
    System.out.println("\n===== RÉSEAU : " + reseau.getNom() + " =====");
    System.out.println("Adresse : " + reseau.getAdresse());
    System.out.println("Masque : " + reseau.getMasqueSousReseau());
    System.out.println("Passerelle : " + reseau.getPasserelle());
    System.out.println("\n--- Machines ---");

    if (reseau.getLesMachines().isEmpty()) {
      System.out.println("(Aucune machine)");
    } else {
      for (Machine m : reseau.getLesMachines()) {
        System.out.println("- " + m.getNom() + " | IP: " + m.getNumeroIp());
      }
    }

    System.out.println("==============================\n");
  }
  
  /**
   * Méthode qui permet les interaction avec l'utilisateur.
   */
  public void interaction() {
    Scanner sc = new Scanner(System.in);

    while (true) {
      System.out.println("1. Modifier nom");
      System.out.println("2. Modifier adresse");
      System.out.println("3. Ajouter machine");
      System.out.println("4. Supprimer machine");
      System.out.println("5. Quitter cette vue");
      System.out.print("Choix : ");

      int c = sc.nextInt();
      sc.nextLine();

      switch (c) {

        case 1:
          System.out.print("Nouveau nom : ");
          reseau.setNom(sc.nextLine());
          systeme.notifierObservers();
          break;

        case 2:
          System.out.print("Nouvelle adresse : ");
          reseau.setAdresse(sc.nextLine());
          systeme.notifierObservers();
          break;

        case 3:
          System.out.print("Nom machine : ");
          String nomM = sc.nextLine();

          System.out.print("IP machine : ");
          int ip = sc.nextInt();
          sc.nextLine();

          Switch sw = reseau.getLesSwitchs().get(0);
          reseau.getLesMachines().add(new Machine(ip, nomM, sw));

          systeme.notifierObservers();
          break;

        case 4:
          System.out.print("Nom machine à supprimer : ");
          reseau.getLesMachines().removeIf(m -> m.getNom().equals(sc.nextLine()));
          systeme.notifierObservers();
          break;

        case 5:
          return;
      }
    }
  }
}


