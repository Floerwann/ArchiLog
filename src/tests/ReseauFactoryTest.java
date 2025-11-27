package tests;

import static org.junit.jupiter.api.Assertions.*;

import application.Machine;
import application.Reseau;
import application.Switch;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reseau.ReseauFactory;


/**
 * Classe de Test pour la classe ReseauFactory.
 */
public class ReseauFactoryTest {
  @Test
  @DisplayName("Création d'un réseau standard simple")
  void testCreationReseauStandard() {
    Reseau reseau = ReseauFactory.creerReseauStandard(
            "MonReseau",
            "192.168.10.0",
            2,
            3
    );

    assertNotNull(reseau);
    assertEquals("MonReseau", reseau.getNom());
    assertEquals("192.168.10.0", reseau.getAdresse());
    assertEquals("192.168.10.1", reseau.getPasserelle());
    assertEquals(24, reseau.getMasqueSousReseau());


    List<Switch> switchs = reseau.getLesSwitchs();
    assertEquals(2, switchs.size());


    List<Machine> machines = reseau.getLesMachines();
    assertEquals(6, machines.size());
  }

  @Test
  @DisplayName("Vérifier que les IP des switchs sont assignées correctement")
  void testSwitchIp() {
    Reseau reseau = ReseauFactory.creerReseauStandard(
            "TestIP",
            "10.0.0.0",
            2,
            1
    );
    List<Switch> s = reseau.getLesSwitchs();

    assertEquals(2, s.get(0).getNumeroIp());

    assertEquals(4, s.get(1).getNumeroIp());
  }

  @Test
  @DisplayName("Vérifier que les machines reçoivent les bonnes IP et le bon switch")
  void testMachinesAssignment() {
    Reseau reseau = ReseauFactory.creerReseauStandard(
            "MachinesTest",
            "172.16.0.0",
            1,
            3
    );

    List<Machine> machines = reseau.getLesMachines();

    assertEquals(3, machines.get(0).getNumeroIp());
    assertEquals(4, machines.get(1).getNumeroIp());
    assertEquals(5, machines.get(2).getNumeroIp());

    assertEquals("Machine_0_0", machines.get(0).getNom());
    assertEquals("Machine_0_1", machines.get(1).getNom());
    assertEquals("Machine_0_2", machines.get(2).getNom());

    Switch sw = reseau.getLesSwitchs().get(0);
    assertSame(sw, machines.get(0).getLeSwitch());
    assertSame(sw, machines.get(1).getLeSwitch());
    assertSame(sw, machines.get(2).getLeSwitch());
  }

  @Test
  @DisplayName("Le compteur de machines fonctionne correctement")
  void testCompteurMachines() {
    int avant = ReseauFactory.getNombreMachinesCreees();

    ReseauFactory.creerReseauStandard("CompteurTest", "10.0.0.0", 1, 4);

    int apres = ReseauFactory.getNombreMachinesCreees();

    assertEquals(avant + 4, apres);
  }

  @Test
  @DisplayName("Création d’un réseau sans switch")
  void testZeroSwitch() {
    Reseau reseau = ReseauFactory.creerReseauStandard(
            "ZeroSwitch",
            "192.168.0.0",
            0,
            10
    );

    assertTrue(reseau.getLesSwitchs().isEmpty());
    assertTrue(reseau.getLesMachines().isEmpty());
  }
}
