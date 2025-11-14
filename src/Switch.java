/**
 * Classe Switch.
 */
class Switch {
  private Integer numeroIp;
  private Boolean estManage;

  /**
   * Constructeur de Switch.

   * @param numeroIp Numero IP du switch
   * @param estManage true si le switch est manageable, sinon false
   */
  public Switch(Integer numeroIp, Boolean estManage) {
    this.numeroIp = numeroIp;
    this.estManage = estManage;
  }

  public Integer getNumeroIp() {
    return numeroIp;
  }

  public void setNumeroIp(Integer numeroIp) {
    this.numeroIp = numeroIp;
  }

  public Boolean getEstManage() {
    return estManage;
  }

  public void setEstManage(Boolean estManage) {
    this.estManage = estManage;
  }
}