package platform;

import com.fasterxml.jackson.annotation.JsonIgnore;
import inputs.MovieInput;

import java.util.ArrayList;
public class Movie {
    private String name;
    private String year;
    private int duration;
    private ArrayList<String> genres;
    private ArrayList<String> actors;
    private ArrayList<String> countriesBanned;
    private Double rating;
    private int numLikes;
    private int numRatings;
    @JsonIgnore
    private int numLikeGenres = 0;

    public Movie() { }
    public Movie(final MovieInput movie) {
        this.name = movie.getName();
        this.year = movie.getYear();
        this.duration = movie.getDuration();
        this.genres = movie.getGenres();
        this.actors = movie.getActors();
        this.countriesBanned = movie.getCountriesBanned();
        if (movie.getRating() == null) {
            this.rating = (double) 0;
        }
        this.numLikes = movie.getNumLikes();
        this.numRatings = movie.getNumRatings();
    }

    public Movie(final Movie movie) {
        this.name = movie.getName();
        this.year = movie.getYear();
        this.duration = movie.getDuration();
        this.genres = movie.getGenres();
        this.actors = movie.getActors();
        this.countriesBanned = movie.getCountriesBanned();
        this.rating = movie.getRating();
        this.numLikes = movie.getNumLikes();
        this.numRatings = movie.getNumRatings();
    }

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(final String name) {
        this.name = name;
    }
    /**
     * @return
     */
    public String getYear() {
        return year;
    }

    /**
     * @param year
     */
    public void setYear(final String year) {
        this.year = year;
    }

    /**
     * @return
     */
    public int getDuration() {
        return duration;
    }

    /**
     * @param duration
     */
    public void setDuration(final int duration) {
        this.duration = duration;
    }

    /**
     * @return
     */
    public ArrayList<String> getGenres() {
        return genres;
    }

    /**
     * @param genres
     */
    public void setGenres(final ArrayList<String> genres) {
        this.genres = genres;
    }

    /**
     * @return
     */
    public ArrayList<String> getActors() {
        return actors;
    }

    /**
     * @param actors
     */
    public void setActors(final ArrayList<String> actors) {
        this.actors = actors;
    }

    /**
     * @return
     */
    public ArrayList<String> getCountriesBanned() {
        return countriesBanned;
    }

    /**
     * @param countriesBanned
     */
    public void setCountriesBanned(final ArrayList<String> countriesBanned) {
        this.countriesBanned = countriesBanned;
    }

    /**
     * @return
     */
    public Double getRating() {
        return rating;
    }

    /**
     * @param rating
     */
    public void setRating(final Double rating) {
        this.rating = rating;
    }

    /**
     * @return
     */
    public int getNumLikes() {
        return numLikes;
    }

    /**
     * @param nrLikes
     */
    public void setNumLikes(final int nrLikes) {
        this.numLikes = nrLikes;
    }
    /**
     *
     */
    public int getNumRatings() {
        return numRatings;
    }

    /**
     * @param nrRating
     */
    public void setNumRatings(final int nrRating) {
        this.numRatings = nrRating;
    }

    /**
     * @return
     */
    public int getNumLikeGenres() {
        return numLikeGenres;
    }

    /**
     * @param numLikeGenres
     */
    public void setNumLikeGenres(final int numLikeGenres) {
        this.numLikeGenres = numLikeGenres;
    }
}
