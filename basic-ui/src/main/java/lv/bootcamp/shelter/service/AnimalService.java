package lv.bootcamp.shelter.service;

import lombok.RequiredArgsConstructor;
import lv.bootcamp.shelter.dto.AnimalCreateRequest;
import lv.bootcamp.shelter.dto.AnimalResponse;
import lv.bootcamp.shelter.form.AnimalForm;
import lv.bootcamp.shelter.model.AdoptionDetails;
import lv.bootcamp.shelter.model.Animal;
import lv.bootcamp.shelter.model.AnimalStatus;
import lv.bootcamp.shelter.model.AnimalType;
import lv.bootcamp.shelter.repository.AnimalRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Business logic for shelter animal management.
 * Delegates data storage to AnimalRepository.
 * Lombok generates the constructor for AnimalRepository injection.
 */
@Service
@RequiredArgsConstructor
public class AnimalService {

    private static final String IMAGES_BASE_PATH = "/images/";
    private static final String ANIMAL_IMAGES_PATH = IMAGES_BASE_PATH + "animals/";
    private static final String FALLBACK_IMAGES_PATH = IMAGES_BASE_PATH + "fallback/";

    private final AnimalRepository animalRepository;

    /**
     * Returns all animals sorted by ID.
     */
    public List<AnimalResponse> findAll() {
        return animalRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Returns a single animal by ID, or empty if not found.
     */
    public Optional<AnimalResponse> findById(Long id) {
        return animalRepository.findById(id).map(this::toResponse);
    }

    /**
     * Returns all adopted animals, sorted by ID. Read-only, so it is a convenient way to
     * exercise ROLE_ADMIN-only authorization (e.g. with a JWT) without any side effects —
     * unlike {@code POST /api/animals}, calling this repeatedly does not create data.
     */
    public List<AnimalResponse> findAdopted() {
        return animalRepository.findAll().stream()
                .filter(animal -> animal.getStatus() == AnimalStatus.ADOPTED)
                .map(this::toResponse)
                .toList();
    }

    /**
     * Creates a new animal with status AVAILABLE and persists it.
     */
        public AnimalResponse create(AnimalCreateRequest request) {
        return createAnimal(
            request.name(),
            request.type(),
            request.breed(),
            request.age(),
            request.description(),
            request.imageUrl()
        );
        }

        /**
         * Creates a new animal from the server-rendered page form.
         */
        public AnimalResponse createFromForm(AnimalForm form) {
        return createAnimal(
            form.name(),
            form.type(),
            form.breed(),
            form.age(),
            form.description(),
            form.imageUrl()
        );
        }

    private AnimalResponse createAnimal(
            String name,
            AnimalType type,
            String breed,
            Integer age,
            String description,
            String imageUrl
        ) {
        long id = animalRepository.nextId();
        Animal animal = new Animal(
                id,
            name,
            type,
            breed,
            age,
            description,
                AnimalStatus.AVAILABLE,
                resolveImageUrl(imageUrl, type),
                null
        );
        return toResponse(animalRepository.save(animal));
    }

    /**
     * Marks an animal as adopted by the given user. Only meaningful for callers with
     * ROLE_USER — enforced by SecurityConfig, not here.
     *
     * @throws IllegalStateException if the animal is already adopted
     */
    public Optional<AnimalResponse> adopt(Long id, String userId) {
        return animalRepository.findById(id).map(animal -> {
            if (animal.getStatus() == AnimalStatus.ADOPTED) {
                throw new IllegalStateException("Animal %d is already adopted".formatted(id));
            }
            animal.setStatus(AnimalStatus.ADOPTED);
            animal.setAdoptionDetails(new AdoptionDetails(userId, LocalDate.now()));
            return toResponse(animalRepository.save(animal));
        });
    }

    private String resolveImageUrl(String imageUrl, AnimalType type) {
        if (imageUrl == null || imageUrl.isBlank()) {
            return fallbackImageFor(type);
        }

        String trimmed = imageUrl.trim();
        if (trimmed.startsWith("http://") || trimmed.startsWith("https://") || trimmed.startsWith("data:")) {
            return trimmed;
        }

        if (trimmed.startsWith("/")) {
            return trimmed;
        }

        return ANIMAL_IMAGES_PATH + sanitizeFilename(trimmed);
    }

    private String sanitizeFilename(String value) {
        String normalized = value.replace('\\', '/');
        int lastSlash = normalized.lastIndexOf('/');
        if (lastSlash >= 0) {
            return normalized.substring(lastSlash + 1);
        }
        return normalized;
    }

    private String fallbackImageFor(AnimalType type) {
        return switch (type) {
            case CAT -> FALLBACK_IMAGES_PATH + "fallback-cat.jpg";
            case DOG -> FALLBACK_IMAGES_PATH + "fallback-dog.jpg";
            case OTHER -> FALLBACK_IMAGES_PATH + "fallback-other.jpg";
        };
    }

    private AnimalResponse toResponse(Animal animal) {
        return new AnimalResponse(
                animal.getId(),
                animal.getName(),
                animal.getType(),
                animal.getBreed(),
                animal.getAge(),
                animal.getDescription(),
                animal.getStatus(),
                animal.getImageUrl(),
                adoptionNoteFor(animal)
        );
    }

    /**
     * Builds the "adopted by {userId} on {date}" note — ADMIN callers only.
     */
    private String adoptionNoteFor(Animal animal) {
        AdoptionDetails details = animal.getAdoptionDetails();
        if (details == null || !isCurrentUserAdmin()) {
            return null;
        }
        return "adopted by %s on %s".formatted(details.userId(), details.adoptionDate());
    }

    private boolean isCurrentUserAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }
        return authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
    }
}
