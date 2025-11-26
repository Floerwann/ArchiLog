package reseau;

import application.Machine;
import application.Reseau;
import application.Switch;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Classe ReseauVerifier qui permet de vérifier un réseau
 * et de stocker un rapport détaillé des erreurs trouvées.
 */
public class ReseauVerifier {

  private static final List<String> erreurs = new ArrayList<>();

  /**
   * Retourne la liste des erreurs enregistrées.

   * @return Les erreurs
   */
  public static List<String> getErreurs() {
    return new ArrayList<>(erreurs);
  }

  /**
   * Verifie l'état d'un réseau.

   * @param reseau Reseau a verifié
   * @return True si le réseau est valide, sinon false
   */
  public static boolean verifierReseau(Reseau reseau) {

    erreurs.clear();
    boolean ok = true;

    if (!adresseValide(reseau.getAdresse())) {
      erreurs.add("[ADRESSE INVALIDE] L’adresse du réseau « " 
                  + reseau.getAdresse() + " » n’est pas une IPv4 valide.");
      ok = false;
    }

    if (!masqueValide(reseau.getMasqueSousReseau())) {
      erreurs.add("[MASQUE INVALIDE] Le masque " 
                 + reseau.getMasqueSousReseau() 
                 + " n’est pas autorisé (seuls 16, 24, 25).");
      ok = false;
    }

    if (!numerosIpValides(reseau)) {
      ok = false;
    }

    if (!numerosIpUniques(reseau)) {
      ok = false;
    }

    if (ok) {
      erreurs.add("[AUCUNE ERREUR] Le réseau est valide.");
    }

    return ok;
  }

  /**
   * Verifie qu'une adresse soit valide.

   * @param adresse Adresse a vérifié
   * @return True si l'adresse est valide, sinon false
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
   * Vérifie si le masque est valide.

   * @param masque MAsque a vérifié
   * @return True si le masque est valide, sinon false
   */
  public static boolean masqueValide(int masque) {
    return masque == 16 || masque == 24 || masque == 25;
  }

  /**
   * Vérifie qu'un numero IP est bon.

   * @param reseau Reseau a vérifié
   * @return True si le numéro est valide, sinon false
   */
  public static boolean numerosIpValides(Reseau reseau) {
    boolean ok = true;

    int masque = reseau.getMasqueSousReseau();
    String baseIp = reseau.getAdresse();

    if (!adresseValide(baseIp)) {
      return false;
    }

    String[] parts = baseIp.split("\\.");
    int prefix = Integer.parseInt(parts[3]); 
    int tailleBloc = (int) Math.pow(2, 32 - masque);

    for (Switch s : reseau.getLesSwitchs()) {
      if (!ipDansPlage(s.getNumeroIp(), prefix, tailleBloc)) {
        erreurs.add("[IP SWITCH HORS PLAGE] Le switch " + s.getNumeroIp()
            + " possède un numéro IP hors de la plage autorisée pour " + baseIp + "/" + masque);
        ok = false;
      }
    }

    for (Machine m : reseau.getLesMachines()) {
      if (!ipDansPlage(m.getNumeroIp(), prefix, tailleBloc)) {
        erreurs.add("[IP MACHINE HORS PLAGE] La machine " + m.getNom()
            + " possède un numéro IP " + m.getNumeroIp() 
            + " hors de la plage autorisée pour " + baseIp + "/" + masque);
        ok = false;
      }
    }

    return ok;
  }

  private static boolean ipDansPlage(int numeroIp, int prefix, int tailleBloc) {
    return numeroIp >= prefix + 1 && numeroIp < prefix + tailleBloc - 1;
  }

  /**
   * Vérifie qu'un IP soit unique.

   * @param reseau Reseau a vérifié
   * @return True si les numéros est unique, sinon false
   */
  public static boolean numerosIpUniques(Reseau reseau) {
    boolean ok = true;
    Set<Integer> vus = new HashSet<>();

    for (Switch s : reseau.getLesSwitchs()) {
      if (!vus.add(s.getNumeroIp())) {
        erreurs.add("[IP DUPLIQUÉE] Le switch " + s.getNumeroIp()
                + " a un IP déjà utilisé : " + s.getNumeroIp());
        ok = false;
      }
    }

    for (Machine m : reseau.getLesMachines()) {
      if (!vus.add(m.getNumeroIp())) {
        erreurs.add("[IP DUPLIQUÉE] La machine " + m.getNom()
                + " a un IP déjà utilisé : " + m.getNumeroIp());
        ok = false;
      }
    }

    return ok;
  }
}

