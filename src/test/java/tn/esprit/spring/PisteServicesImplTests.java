package tn.esprit.spring;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tn.esprit.spring.entities.Piste;
import tn.esprit.spring.entities.Color;
import tn.esprit.spring.repositories.IPisteRepository;
import tn.esprit.spring.services.IPisteServices;

public class PisteServicesImplTests {
    @Autowired
    private IPisteServices pisteServices; // Use the actual service

    @Test
    void testAddPiste() {
        // Given
        Piste pisteToAdd = new Piste(null, "Test Piste", Color.RED, 200, 30);

        // When
        Piste addedPiste = pisteServices.addPiste(pisteToAdd);

        // Then
        assertThat(addedPiste).isNotNull();
        assertThat(addedPiste.getNamePiste()).isEqualTo("Test Piste");
    }
}
