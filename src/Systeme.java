import java.util.ArrayList;
import java.util.List;

/**
 * Classe Systeme.
 */
public class Systeme {
  
  private List<Reseau> reseaux;
  
  /**
   * Constructeur de la classe Systeme.
   */
  public Systeme() {
    this.reseaux = new ArrayList<>();
  }
  
  /**
   * Ajoute un réseau au système.

   * @param reseau Réseau qu'on veut ajouter
   */
  public void ajouterReseau(Reseau reseau) {
    this.reseaux.add(reseau);
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
  @SuppressWarnings("unused")
  public boolean majReseau(String nom, Reseau rmaj) {
    for (int i = 0; i < this.reseaux.size(); i++) {
      if (this.reseaux.get(i).getNom().equals(nom)) {
        this.reseaux.set(i, rmaj);
      }
      return true;
    }
    return false;
  }
  
  /**
   * Supprimer un réseau.

   * @param nom Nom du réseau à supprimer
   * @return Renvoie true si le réseau est supprimé, sinon false
   */
  public boolean deleteReseau(String nom) {
    for (int i = 0; i < this.reseaux.size(); i++) {
      if (this.reseaux.get(i).getNom().equals(nom)) {
        this.reseaux.remove(i);
        return true;
      }
    }
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

}
