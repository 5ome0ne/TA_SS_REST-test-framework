package entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Book {

    private Integer bookId;
    private String bookName;
    private String bookLanguage;
    private String bookDescription;
    private Additional additional;
    private Integer publicationYear;

    @Data
    @Builder
    public static class Additional {

        private Integer pageCount;
        private Size size;
    }

    @Data
    @Builder
    public static class Size {

        private Double height;
        private Double width;
        private Double length;
    }
}
