package models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Fav {
    private Long id;
    private Long userID;
    private Long postID;
}
