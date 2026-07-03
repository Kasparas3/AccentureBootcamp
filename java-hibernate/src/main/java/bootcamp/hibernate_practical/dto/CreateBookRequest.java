package bootcamp.hibernate_practical.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class CreateBookRequest {
    @NotBlank (message = "Title is required")
    private String title;
    @NotBlank (message = "Author is required")
    private String author;
    @NotBlank (message = "Genre is required")
    private String genre;
    @Positive (message = "Publication year must be positive ")
    private int publicationYear;
}
