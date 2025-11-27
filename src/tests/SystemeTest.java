package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import application.Machine;
import application.Reseau;
import application.Switch;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import systeme.Systeme;

/**
 * Classe de Test de la classe Systeme.
 */
public class SystemeTest {

  private Systeme sys;

  @BeforeEach
  void init() {
    sys = new Systeme();
  }

  private Reseau creerReseauValide(String nom) {
    List<Switch> sw = new ArrayList<>();
    sw.add(new Switch(2, true));
    sw.add(new Switch(3, true));

    List<Machine> machines = new ArrayList<>();
    machines.add(new Machine(4, "PC1", sw.get(0)));
    machines.add(new Machine(5, "PC2", sw.get(1)));

    return new Reseau(nom, "192.168.1.0", "192.168.1.1", 24, machines, sw);
  }

  private Reseau reseauAdresseInvalide() {
    return new Reseau("RInvalideAdresse", "999.999.1.1", "1.1.1.1", 24,
            new ArrayList<>(), new ArrayList<>());
  }

  private Reseau reseauMasqueInvalide() {
    return new Reseau("RinvalideMasque", "192.168.1.0", "192.168.1.1", 13,
            new ArrayList<>(), new ArrayList<>());
  }

  private Reseau reseauIpDupliquee() {
    List<Switch> sw = new ArrayList<>();
    sw.add(new Switch(2, true));
    sw.add(new Switch(2, true)); 

    return new Reseau("RIpDupliquee", "192.168.1.0", "192.168.1.1", 24,
            new ArrayList<>(), sw);
  }


  @Test
  void testAjoutReseauValide() {
    Reseau r = creerReseauValide("R1");
    assertTrue(sys.ajouterReseau(r));
    assertEquals(1, sys.nombreReseaux());
  }

  @Test
  void testAjoutReseauAdresseInvalide() {
    Reseau r = reseauAdresseInvalide();
    assertFalse(sys.ajouterReseau(r));
    assertEquals(0, sys.nombreReseaux());
  }

  @Test
  void testAjoutMasqueInvalide() {
    Reseau r = reseauMasqueInvalide();
    assertFalse(sys.ajouterReseau(r));
  }

  @Test
  void testAjoutIpDupliquee() {
    Reseau r = reseauIpDupliquee();
    assertFalse(sys.ajouterReseau(r));
  }

  @Test
  void testAjoutReseauDejaExistant() {
    Reseau r1 = creerReseauValide("R1");
    Reseau r2 = creerReseauValide("R1");

    assertTrue(sys.ajouterReseau(r1));
    assertFalse(sys.ajouterReseau(r2));
  }



  @Test
  void testMajReseauValide() {
    sys.ajouterReseau(creerReseauValide("R1"));

    Reseau maj = creerReseauValide("R1");
    maj.setAdresse("192.168.5.0");

    assertTrue(sys.majReseau("R1", maj));
    assertEquals("192.168.5.0", sys.getReseau("R1").getAdresse());
  }

  @Test
  void testMajReseauInvalide() {
    sys.ajouterReseau(creerReseauValide("R1"));

    Reseau maj = reseauIpDupliquee();
    maj.setNom("R1");

    assertFalse(sys.majReseau("R1", maj));
  }

  @Test
  void testMajReseauInexistant() {
    Reseau maj = creerReseauValide("R2");

    assertFalse(sys.majReseau("Inconnu", maj));
  }



  @Test
  void testSuppressionReseauExistant() {
    sys.ajouterReseau(creerReseauValide("R1"));
    assertTrue(sys.supprimerReseau("R1"));
    assertEquals(0, sys.nombreReseaux());
  }

  @Test
  void testSuppressionReseauInexistant() {
    assertFalse(sys.supprimerReseau("ReseauInconnu"));
  }



  @Test
  void testGetReseauExistant() {
    sys.ajouterReseau(creerReseauValide("R1"));
    assertNotNull(sys.getReseau("R1"));
  }

  @Test
  void testGetReseauInexistant() {
    assertNull(sys.getReseau("ReseauInconnu"));
  }

  @Test
  void testVerifierReseauValide() {
    sys.ajouterReseau(creerReseauValide("R1"));
    assertTrue(sys.verifierReseau("R1"));
  }

  @Test
  void testVerifierReseauInexistant() {
    assertFalse(sys.verifierReseau("ReseauInconnnu"));
  }

  @Test
  void testNombreReseaux() {
    sys.ajouterReseau(creerReseauValide("R1"));
    sys.ajouterReseau(creerReseauValide("R2"));
    assertEquals(2, sys.nombreReseaux());
  }
}

