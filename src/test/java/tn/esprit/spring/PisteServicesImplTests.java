package tn.esprit.spring;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.Piste;
import tn.esprit.spring.entities.Color;
import tn.esprit.spring.repositories.IPisteRepository;
import tn.esprit.spring.services.PisteServicesImpl;
public class PisteServicesImplTests {
    @Mock
    private IPisteRepository pisteRepository;

    @InjectMocks
    private PisteServicesImpl pisteServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddPiste() {
        // Given
        Piste pisteToAdd = new Piste(null, "Test Piste", Color.RED, 200, 30);
        when(pisteRepository.save(pisteToAdd)).thenReturn(pisteToAdd);

        // When
        Piste addedPiste = pisteServices.addPiste(pisteToAdd);

        // Then
        assertThat(addedPiste).isNotNull();
        assertThat(addedPiste.getNamePiste()).isEqualTo("Test Piste");
        verify(pisteRepository, times(1)).save(pisteToAdd);
    }
}
