package bootcamp.hibernate_practical.dto;

import lombok.Getter;

@Getter
public class UpdateBookRequest {
    private String title;
    private String author;
    private String genre;
    private int publicationYear;
    public boolean available;
}
