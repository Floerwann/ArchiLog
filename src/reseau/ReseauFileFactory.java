package reseau;

import application.Machine;
import application.Reseau;
import application.Switch;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Classe ReseauFileFactory qui permet de créer des réseaux à partir d'un fichier JSON.
 * Les erreurs de création sont collectées pour un rapport complet.
 */
public class ReseauFileFactory {

  /**
   * Charge un ou plusieurs réseaux depuis un fichier JSON.

   * @param path Chemin du fichier JSON
   * @param erreurs Liste où seront ajoutées les erreurs rencontrées
   * @return Liste des réseaux valides
   * @throws IOException si le fichier est introuvable ou illisible
   */
  public static List<Reseau> creerReseauDepuisFichier(String path, List<String> erreurs) 
         throws IOException {
    String contenu = Files.readString(Paths.get(path));
    JSONObject racine = new JSONObject(contenu);

    List<Reseau> listeReseaux = new ArrayList<>();
    JSONArray reseauxJson = racine.getJSONArray("reseaux");

    for (int i = 0; i < reseauxJson.length(); i++) {
      JSONObject r = reseauxJson.getJSONObject(i);

      try {
        Reseau reseau = creerReseauUnique(r);
        if (!ReseauVerifier.verifierReseau(reseau)) {
          erreurs.add("Réseau invalide : " + reseau.getNom());
          erreurs.addAll(ReseauVerifier.getErreurs());
        } else {
          listeReseaux.add(reseau);
        }
      } catch (IllegalArgumentException ex) {
        erreurs.add("Erreur lors de la création du réseau : " + ex.getMessage());
      }
    }

    return listeReseaux;
  }

  /**
   * Méthode de création d'un réseau.

   * @param r Reseau du fichier
   * @return Retourne le réseau crée
   */
  private static Reseau creerReseauUnique(JSONObject r) {
    String nomReseau = r.getString("nomReseau");
    String adresse = r.getString("adresseReseau");
    int masque = r.getInt("masque");
    String passerelle = r.getString("passerelle");

    List<Switch> listeSwitchs = new ArrayList<>();
    List<Machine> listeMachines = new ArrayList<>();

    Map<Integer, Switch> mapSwitch = creerSwitches(r.getJSONArray("switches"), listeSwitchs);
    creerMachines(r.getJSONArray("machines"), listeMachines, mapSwitch);

    return new Reseau(nomReseau, adresse, passerelle, masque, listeMachines, listeSwitchs);
  }

  
  /**
   * Méthode de création des switchs.

   * @param switchesJson Liste des switchs du fichier
   * @param listeSwitchs Liste de switchs
   * @return Retourne une map des switchs
   */
  private static Map<Integer, Switch> creerSwitches(JSONArray switchesJson, 
                                                    List<Switch> listeSwitchs) {
    Map<Integer, Switch> map = new HashMap<>();

    for (int i = 0; i < switchesJson.length(); i++) {
      JSONObject s = switchesJson.getJSONObject(i);

      int numeroIp = s.getInt("numeroIp");
      boolean estManage = s.getBoolean("estManage");

      if (map.containsKey(numeroIp)) {
        throw new IllegalArgumentException("Deux switches ont la même adresse IP : " + numeroIp);
      }

      Switch sw = new Switch(numeroIp, estManage);
      listeSwitchs.add(sw);
      map.put(numeroIp, sw);
    }

    return map;
  }

  /**
   * Méthode création des machines.

   * @param machinesJson Les machines du fichier
   * @param listeMachines Liste de machine
   * @param mapSwitch Map des switch
   */
  private static void creerMachines(JSONArray machinesJson, List<Machine> listeMachines,
                                    Map<Integer, Switch> mapSwitch) {
    Set<Integer> ipsMachines = new HashSet<>();

    for (int i = 0; i < machinesJson.length(); i++) {
      JSONObject m = machinesJson.getJSONObject(i);

      String nom = m.getString("nom");
      int numeroIp = m.getInt("numeroIp");
      int switchIp = m.getInt("switchIp");

      if (ipsMachines.contains(numeroIp)) {
        throw new IllegalArgumentException("Deux machines ont la même IP : " + numeroIp);
      }

      Switch parent = mapSwitch.get(switchIp);
      if (parent == null) {
        throw new IllegalArgumentException("Machine '" + nom 
                                             + "' associée à un switch inexistant : " 
                                            + switchIp);
      }

      Machine machine = new Machine(numeroIp, nom, parent);
      listeMachines.add(machine);
      ipsMachines.add(numeroIp);
    }
  }
}
