/**
 * Classe Machine.
 */
class Machine implements Visitable {
  private Integer numeroIp;
  private String nom;
  private Switch leSwitch;

  /**
   * Constructeur de Machine.

   * @param numeroIp NumeroIp  de la machine
   * @param nom Nom de la machine
   * @param leSwitch Le switch associé la machine
   */
  public Machine(Integer numeroIp, String nom, Switch leSwitch) {
    this.numeroIp = numeroIp;
    this.nom = nom;
    this.leSwitch = leSwitch;
  }

  public Integer getNumeroIp() {
    return numeroIp;
  }

  public void setNumeroIp(Integer numeroIp) {
    this.numeroIp = numeroIp;
  }

  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public Switch getLeSwitch() {
    return leSwitch;
  }

  public void setLeSwitch(Switch leSwitch) {
    this.leSwitch = leSwitch;
  }

  @Override
  public void applique(Visiteur unVisiteur) {
    unVisiteur.agitSur(this);
    
  }
}