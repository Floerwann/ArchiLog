package visiteurs;

/**
 * Interface Visitable pour les éléments visités.
 */
public interface Visitable {
  /**
   * Méthode pour appliqué un Visiteur.

   * @param unVisiteur Le visiteur a appliqué
   */
  void applique(Visiteur unVisiteur);
}
