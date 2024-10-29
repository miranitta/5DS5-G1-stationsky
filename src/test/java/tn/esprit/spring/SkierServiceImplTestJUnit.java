package tn.esprit.spring;

import tn.esprit.spring.entities.Skier;
import tn.esprit.spring.entities.TypeSubscription;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.services.ISkierServices;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SkierServiceImplTestJUnit {

    @Autowired
    ISkierServices skierService;

    private Skier skier;

    @BeforeEach
    void setUp() {
        skier = new Skier(null, "John", "Doe", LocalDate.of(1990, 1, 1), "New York", null, null, null);
    }

    @Test
    void testAddSkier() {
        Skier addedSkier = skierService.addSkier(skier);
        assertNotNull(addedSkier);
        assertEquals("John", addedSkier.getFirstName());
    }

    @Test
    void testRetrieveAllSkiers() {
        List<Skier> skiers = skierService.retrieveAllSkiers();
        assertNotNull(skiers);
        assertTrue(skiers.size() >= 0);
    }

    @Test
    void testRetrieveSkier() {
        Skier addedSkier = skierService.addSkier(skier);
        Skier retrievedSkier = skierService.retrieveSkier(addedSkier.getNumSkier());
        assertNotNull(retrievedSkier);
        assertEquals(addedSkier.getFirstName(), retrievedSkier.getFirstName());
    }

    @Test
    void testRemoveSkier() {
        Skier addedSkier = skierService.addSkier(skier);
        skierService.removeSkier(addedSkier.getNumSkier());
        Skier deletedSkier = skierService.retrieveSkier(addedSkier.getNumSkier());
        assertNull(deletedSkier);
    }
}