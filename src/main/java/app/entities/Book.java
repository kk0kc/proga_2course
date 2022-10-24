package app.entities;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Book {
    int id;
    String name;
    String author;
}
