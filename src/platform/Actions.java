package platform;
import inputs.ActionInput;
public class Actions {
    private final String type;
    private final String page;
    private String feature;
    private final String startWith;
    private Credentials credentials;
    private final Filter filters;
    private final String objectType;
    private final String count;
    private String movie;
    private final Double rate;
    private final String subscribedGenre;
    private Movie addedMovie;
    private String deletedMovie;

    public Actions(final ActionInput actionInput) {
        this.type = actionInput.getType();
        this.page = actionInput.getPage();
        this.feature = actionInput.getFeature();
        this.credentials = actionInput.getCredentials();
        this.startWith = actionInput.getStartsWith();
        this.filters = actionInput.getFilters();
        this.objectType = actionInput.getObjectType();
        this.count = actionInput.getCount();
        this.movie = actionInput.getMovie();
        this.rate = actionInput.getRate();
        this.subscribedGenre = actionInput.getSubscribedGenre();
        this.addedMovie = actionInput.getAddedMovie();
        this.deletedMovie = actionInput.getDeletedMovie();
    }

    public Actions(final Actions actions) {
        this.type = actions.getType();
        this.page = actions.getPage();
        this.feature = actions.getFeature();
        this.credentials = actions.getCredentials();
        this.startWith = actions.getStartWith();
        this.filters = actions.getFilters();
        this.objectType = actions.getObjectType();
        this.count = actions.getCount();
        this.movie = actions.getMovie();
        this.rate = actions.getRate();
        this.subscribedGenre = actions.getSubscribedGenre();
        this.addedMovie = actions.getAddedMovie();
        this.deletedMovie = actions.getDeletedMovie();
    }

    /**
     *
     */
    public String getType() {
        return type;
    }

    /**
     */
    public String getPage() {
        return page;
    }
    /**
     */
    public String getFeature() {
        return feature;
    }
    /**
     */
    public void setFeature(final String feature) {
        this.feature = feature;
    }
    /**
     */
    public String getStartWith() {
        return startWith;
    }
    /**
     */
    public Credentials getCredentials() {
        return credentials;
    }
    /**
     */
    public void setCredentials(final Credentials credentials) {
        this.credentials = credentials;
    }
    /**
     */
    public Filter getFilters() {
        return filters;
    }
    /**
     */
    public String getObjectType() {
        return objectType;
    }
    /**
     */
    public String getCount() {
        return count;
    }
    /**
     */
    public String getMovie() {
        return movie;
    }
    /**
     */
    public void setMovie(final String movie) {
        this.movie = movie;
    }
    /**
     */
    public Double getRate() {
        return rate;
    }

    /**
     * @return
     */
    public String getSubscribedGenre() {
        return subscribedGenre;
    }

    /**
     * @return
     */
    public Movie getAddedMovie() {
        return addedMovie;
    }

    /**
     * @param addedMovie
     */
    public void setAddedMovie(final Movie addedMovie) {
        this.addedMovie = addedMovie;
    }

    /**
     * @return
     */
    public String getDeletedMovie() {
        return deletedMovie;
    }

    /**
     * @param deletedMovie
     */
    public void setDeletedMovie(final String deletedMovie) {
        this.deletedMovie = deletedMovie;
    }
}

