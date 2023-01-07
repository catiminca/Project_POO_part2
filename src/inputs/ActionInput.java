package inputs;

import platform.Credentials;
import platform.Filter;
import platform.Movie;

public final class ActionInput {
    private String type;
    private String page;
    private Credentials credentials;
    private String feature;
    private String startsWith;
    private Filter filters;
    private String objectType;
    private String count;
    private String movie;
    private Double rate;
    private String subscribedGenre;
    private Movie addedMovie;
    private String deletedMovie;

    public ActionInput() { }
    /**
     *
     */
    public String getType() {
        return this.type;
    }

    /**
     *
     */
    public void setType(final String type) {
        this.type = type;
    }

    /**
     *
     */
    public String getPage() {
        return this.page;
    }

    /**
     *
     */
    public void setPage(final String page) {
        this.page = page;
    }

    /**
     *
     */
    public Credentials getCredentials() {
        return this.credentials;
    }

    /**
     *
     */
    public void setCredentials(final Credentials credentials) {
        this.credentials = credentials;
    }

    /**
     *
     */
    public String getFeature() {
        return this.feature;
    }

    /**
     *
     */
    public void setFeature(final String feature) {
        this.feature = feature;
    }

    /**
     *
     */
    public String getStartsWith() {
        return this.startsWith;
    }

    /**
     *
     */
    public Filter getFilters() {
        return this.filters;
    }

    /**
     *
     */
    public String getObjectType() {
        return this.objectType;
    }

    /**
     *
     */
    public String getCount() {
        return this.count;
    }

    /**
     *
     */
    public Double getRate() {
        return this.rate;
    }

    /**
     *
     */
    public String getMovie() {
        return this.movie;
    }

    /**
     *
     */
    public void setMovie(final String movie) {
        this.movie = movie;
    }

    /**
     * @return
     */
    public String getSubscribedGenre() {
        return subscribedGenre;
    }

    public void setStartsWith(final String startsWith) {
        this.startsWith = startsWith;
    }

    public Movie getAddedMovie() {
        return addedMovie;
    }

    public void setAddedMovie(final Movie addedMovie) {
        this.addedMovie = addedMovie;
    }

    public String getDeletedMovie() {
        return deletedMovie;
    }

    public void setDeletedMovie(final String deletedMovie) {
        this.deletedMovie = deletedMovie;
    }
}
