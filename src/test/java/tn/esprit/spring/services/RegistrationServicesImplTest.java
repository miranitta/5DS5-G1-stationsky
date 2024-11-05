package tn.esprit.spring.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Registration;
import tn.esprit.spring.entities.Skier;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.IRegistrationRepository;
import tn.esprit.spring.repositories.ISkierRepository;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class RegistrationServicesImplTest {

    @Mock
    private IRegistrationRepository registrationRepository;

    @Mock
    private ISkierRepository skierRepository;

    @Mock
    private ICourseRepository courseRepository;

    @InjectMocks
    private RegistrationServicesImpl registrationServices;

    private Registration registration;
    private Skier skier;
    private Course course;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        registration = new Registration();
        skier = new Skier();
        skier.setNumSkier(1L);
        course = new Course();
        course.setNumCourse(1L);
        course.setTypeCourse(course.getTypeCourse().INDIVIDUAL);
    }

    @Test
    void addRegistrationAndAssignToSkier_ShouldReturnRegistration() {
        // Mock behavior
        when(skierRepository.findById(1L)).thenReturn(java.util.Optional.of(skier));
        when(registrationRepository.save(any(Registration.class))).thenReturn(registration);

        // Call the method to test
        Registration result = registrationServices.addRegistrationAndAssignToSkier(registration, 1L);

        // Verify the result
        assertNotNull(result);
        verify(skierRepository).findById(1L);
        verify(registrationRepository).save(any(Registration.class));
    }

    @Test
    void assignRegistrationToCourse_ShouldReturnRegistration() {
        // Mock behavior
        when(registrationRepository.findById(1L)).thenReturn(java.util.Optional.of(registration));
        when(courseRepository.findById(1L)).thenReturn(java.util.Optional.of(course));
        when(registrationRepository.save(any(Registration.class))).thenReturn(registration);

        // Call the method to test
        Registration result = registrationServices.assignRegistrationToCourse(1L, 1L);

        // Verify the result
        assertNotNull(result);
        verify(registrationRepository).findById(1L);
        verify(courseRepository).findById(1L);
        verify(registrationRepository).save(any(Registration.class));
    }
}
