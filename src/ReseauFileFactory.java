import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Classe ReseauFileFactory qui permet de créer des réseaux à partir de fichier.
 */
public class ReseauFileFactory {

  // ---------- JSON ----------
  /**
   * Méthode qui permet de créer un réseau standard à partir d'un fichier JSON.

   * @param file Fichier JSON avec lequel on veut créer le réseau
   * @return Retourne la liste de réseau qu'on a crée
   * @throws IOException Exception
   */
  public static List<Reseau> creerReseauDepuisFichier(File file) throws IOException {
    String contenu = Files.readString(file.toPath());
    JSONObject racine = new JSONObject(contenu);

    List<Reseau> liste = new ArrayList<>();
    JSONArray reseauxJson = racine.getJSONArray("reseaux");

    for (int i = 0; i < reseauxJson.length(); i++) {
      JSONObject r = reseauxJson.getJSONObject(i);
        
      String nomReseau = r.getString("nomReseau");
      String adresse = r.getString("adresseReseau");
      int masque = r.getInt("masque");
      String passerelle = r.getString("passerelle");

      // --- Étape 1 : listes temporaires ---
      List<Switch> listeSwitchs = new ArrayList<>();
      List<Machine> listeMachines = new ArrayList<>();

      // Map pour retrouver les Switch par leur IP
      Map<Integer, Switch> switchMap = new HashMap<>();

      // --- Charger les switchs ---
      JSONArray switchesJson = r.getJSONArray("switches");
      for (int j = 0; j < switchesJson.length(); j++) {
        JSONObject s = switchesJson.getJSONObject(j);

        int numeroIp = s.getInt("numeroIp");
        boolean estManage = s.getBoolean("estManage");

        Switch sw = new Switch(numeroIp, estManage);
        listeSwitchs.add(sw);
        switchMap.put(numeroIp, sw);
      }

      // --- Charger les machines ---
      JSONArray machinesJson = r.getJSONArray("machines");
      for (int j = 0; j < machinesJson.length(); j++) {
        JSONObject m = machinesJson.getJSONObject(j);

        String nom = m.getString("nom");
        int numeroIp = m.getInt("numeroIp");
        int switchIp = m.getInt("switchIp");

        Switch parent = switchMap.get(switchIp);
        if (parent == null) {
          throw new IllegalArgumentException(
              "Machine " + nom + " associée à un switch inexistant : " + switchIp
          );
        }

        Machine machine = new Machine(numeroIp, nom, parent);
        listeMachines.add(machine);
      }

      // --- Étape 2 : construire le réseau ---
      Reseau reseau = new Reseau(
          nomReseau,
          adresse,
          passerelle,
          masque,
          listeMachines,
          listeSwitchs
      );

      // --- Étape 3 : Vérification ---
      if (!ReseauVerifier.verifierReseau(reseau)) {
        throw new IllegalArgumentException(
            "Le réseau " + adresse + " est invalide."
        );
      }

      liste.add(reseau);
    }

    return liste;
  }
}
