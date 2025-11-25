import java.util.List;

/**
 * Classe Reseau.
 */
class Reseau implements Visitable {
  private String nom;
  private String adresse;
  private String passerelle;
  private Integer masqueSousReseau;
  private List<Machine> lesMachines;
  private List<Switch> lesSwitchs;

  /**
   * Constructeur de Reseau.

   * @param nom Nom du réseau
   * @param adresse Adresse du réseau (format IPv4)
   * @param passerelle Passerelle du Réseau (format IPv4)
   * @param masqueSousReseau Masque de sous réseau
   * @param lesMachines Les machines du réseau
   * @param lesSwitchs Les switchs du réseau
   */
  public Reseau(String nom, String adresse, String passerelle, Integer masqueSousReseau,
                List<Machine> lesMachines, List<Switch> lesSwitchs) {
    this.nom = nom;
    this.adresse = adresse;
    this.passerelle = passerelle;
    this.masqueSousReseau = masqueSousReseau;
    this.lesMachines = lesMachines;
    this.lesSwitchs = lesSwitchs;
  }


  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public String getAdresse() {
    return adresse;
  }

  public void setAdresse(String adresse) {
    this.adresse = adresse;
  }

  public String getPasserelle() {
    return passerelle;
  }

  public void setPasserelle(String passerelle) {
    this.passerelle = passerelle;
  }

  public Integer getMasqueSousReseau() {
    return masqueSousReseau;
  }

  public void setMasqueSousReseau(Integer masqueSousReseau) {
    this.masqueSousReseau = masqueSousReseau;
  }

  public List<Machine> getLesMachines() {
    return lesMachines;
  }

  public void setLesMachines(List<Machine> lesMachines) {
    this.lesMachines = lesMachines;
  }

  public List<Switch> getLesSwitchs() {
    return lesSwitchs;
  }

  public void setLesSwitchs(List<Switch> lesSwitchs) {
    this.lesSwitchs = lesSwitchs;
  }


  @Override
  public void applique(Visiteur unVisiteur) {
    unVisiteur.agitSur(this);
    if (this.lesSwitchs != null) {
      for (Switch sw : this.lesSwitchs) {
        sw.applique(unVisiteur);
      }
    }
    if (this.lesMachines != null) {
      for (Machine mc : this.lesMachines) {
        mc.applique(unVisiteur);
      }
    }
    
  }
}