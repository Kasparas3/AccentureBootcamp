package lv.bootcamp.shelter;

import lv.bootcamp.shelter.client.NotificationClient;
import lv.bootcamp.shelter.dto.AdoptionRequest;
import lv.bootcamp.shelter.dto.AnimalCreateRequest;
import lv.bootcamp.shelter.dto.AnimalResponse;
import lv.bootcamp.shelter.model.AnimalStatus;
import lv.bootcamp.shelter.model.AnimalType;
import lv.bootcamp.shelter.service.AnimalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

/**
 * Task: Integration test with @SpringBootTest.
 *
 * The full application context loads — use @MockitoBean only for the external
 * NotificationClient. Everything else (service, repository, JPA) is real.
 * @Transactional rolls back after each test.
 */
@SpringBootTest
@Transactional
class AdoptionIntegrationTest {

    @Autowired
    private AnimalService animalService;

    @MockitoBean
    private NotificationClient notificationClient;

    @Test
    void adoptionFlow_shouldPersistStatusAndNotifyExternalSystem() {
        AnimalCreateRequest createRequest = new AnimalCreateRequest("Rex", AnimalType.DOG, "Labrador", 4, "friendly");
        AnimalResponse created = animalService.create(createRequest);
        assertThat(created.status()).isEqualTo(AnimalStatus.AVAILABLE);

        AdoptionRequest adoptionRequest = new AdoptionRequest(created.id(), "John", "john@example.com");
        AnimalResponse adopted = animalService.adopt(adoptionRequest);
        assertThat(adopted.status()).isEqualTo(AnimalStatus.ADOPTED);

        verify(notificationClient).sendAdoptionNotification(created.id(), "Rex", "john@example.com");

        AnimalResponse refetched = animalService.findById(created.id());
        assertThat(refetched.status()).isEqualTo(AnimalStatus.ADOPTED);
    }
}
