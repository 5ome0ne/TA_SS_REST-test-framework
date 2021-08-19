package constant;

import entity.Genre;
import org.testng.annotations.DataProvider;
import service.GenreService;

public class Provider {

    @DataProvider(name = "letter-combination-number")
    public static Object[][] letterCombination() {
        return new Object[][]{{1}, {3}, {6}};
    }

    @DataProvider(name = "new-one-genre")
    public static Object[][] getNewGenre() {
        int newId = new GenreService().getLastId() + 1;
        Genre genre = Genre
                .builder()
                .genreId(newId)
                .genreDescription("New Genre Description " + newId)
                .genreName("New genre Name " + newId)
                .build();
        return new Object[][]{{genre}};
    }
}
