package bootcamp.hibernate_practical.dto;

import lombok.Getter;

@Getter
public class CreateBookRequest {
    private String title;
    private String author;
    private String genre;
    private int publicationYear;
}
