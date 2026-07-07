package com.accenture.externalapis.demo.client;

import com.accenture.externalapis.demo.config.ExternalServiceProperties;
import com.accenture.externalapis.demo.dto.BookApiResponse;
import com.accenture.externalapis.demo.dto.BookDto;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class BookWebClientImpl implements BookWebClient {

    private final WebClient webClient;

    public BookWebClientImpl(WebClient.Builder builder, ExternalServiceProperties properties) {
        this.webClient = builder.baseUrl(properties.baseUrl()).build();
    }

    @Override
    public Mono<BookDto> getBookAsync(Long id) {
        return webClient.get()
                .uri("/books/{id}", id)
                .retrieve()
                .bodyToMono(BookApiResponse.class)
                .switchIfEmpty(Mono.error(new ClientException("Empty response body for book " + id)))
                .map(this::toDto)
                .onErrorMap(e -> !(e instanceof ClientException),
                        e -> new ClientException("Error fetching book " + id, e));
    }

    @Override
    public Flux<BookDto> getAllBooksAsync() {
        return webClient.get()
                .uri("/books")
                .retrieve()
                .bodyToFlux(BookApiResponse.class)
                .map(this::toDto)
                .onErrorMap(e -> !(e instanceof ClientException),
                        e -> new ClientException("Error fetching all books", e));
    }

    @Override
    public Mono<List<BookDto>> getBooksInParallel(Long id1, Long id2) {
        throw new UnsupportedOperationException("Parallel fetch not implemented yet");
    }

    private BookDto toDto(BookApiResponse response) {
        return new BookDto(response.title(), response.author(), response.genre(), response.price());
    }
}
