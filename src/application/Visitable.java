package application;

/**
 * Interface Visitable pour les éléments visités.
 */
public interface Visitable {
  void applique(Visiteur unVisiteur);
}
