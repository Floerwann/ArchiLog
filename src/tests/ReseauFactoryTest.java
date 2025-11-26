package tests;

import static org.junit.jupiter.api.Assertions.*;

import application.Machine;
import application.Reseau;
import application.ReseauFactory;
import application.Switch;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

    // Switchs
    List<Switch> switchs = reseau.getLesSwitchs();
    assertEquals(2, switchs.size());

    // Machines
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

    // Switch 0 → IP = 2
    assertEquals(2, s.get(0).getNumeroIp());

    // Switch 1 → IP = 4 (car une machine a pris l'IP 3)
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

    // IP attendues : 3, 4, 5
    assertEquals(3, machines.get(0).getNumeroIp());
    assertEquals(4, machines.get(1).getNumeroIp());
    assertEquals(5, machines.get(2).getNumeroIp());

    // Vérifier le nom des machines
    assertEquals("Machine_0_0", machines.get(0).getNom());
    assertEquals("Machine_0_1", machines.get(1).getNom());
    assertEquals("Machine_0_2", machines.get(2).getNom());

    // Vérifier qu'elles pointent vers le bon switch
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
    assertTrue(reseau.getLesMachines().isEmpty()); // car pas de switch ⇒ pas de machines créées
  }
}
