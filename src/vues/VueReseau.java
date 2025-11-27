package vues;

import systeme.Observer;
import application.Reseau;
import systeme.Systeme;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import application.Machine;
import application.Switch;

/**
 * Classe de la VueReseau.
 */
public class VueReseau implements Observer {

  private Systeme systeme;
  private Reseau reseau;
  
  /**
   * Constructeur de la VueReseau.

   * @param systeme Systeme
   * @param reseau Reseau dans lequel on va utiliser la Vue
   */
  public VueReseau(Systeme systeme, Reseau reseau) {
    this.systeme = systeme;
    this.reseau = reseau;
    systeme.ajouterObserver(this);
  }
  
  @Override
  public void mettreAjour() {
    afficher();
  }
  
  /**
   * Méthode d'affichage de la Vue.
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
   * Méthode d'interaction avec l'utilisateur.
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
      
      String ligne = sc.nextLine();
      int c;
      try {
        c = Integer.parseInt(ligne);
      } catch (NumberFormatException e) {
        System.out.println("Choix incorrect, recommencez.");
        continue;
      }
      
      this.afficher();
      
      switch (c) {
      
        case 1:
          System.out.print("Nouveau nom : ");
          String nouveauNom = sc.nextLine();
      
          Reseau tmpNom = new Reseau(nouveauNom, reseau.getAdresse(), reseau.getPasserelle(),
                     reseau.getMasqueSousReseau(),
                     reseau.getLesMachines(), reseau.getLesSwitchs());
      
          if (systeme.majReseau(reseau.getNom(), tmpNom)) {
            reseau = tmpNom;
          } else {
            System.out.println("Modification annulée, nom déjà utilisé ou réseau invalide.");
          }
          break;
      
        case 2:
          System.out.print("Nouvelle adresse : ");
          String nouvelleAdr = sc.nextLine();
      
          Reseau tmpAdr = new Reseau(reseau.getNom(), nouvelleAdr, reseau.getPasserelle(),
                     reseau.getMasqueSousReseau(),
                     reseau.getLesMachines(), reseau.getLesSwitchs());
      
          if (systeme.majReseau(reseau.getNom(), tmpAdr)) {
            reseau = tmpAdr;
          } else {
            System.out.println("Modification annulée : adresse invalide.");
          }
          break;
      
        case 3:
          
          System.out.print("Nom machine : ");
          String nomM = sc.nextLine();
      
          System.out.print("IP machine : ");
          int ip = sc.nextInt();
          sc.nextLine();
      
          Switch sw = reseau.getLesSwitchs().get(0); // exemple : prendre le premier switch
          List<Machine> nouvellesMachines = new ArrayList<>(reseau.getLesMachines());
          nouvellesMachines.add(new Machine(ip, nomM, sw));
      
          Reseau tmpMachine = new Reseau(reseau.getNom(), reseau.getAdresse(), 
                     reseau.getPasserelle(),
                     reseau.getMasqueSousReseau(), nouvellesMachines,
                     reseau.getLesSwitchs());
      
          if (systeme.majReseau(reseau.getNom(), tmpMachine)) {
            reseau = tmpMachine;
          } else {
            System.out.println("Ajout annulé : IP invalide ou dupliquée.");
          }
          break;
      
        case 4:
          System.out.print("Nom machine à supprimer : ");
          String nomAsuppr = sc.nextLine();
      
          List<Machine> machinesMod = new ArrayList<>(reseau.getLesMachines());
          boolean estSupprime = machinesMod.removeIf(m -> m.getNom().equals(nomAsuppr));
          
          if (estSupprime) {
            Reseau tmpSuppr = new Reseau(reseau.getNom(), reseau.getAdresse(), 
                reseau.getPasserelle(),
                reseau.getMasqueSousReseau(), machinesMod,
                reseau.getLesSwitchs());
            systeme.majReseau(reseau.getNom(), tmpSuppr);
            reseau = tmpSuppr;
          } else {
            System.out.println("Aucune machine avec ce nom à supprimer");
          }
          
          break;
      
        case 5:
          return;
             
        default:
          System.out.println("Choix incorrect, recommencez.");
          break;
      }
    }
  }
}