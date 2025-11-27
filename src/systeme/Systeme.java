package systeme;

import application.Reseau;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import reseau.ReseauFileFactory;
import reseau.ReseauVerifier;
import visiteurs.Visiteur;

/**
 * Classe Systeme.
 */
public class Systeme {


  private List<Reseau> reseaux;
  private List<Observer> observers = new ArrayList<>();
  
  /**
   * Constructeur du Systeme.
   */
  public Systeme() {
    this.reseaux = new ArrayList<>();
  }
  
  /**
   * Méthode d'ajout d'un Observer.

   * @param obs Observer a ajouté
   */
  public void ajouterObserver(Observer obs) {
    observers.add(obs);
  }
  
  /**
   * Méthode notification des Observer.
   */
  public void notifierObservers() {
    for (Observer o : observers) {
      o.mettreAjour();
    }
  }
  
  /**
   * Méthode d'ajout d'un réseau avec vérification.

   * @param reseau Reseau a ajouté
   * @return True si le reseau est bien ajouté, false sinon
   */
  public boolean ajouterReseau(Reseau reseau) {
    if (this.getReseau(reseau.getNom()) != null) {
      System.out.println("Ajout annulé : un réseau avec le nom " + reseau.getNom() 
          + " existe déjà");
      return false;
    }
    
    if (!ReseauVerifier.verifierReseau(reseau)) {
      System.out.println("Réseau invalide, ajout annulé : " + reseau.getNom());
      ReseauVerifier.getErreurs().forEach(System.out::println);
      return false;
    }
    this.reseaux.add(reseau);
    this.notifierObservers();
    return true;
  }
  
  /**
   * Méthode de mise à jour d'un réseau avec vérification.

   * @param nom Nom du reseau a mettre à jour
   * @param rmaj Reseau mise à jour
   * @return True s'il est bien modifié, sinon false
   */
  public boolean majReseau(String nom, Reseau rmaj) {
    if (!ReseauVerifier.verifierReseau(rmaj)) {
      System.out.println("Réseau invalide, mise à jour annulée : " + rmaj.getNom());
      ReseauVerifier.getErreurs().forEach(System.out::println);
      return false;
    }
    for (int i = 0; i < this.reseaux.size(); i++) {
      if (this.reseaux.get(i).getNom().equals(nom)) {
        this.reseaux.set(i, rmaj);
        this.notifierObservers();
        return true;
      }
    }
    System.out.println("Réseau à mettre à jour introuvable : " + nom);
    return false;
  }
  

  /**
   * Méthode de suppression d'un réseau.

   * @param nom Nom du réseau a supprimé
   * @return True si le réseau est bien supprimé, sinon false
   */
  public boolean supprimerReseau(String nom) {
    Reseau r = getReseau(nom);
    if (r == null) {
      System.out.println("Réseau introuvable, suppression annulée : " + nom);
      return false;
    }
    this.reseaux.remove(r);
    this.notifierObservers();
    return true;
  }
  
  /**
   * Méthode get d'un réseau.

   * @param nom Nom du réseau a retourné
   * @return Retourne le réseau s'il existe, sinon null
   */
  public Reseau getReseau(String nom) {
    for (Reseau reseau : reseaux) {
      if (reseau.getNom().equals(nom)) {
        return reseau;
      }
    }
    return null;
  }
  
  public List<Reseau> getLesReseaux() {
    return this.reseaux;
  }
  
  /**
   * Méthode pour savoir le nombre de réseau du Syteme.

   * @return Le nombre de réseau du Systeme
   */
  public int nombreReseaux() {
    return this.reseaux.size();
  }
  
  /**
   * Méthode pour verifier l'état d'un réseau.

   * @param nom Nom du réseau a vérifié
   * @return True si le réseau est valide, sinon false
   */
  public boolean verifierReseau(String nom) {
    Reseau r = this.getReseau(nom);
    if (r == null) {
      return false;
    }
    return ReseauVerifier.verifierReseau(r);
  }
  
  /**
   * Méthode qui permet de charger un réseau depuis un fichier.

   * @param path Chemni du fichier.
   * @throws IOException Exception
   */
  public List<String> chargerDepuisFichier(String path) {
    List<String> erreurs = new ArrayList<>();
    List<Reseau> charges;
    try {
      charges = ReseauFileFactory.creerReseauDepuisFichier(path, erreurs);
    } catch (IOException e) {
      erreurs.add("Erreur lecture fichier : " + e.getMessage());
      return erreurs;
    }

    for (Reseau r : charges) {
      if (getReseau(r.getNom()) != null) {
        erreurs.add("Réseau déjà existant, non chargé : " + r.getNom());
      } else if (!ajouterReseau(r)) {
        erreurs.add("Impossible d'ajouter le réseau : " + r.getNom());
      }
    }

    return erreurs;
  }

  
  /**
   * Méthode d'affichage pour les visiteurs.

   * @param nomReseau Nom du réseau a appliqué au visiteur
   * @param unVisiteur Le visiteur agissant sur le réseau.
   */
  public void afficherAvecVisiteur(String nomReseau, Visiteur unVisiteur) {
    Reseau r = getReseau(nomReseau);
    if (r != null) {
      r.applique(unVisiteur);
    } else {
      System.out.println("Réseau introuvable: " + nomReseau);
    }
  }

}
