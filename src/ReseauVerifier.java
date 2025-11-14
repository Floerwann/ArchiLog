import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Classe ReseauVerifier qui permet de vérifier que le réseau est bon.
 */
public class ReseauVerifier {

  /**
   * Verifier que l'ensemble du réseau est valide.

   * @param reseau Le réseau ue l'on veut vérifier
   * @return Renvoie true si le réseau est correct, sinon false
   */
  public static boolean verifierReseau(Reseau reseau) {
    return adresseValide(reseau.getAdresse())
              && masqueValide(reseau.getMasqueSousReseau())
              && numerosIpValides(reseau)
              && numerosIpUniques(reseau);
  }

  /**
   * Vérifie qu'une adresse est valide (sous format IPv4).

   * @param adresse L'adresse qu'on veut vérifier
   * @return Renvoie true si l'adresse est valide, sinon false
   */
  public static boolean adresseValide(String adresse) {
    if (adresse == null) {
      return false;
    }

    String regex = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9]?[0-9])\\.){3}"
                 + "(25[0-5]|2[0-4][0-9]|[01]?[0-9]?[0-9])$";

    return adresse.matches(regex);
  }

  /**
   * Vérifie que le masque est valide.

   * @param masque Masque.
   * @return Renvoie true si le masque est valide, false sinon.
   */
  public static boolean masqueValide(int masque) {
    return masque == 16 || masque == 24 || masque == 25;
  }

  /**
   * Vérifie que les numeroIp sont cohérents avec le masque.

   * @param reseau Réseau dans lequel on veut vérifier que les numeroIp sont cohérents
   * @return Renvoie true si les numeroIp sont cohérents avec le masque, sinon false
   */
  public static boolean numerosIpValides(Reseau reseau) {
    int masque = reseau.getMasqueSousReseau();

    String baseIp = reseau.getAdresse();
    String[] parts = baseIp.split("\\.");
    int prefix = Integer.parseInt(parts[3]); // Dernier octet réseau

    
    // Calcul du nombre max d’adresses disponibles selon masque
    int tailleBloc = (int) Math.pow(2, 32 - masque);

    List<Switch> sw = reseau.getLesSwitchs();
    List<Machine> ms = reseau.getLesMachines();

    for (Switch s : sw) {
      if (!ipDansPlage(s.getNumeroIp(), prefix, tailleBloc)) {
        return false;
      }
    }
    for (Machine m : ms) {
      if (!ipDansPlage(m.getNumeroIp(), prefix, tailleBloc)) {
        return false;
      }
    }
    return true;
  }


  private static boolean ipDansPlage(int numeroIp, int prefix, int tailleBloc) {
    return numeroIp >= prefix + 1 && numeroIp < prefix + tailleBloc - 1;
  }

  /**
   * Vérifie que tous les numeroIp sont uniques.

   * @param reseau Le réseau dans le lequel on veut vérifier que les numeroIp sont uniques
   * @return Renvoie true si les numeroIp sont uniques, sinon false
   */
  public static boolean numerosIpUniques(Reseau reseau) {
    Set<Integer> vus = new HashSet<>();

    for (Switch s : reseau.getLesSwitchs()) {
      if (!vus.add(s.getNumeroIp())) {
        return false;
      }
    }
    for (Machine m : reseau.getLesMachines()) {
      if (!vus.add(m.getNumeroIp())) {
        return false;
      }
    }

    return true;
  }
}
