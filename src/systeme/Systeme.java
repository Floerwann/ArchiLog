package systeme;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import application.Reseau;
import reseau.ReseauFileFactory;
import reseau.ReseauVerifier;
import visiteurs.Visiteur;

public class Systeme {


  private List<Reseau> reseaux;
  private List<Observer> observers = new ArrayList<>();
  
  public Systeme() {
    this.reseaux = new ArrayList<>();
  }
  
  public void ajouterObserver(Observer obs) {
    observers.add(obs);
  }
  
  public void notifierObservers() {
    for (Observer o : observers) {
      o.mettreAjour();
    }
  }
  
  // --- Ajout d'un réseau avec vérification ---
  public boolean ajouterReseau(Reseau reseau) {
    if(this.getReseau(reseau.getNom()) != null) {
      System.out.println("Ajout annulé : un réseau avec le nom " + reseau.getNom() + " existe déjà");
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
  
  // --- Mise à jour d'un réseau avec vérification ---
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
  
  // --- Suppression d'un réseau ---
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
  
  public int nombreReseaux() {
    return this.reseaux.size();
  }
  
  public boolean verifierReseau(String nom) {
    Reseau r = this.getReseau(nom);
    if (r == null) {
      return false;
    }
    return ReseauVerifier.verifierReseau(r);
  }
  
  public void chargerDepuisFichier(String path) throws IOException {
    List<Reseau> charges = ReseauFileFactory.creerReseauDepuisFichier(path);
    for (Reseau r : charges) {
      if (!ReseauVerifier.verifierReseau(r)) {
        System.out.println("Réseau invalide, non chargé : " + r.getNom());
        ReseauVerifier.getErreurs().forEach(System.out::println);
      } else {
        this.reseaux.add(r);
      }
    }
    this.notifierObservers();
  }
  
  public void afficherAvecVisiteur(String nomReseau, Visiteur unVisiteur) {
    Reseau r = getReseau(nomReseau);
    if (r != null) {
      r.applique(unVisiteur);
    } else {
      System.out.println("Réseau introuvable: " + nomReseau);
    }
  }

}
