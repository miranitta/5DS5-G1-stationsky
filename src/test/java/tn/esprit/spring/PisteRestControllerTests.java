package tn.esprit.spring;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.spring.entities.Piste;
import tn.esprit.spring.entities.Color;
import tn.esprit.spring.services.IPisteServices;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class PisteRestControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IPisteServices pisteServices; // Use the actual service

    @Test
    void testGetAllPistes() throws Exception {
        // Setup test data
        Piste piste1 = new Piste(1L, "Piste 1", Color.RED, 150, 20);
        Piste piste2 = new Piste(2L, "Piste 2", Color.BLUE, 300, 40);
        List<Piste> pistes = Arrays.asList(piste1, piste2);
        // You need to ensure these pistes are saved in the test database before running the test

        // When & Then
        mockMvc.perform(get("/piste/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].namePiste").value("Piste 1"))
                .andExpect(jsonPath("$[1].namePiste").value("Piste 2"));
    }
}
