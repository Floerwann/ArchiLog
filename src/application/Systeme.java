package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe Systeme.
 */
public class Systeme {
  
  private List<Reseau> reseaux;
  private List<Observer> observers = new ArrayList<>();
  
  /**
   * Constructeur de la classe Systeme.
   */
  public Systeme() {
    this.reseaux = new ArrayList<>();
  }
  
  /**
   * Ajoute un Observer au systeme.

   * @param obs Observer qu'on ajoute
   */
  public void ajouterObserver(Observer obs) {
    observers.add(obs);
  }
  
  /**
   * Notifie l'Observer.
   */
  public void notifierObservers() {
    for (Observer o : observers) {
      o.mettreAJour();
    }
  }
  
  /**
   * Ajoute un réseau au système.

   * @param reseau Réseau qu'on veut ajouter
   */
  public void ajouterReseau(Reseau reseau) {
    this.reseaux.add(reseau);
    this.notifierObservers();
  }
  
  /**
   * Recupérer un réseau à partir de son nom s'il existe.

   * @param nom Nom du réseau qu'on veut recupérer
   * @return Renvoie un réseau s'il existe
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
   * Mettre à jour un réseau.

   * @param nom Nom du réseau à mettre à jour
   * @param rmaj Nouveau réseau afin de mettre à jour
   * @return Renvoie true si le nom du réseau est trouvé et est mis à jour, sinon false
   */
  public boolean majReseau(String nom, Reseau rmaj) {
    for (int i = 0; i < this.reseaux.size(); i++) {
      if (this.reseaux.get(i).getNom().equals(nom)) {
        this.reseaux.set(i, rmaj);
        return true;
      }
    }
    return false;
  }
  
  /**
   * Supprimer un réseau.

   * @param nom Nom du réseau à supprimer
   * @return Renvoie true si le réseau est supprimé, sinon false
   */
  public boolean supprimerReseau(String nom) {
    for (int i = 0; i < this.reseaux.size(); i++) {
      if (this.reseaux.get(i).getNom().equals(nom)) {
        this.reseaux.remove(i);
        this.notifierObservers();
        return true;
      }
    }
    this.notifierObservers();
    return false;
  }
  
  /**
   * Vérifier un réseau.

   * @param nom Nom du réseau à vérifier
   * @return Renvoie true si le réseau est vérifié, sinon false
   */
  public boolean verifierReseau(String nom) {
    Reseau r = this.getReseau(nom);
    if (r == null) {
      return false;
    }
    return ReseauVerifier.verifierReseau(r);
  }
  
  /**
   * Retourne le nombre de réseau du système.
   */
  public int nombreReseaux() {
    return this.reseaux.size();
  }
  
  
  /**
   * Méthode qui permet de charger des réseaux depuis un fichier.

   * @param path Le fichier a chargé
   * @throws IOException Exception
   */
  public void chargerDepuisFichier(String path) throws IOException {
    List<Reseau> charges = ReseauFileFactory.creerReseauDepuisFichier(path);

    for (Reseau r : charges) {
      if (ReseauVerifier.verifierReseau(r)) {
        reseaux.add(r);
      } else {
        System.out.println("Réseau invalide : " + r.getNom());
      }
    }
  }
  
  /**
   * Méthode pour appliquer un visiteur à un réseau.

   * @param nomReseau Nom du Reseau
   * @param unVisiteur Le visiteur
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
