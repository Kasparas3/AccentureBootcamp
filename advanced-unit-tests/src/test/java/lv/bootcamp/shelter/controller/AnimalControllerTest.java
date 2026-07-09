package lv.bootcamp.shelter.controller;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import lv.bootcamp.shelter.config.SecurityConfig;
import lv.bootcamp.shelter.dto.AnimalCreateRequest;
import lv.bootcamp.shelter.dto.AnimalResponse;
import lv.bootcamp.shelter.model.AnimalStatus;
import lv.bootcamp.shelter.model.AnimalType;
import lv.bootcamp.shelter.service.AnimalNotFoundException;
import lv.bootcamp.shelter.service.AnimalService;

/**
 * Task: REST controller tests with MockMvc and @WebMvcTest.
 *
 * Stub the service with @MockitoBean. Use mockMvc.perform() to make requests
 * and chain .andExpect() calls to verify status, JSON content, and error responses.
 */
@WebMvcTest(AnimalController.class)
@Import(SecurityConfig.class)
class AnimalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AnimalService animalService;

    @Test
    void findAll_shouldReturnListOfAnimals() throws Exception {
        AnimalResponse rex = new AnimalResponse(1L, "Rex", AnimalType.DOG, "Labrador", 4, "friendly", AnimalStatus.AVAILABLE);
        AnimalResponse luna = new AnimalResponse(2L, "Luna", AnimalType.CAT, "Siamese", 2, "shy", AnimalStatus.AVAILABLE);
        when(animalService.findAll()).thenReturn(List.of(rex, luna));

        mockMvc.perform(get("/api/animals"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Rex"))
                .andExpect(jsonPath("$[1].name").value("Luna"));
    }

    @Test
    void findById_shouldReturn404WhenNotFound() throws Exception {
        when(animalService.findById(99L)).thenThrow(new AnimalNotFoundException(99L));

        mockMvc.perform(get("/api/animals/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void create_shouldReturn201WithCreatedAnimal() throws Exception {
        AnimalResponse created = new AnimalResponse(1L, "Rex", AnimalType.DOG, "Labrador", 4, "friendly", AnimalStatus.AVAILABLE);
        when(animalService.create(any())).thenReturn(created);

        AnimalCreateRequest request = new AnimalCreateRequest("Rex", AnimalType.DOG, "Labrador", 4, "friendly");

        mockMvc.perform(post("/api/animals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Rex"))
                .andExpect(jsonPath("$.status").value("AVAILABLE"));
    }

    @Test
    void create_shouldReturn400WhenNameIsBlank() throws Exception {
        AnimalCreateRequest request = new AnimalCreateRequest("", AnimalType.DOG, "Labrador", 4, "friendly");

        mockMvc.perform(post("/api/animals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void create_shouldReturn400WhenTypeIsNull() throws Exception {
        AnimalCreateRequest request = new AnimalCreateRequest("Rex", null, "Labrador", 4, "friendly");
        mockMvc.perform(post("/api/animals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
    }
}
