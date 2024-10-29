package tn.esprit.spring;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.spring.entities.Skier;
import tn.esprit.spring.entities.Subscription;
import tn.esprit.spring.entities.TypeSubscription;
import tn.esprit.spring.repositories.ISkierRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.services.SkierServicesImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
class SkierServiceImplTestMockito {

    @Mock
    private ISkierRepository skierRepository;

    @InjectMocks
    private SkierServicesImpl skierService;

    private Skier skier;

    @BeforeEach
    void setUp() {
        // Create a valid Subscription object
        Subscription subscription = new Subscription();
        subscription.setStartDate(LocalDate.now());
        subscription.setTypeSub(TypeSubscription.ANNUAL); // Set a valid subscription type

        // Initialize the Skier object with a subscription
        skier = new Skier(1L, "John", "Doe", LocalDate.of(1990, 1, 1), "New York", subscription, null, null);
    }

    @Test
    void testAddSkier() {
        // Mock repository behavior
        when(skierRepository.save(skier)).thenReturn(skier);

        // Call service method
        Skier addedSkier = skierService.addSkier(skier);

        // Assertions
        assertNotNull(addedSkier);
        assertEquals("John", addedSkier.getFirstName());

        // Verify repository interaction
        verify(skierRepository, times(1)).save(skier);
    }

    @Test
    void testRetrieveSkier() {
        // Mock repository behavior
        when(skierRepository.findById(1L)).thenReturn(Optional.of(skier));

        // Call service method
        Skier retrievedSkier = skierService.retrieveSkier(1L);

        // Assertions
        assertNotNull(retrievedSkier);
        assertEquals("John", retrievedSkier.getFirstName());

        // Verify repository interaction
        verify(skierRepository, times(1)).findById(1L);
    }

    @Test
    void testRetrieveAllSkiers() {
        // Mock repository behavior
        List<Skier> skierList = new ArrayList<>();
        skierList.add(skier);
        when(skierRepository.findAll()).thenReturn(skierList);

        // Call service method
        List<Skier> retrievedSkiers = skierService.retrieveAllSkiers();

        // Assertions
        assertNotNull(retrievedSkiers);
        assertEquals(1, retrievedSkiers.size());

        // Verify repository interaction
        verify(skierRepository, times(1)).findAll();
    }

    @Test
    void testRemoveSkier() {
        // Mock repository behavior
        doNothing().when(skierRepository).deleteById(1L);

        // Call service method
        skierService.removeSkier(1L);

        // Verify repository interaction
        verify(skierRepository, times(1)).deleteById(1L);
    }
}