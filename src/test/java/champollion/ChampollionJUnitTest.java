package champollion;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class ChampollionJUnitTest {
	Enseignant untel;
	UE uml, java;
	Salle salle
		
	@BeforeEach
	public void setUp() {
		untel = new Enseignant("untel", "untel@gmail.com");
		uml = new UE("UML");
		java = new UE("Programmation en java");
		salle = new Salle("Salle 01", 10);
	}
	

	@Test
	public void testNouvelEnseignantSansService() {
		assertEquals(0, untel.heuresPrevues(),
                        "Un nouvel enseignant doit avoir 0 heures prévues");
	}
	
	@Test
	public void testAjouteHeures() {
                // 10h TD pour UML
		untel.ajouteEnseignement(uml, 0, 10, 0);

		assertEquals(10, untel.heuresPrevuesPourUE(uml),
                        "L'enseignant doit maintenant avoir 10 heures prévues pour l'UE 'uml'");

		// 20h TD pour UML
        untel.ajouteEnseignement(uml, 0, 20, 0);
                
		assertEquals(10 + 20, untel.heuresPrevuesPourUE(uml),
                         "L'enseignant doit maintenant avoir 30 heures prévues pour l'UE 'uml'");		
		
	}
	public void testAjouteHeuresNegatives() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			untel.ajouteEnseignement(uml, -20, -1, -5);
		});
		assertEquals("Le volume horaire ne peut pas être négatif, impossible.", exception.getMessage());
	}

	public void testHeuresPrevuesAvecCM_TD_TP() {
		untel.ajouteEnseignement(uml, 23, 10, 40);
		int heuresPrevues = (int) Math.round(23* 1.5 + 10 + 40 * 0.75);
		assertEquals(heuresPrevues, untel.heuresPrevues(),
				"Les calculs de prévisions d'heure sont impossibles.");
	}

	public void testAjouteIntervention() {
		untel.ajouteEnseignement(uml, 15, 10, 30);
		Intervention intervention = new Intervention(uml, TypeIntervention.CM, new Date(), 5, false, salle);
		untel.ajouteIntervention(intervention);
		assertEquals(10, untel.resteAPlanifier(uml, TypeIntervention.CM),
				"Il devrait rester 10 heures à planifier pour l'UE uml en CM.");
	}

	public void testAjouteInterventionAvecException() {
		untel.ajouteEnseignement(uml, 10, 10, 4);
		Intervention intervention = new Intervention(uml, TypeIntervention.CM, new Date(), 10, false, salle);
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			untel.ajouteIntervention(intervention);
		});
		assertEquals("pas possible, intervention trop longue pour cet enseignant", exception.getMessage());
	}

	public void testResteAPlanifier() {
		untel.ajouteEnseignement(uml, 16, 8, 6);
		Intervention intervention = new Intervention(uml, TypeIntervention.CM, new Date(), 5, false, salle);
		untel.ajouteIntervention(intervention);
		assertEquals(2, untel.resteAPlanifier(uml, TypeIntervention.CM),
				"Il devrait rester 2 heures à planifier pour l'UE uml en CM.");
	}

	public void testResteAPlanifierPourTousTypes() {
		untel.ajouteEnseignement(uml, 10, 14, 12);
		untel.ajouteIntervention(new Intervention(uml, TypeIntervention.CM, new Date(), 5, false, salle));
		untel.ajouteIntervention(new Intervention(uml, TypeIntervention.TD, new Date(), 5, false, salle));
		untel.ajouteIntervention(new Intervention(uml, TypeIntervention.TP, new Date(), 5, false, salle));
		assertEquals(2, untel.resteAPlanifier(uml, TypeIntervention.CM),
				"Il devrait rester 2 heures à planifier pour l'UE uml en CM.");
		assertEquals(6, untel.resteAPlanifier(uml, TypeIntervention.TD),
				"Il devrait rester 6 heures à planifier pour l'UE uml en TD.");
		assertEquals(8, untel.resteAPlanifier(uml, TypeIntervention.TP),
				"Il devrait rester 8 heures à planifier pour l'UE uml en TP.");
	}

	public void testResteAPlanifierSansService() {
		assertEquals(0, untel.resteAPlanifier(uml, TypeIntervention.CM),
				"Il ne reste pas d'heure à planifier pour une UE sans service prévu.");
	}

	@Test
	public void testEnSousService() {
		untel.ajouteEnseignement(uml, 12, 20, 20);
		assertTrue(untel.enSousService(), "L'enseignant doit être en sous-service avec moins de 192 heures.");
		untel.ajouteEnseignement(uml, 10, 40, 30);
		assertFalse(untel.enSousService(), "L'enseignant ne doit pas être en sous-service avec 192 heures ou plus.");
	}

	@Test
	public void testGetIntituleUE() {
		assertEquals("UML", uml.getIntitule(), "L'intitulé de l'UE doit être UML.");
	}



}





