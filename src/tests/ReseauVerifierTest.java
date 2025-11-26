package tests;

import static org.junit.jupiter.api.Assertions.*;

import application.Reseau;
import application.ReseauFactory;
import application.ReseauVerifier;
import org.junit.jupiter.api.Test;

/**
 * Classe de Test pour la classe ReseauVerifier.
 */
public class ReseauVerifierTest { 
  
  Reseau reseauBase = ReseauFactory.creerReseauStandard(
      "MonReseau",
      "192.168.10.0",
      2,
      3
   );
  

  @Test
  void testAdresseValide() {
    assertTrue(ReseauVerifier.adresseValide("192.168.1.1"));
    assertTrue(ReseauVerifier.adresseValide("10.0.0.255"));

    assertFalse(ReseauVerifier.adresseValide("999.999.999.999"));
    assertFalse(ReseauVerifier.adresseValide("abc.def.ghi.jkl"));
    assertFalse(ReseauVerifier.adresseValide(null));
  }

  @Test
  void testMasqueValide() {
    assertTrue(ReseauVerifier.masqueValide(16));
    assertTrue(ReseauVerifier.masqueValide(24));
    assertTrue(ReseauVerifier.masqueValide(25));

    assertFalse(ReseauVerifier.masqueValide(23));
    assertFalse(ReseauVerifier.masqueValide(30));
  }

  @Test
  void testIpDansLaPlageValide() {
    assertTrue(ReseauVerifier.numerosIpValides(reseauBase));
  }

  @Test
  void testIpHorsPlage() {
    reseauBase.getLesSwitchs().get(0).setNumeroIp(300);

    assertFalse(ReseauVerifier.numerosIpValides(reseauBase));
  }

  @Test
  void testIpUniques() {
    assertTrue(ReseauVerifier.numerosIpUniques(reseauBase));
  }

  @Test
  void testIpDupliquees() {
    reseauBase.getLesMachines().get(0).setNumeroIp(2);
    assertFalse(ReseauVerifier.numerosIpUniques(reseauBase));
  }

  @Test
  void testVerifierReseauGlobalOk() {
    assertTrue(ReseauVerifier.verifierReseau(reseauBase));
  }

  @Test
  void testVerifierReseauGlobalKo() {
    reseauBase.getLesMachines().get(0).setNumeroIp(300);

    assertFalse(ReseauVerifier.verifierReseau(reseauBase));
  }
}
