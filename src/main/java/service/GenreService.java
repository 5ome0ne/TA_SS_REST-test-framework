package service;

import client.HttpClient;
import com.google.gson.Gson;
import constant.OrderType;
import entity.Genre;
import entity.ListOptions;
import io.qameta.allure.Step;
import io.restassured.internal.http.Status;
import org.apache.log4j.Logger;
import response.BaseResponse;
import utils.EndpointBuilder;
import org.testng.Assert;

import java.util.Arrays;
import java.util.List;

public class GenreService {

    private BaseResponse<Genre> response;
    private static final Logger LOG = Logger.getLogger(GenreService.class);

    @Step("Make a request: Get genre by index {genreId}.")
    public GenreService getGenre(int genreId) {
        String endpoint = new EndpointBuilder().pathParameter("genre").pathParameter(genreId).get();
        response = new BaseResponse<>(HttpClient.get(endpoint), Genre.class);
        LOG.info("Get Genre StatusCode= " + response.getStatusCode());
        return this;
    }

    @Step("Get Genres Model")
    public Genre getModel() {
        return response.getBody();
    }

    @Step("Make a request: Get genres with option-> {{options.page}, {options.size}, {options.sortBy}, {options.orderType.order}}.")
    public GenreService getGenres(ListOptions options) {
        EndpointBuilder endpoint = new EndpointBuilder().pathParameter("genres");
        if (options.getOrderType() != null) endpoint.queryParam("orderType", options.getOrderType().getOrder());
        endpoint
                .queryParam("page", options.getPage())
                .queryParam("pagination", options.isPagination())
                .queryParam("size", options.getSize());
        if (options.getSortBy() != null) endpoint.queryParam("sortBy", options.getSortBy());
        response = new BaseResponse<>(HttpClient.get(endpoint.get()), Genre.class);
        LOG.info("Get GenreS StatusCode= " + response.getStatusCode());
        return this;
    }

    @Step("Make a request: Post genre with ID {genre.genreId}.")
    public GenreService postGenre(Genre genre) {
        String endpoint = new EndpointBuilder().pathParameter("genre").get();
        String jsonBody = new Gson().toJson(genre);
        response = new BaseResponse<>(HttpClient.post(endpoint, jsonBody, null), Genre.class);
        LOG.info("Add Genre StatusCode= " + response.getStatusCode());
        return this;
    }

    @Step("Make a request: Delete genre with ID {genreId}.")
    public GenreService deleteGenre(int genreId) {
        String endpoint = new EndpointBuilder().pathParameter("genre").pathParameter(genreId).get();
        response = new BaseResponse<>(HttpClient.delete(endpoint), Genre.class);
        LOG.info("Remove Genre StatusCode= " + response.getStatusCode());
        return this;
    }

    @Step("Make a request: Put genre with ID {genre.genreId}.")
    public GenreService putGenre(Genre genre) {
        String endpoint = new EndpointBuilder().pathParameter("genre").get();
        String jsonBody = new Gson().toJson(genre);
        response = new BaseResponse<>(HttpClient.put(endpoint, jsonBody, null), Genre.class);
        LOG.info("Put Genre StatusCode= " + response.getStatusCode());
        return this;
    }

    @Step("Make a request: Search genre by keys {combination}.")
    public GenreService searchGenres(String[] combination) {
        String query = String.join(" ", combination);
        String endpoint = new EndpointBuilder()
                .pathParameter("genres")
                .pathParameter("search")
                .queryParam("query", query).get();
        response = new BaseResponse<>(HttpClient.get(endpoint), Genre.class);
        LOG.info(String.format("Search Genre by '%s' StatusCode= %s", Arrays.toString(combination), response.getStatusCode()));
        return this;
    }

    @Step("Check response status.")
    public GenreService verifyStatusSuccess() {
        Assert.assertTrue(
                Status.SUCCESS.matches(response.getStatusCode()),
                String.format("Response Status '%s' not SUCCESS", response.getStatusCode()));
        return this;
    }

    @Step("Get list<Genre> models")
    public List<Genre> getListModels() {
        return response.getListObjects();
    }

    @Step("Get last id of genre.")
    public int getLastId() {
        return getLastGenre().getGenreId();
    }

    @Step("Get last genre.")
    public Genre getLastGenre() {
        ListOptions options = ListOptions.builder().orderType(OrderType.DESC).size(1).build();
        getGenres(options);
        return getListModels().get(0);
    }

    @Step("Check is genres are equals.")
    public GenreService isGenreEquals(Genre genre1, Genre genre2) {
        Assert.assertEquals(
                genre1,
                genre2,
                "Genres 'Check Equals' is not SUCCESS");
        return this;
    }

    @Step("Check is genres arent equals.")
    public GenreService isGenreNotEquals(Genre genre1, Genre genre2) {
        Assert.assertNotEquals(
                genre1,
                genre2,
                "Genres 'Check NO Equals' is not SUCCESS");
        return this;
    }

    @Step("Get random genre by.")
    public Genre getRandomGenre() {
        ListOptions options = ListOptions.builder().build();
        List<Genre> list = getGenres(options)
                .verifyStatusSuccess()
                .getListModels();

        int randomIndex = (int) ((list.size() - 1) * Math.random());
        return list.get(randomIndex);
    }

    @Step("Check all searched genres contains one of {combination}.")
    public void isSearchedGenresNamesContains(String[] combination) {
        List<Genre> list = getListModels();
        boolean isAllMatch = list.stream()
                .allMatch(genre -> Arrays.stream(combination)
                        .anyMatch(comb -> genre.getGenreName().contains(comb)));
        Assert.assertTrue(isAllMatch, String.format("Some of Genre Name isn't match any one of combination '%s'", Arrays.toString(combination)));
    }
}
