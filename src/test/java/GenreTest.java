import constant.Provider;
import entity.Genre;
import entity.ListOptions;
import io.qameta.allure.Description;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import service.GenreService;
import service.TestListener;

import java.util.List;

@Listeners({TestListener.class})
public class GenreTest {

    private GenreService genreService;

    @BeforeClass(description = "Initialize 'Genre Service'")
    void beforeTest() {
        genreService = new GenreService();
    }

    @Test(dataProvider = "new-one-genre",
            dataProviderClass = Provider.class,
            description = "Verify POST Genre")
    @Description("Try to Post generated new Genre, than Get it and check equals, then Delete it")
    public void postGenre(Genre newGenre) {

        genreService
                .postGenre(newGenre)
                .verifyStatusSuccess()
                .deleteGenre(newGenre.getGenreId())
                .verifyStatusSuccess();
    }

    @Test(dataProvider = "new-one-genre",
            dataProviderClass = Provider.class,
            description = "Verify GET Genre")
    @Description("Try to Get generated new Genre")
    public void getGenre(Genre newGenre) {

        Genre getedGenre = genreService
                .postGenre(newGenre)
                .verifyStatusSuccess()
                .getGenre(newGenre.getGenreId())
                .verifyStatusSuccess()
                .getModel();

        genreService
                .isGenreEquals(getedGenre, newGenre)
                .deleteGenre(newGenre.getGenreId())
                .verifyStatusSuccess();
    }

    @Test(dataProvider = "new-one-genre",
            dataProviderClass = Provider.class,
            description = "Verify DELETE Genre")
    @Description("Try to Delete generated new Genre")
    public void deleteGenre(Genre newGenre) {

        genreService
                .postGenre(newGenre)
                .verifyStatusSuccess()
                .deleteGenre(newGenre.getGenreId())
                .verifyStatusSuccess();
    }

    @Test(dataProvider = "new-one-genre",
            dataProviderClass = Provider.class,
            description = "Verify PUT Genre")
    @Description("Try to Post generated new Genre, than Put changed, check equals, then Delete it")
    public void putGenre(Genre newGenre) {

        genreService
                .postGenre(newGenre)
                .verifyStatusSuccess();

        newGenre.setGenreDescription("Edited Description");
        newGenre.setGenreName("Edited Name");

        Genre getedGenre = genreService
                .putGenre(newGenre)
                .verifyStatusSuccess()
                .getGenre(newGenre.getGenreId())
                .verifyStatusSuccess()
                .getModel();

        genreService
                .isGenreEquals(getedGenre, newGenre)
                .deleteGenre(newGenre.getGenreId())
                .verifyStatusSuccess();
    }

    @Test(description = "Verify GET List of Genres")
    @Description("Try to Get array of genres with default ListOptions, than try to cast to type Genre, and print ia all for visual comparison")
    public void parseFirs10GenresNativeSort() {

        ListOptions options = ListOptions.builder().build();
        List<Genre> list = genreService
                .getGenres(options)
                .verifyStatusSuccess()
                .getListModels();

        list.forEach(System.out::println);
    }

    @Test(dataProvider = "letter-combination-number",
            dataProviderClass = Provider.class,
            description = "Verify GET GenreS->Search by letter combination")
    @Description("Try to Search genres by letter combination, than check is all of funded Genres gas that combination of letters")
    public void searchGenresByCombination(int numberOfCombination) {

        String[] combination = new String[numberOfCombination];
        for (int i = 0; i < numberOfCombination; i++) {
            Genre randomGenre = genreService.getRandomGenre();
            String name = randomGenre.getGenreName();
            int minAlphaSequenceLength = 3;
            combination[i] = name.substring(name.length() - minAlphaSequenceLength);
        }

        genreService
                .searchGenres(combination)
                .verifyStatusSuccess()
                .isSearchedGenresNamesContains(combination);
    }

    @Test(dataProvider = "new-one-genre",
            dataProviderClass = Provider.class,
            description = "Verify Genre not Equals")
    @Description("Try to Equals 'POST' genre vs his edited copy")
    public void negativeEqualsGenre(Genre newGenre) {

        genreService
                .postGenre(newGenre)
                .verifyStatusSuccess();

        newGenre.setGenreDescription("Edited Description not Equals");
        newGenre.setGenreName("Edited Name not Equals");

        Genre genreFromGet = genreService
                .getGenre(newGenre.getGenreId())
                .verifyStatusSuccess()
                .getModel();

        genreService
                .isGenreNotEquals(genreFromGet, newGenre)
                .deleteGenre(newGenre.getGenreId())
                .verifyStatusSuccess();
    }
}
