package platform;

import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.ArrayList;
import java.util.Comparator;

public class AllActions {
    private ArrayList<Actions> actions;
    private GetCurrentPage getCurrentPage;
    private CurrentUser currentUser;
    private String current;
    private CurrentMovieList currentMovieList;
    private MementoUse memento;
    private Rates rates;

    public AllActions(final DataBase base, final String curr, final ArrayNode output) {
        this.actions = new ArrayList<>();
        this.actions.addAll(base.getAllActions());
        this.getCurrentPage = new GetCurrentPage(curr);
        CurrentPage currentPage = null;
        this.current = curr;
        this.currentUser = null;
        this.currentMovieList = null;
        this.memento = new MementoUse();
        this.rates = new Rates(base.getAllusers());
        actions(currentPage, base, output);
    }

    /**
     * functie cu ajutorul careia tratez fiecare tip de actiune, aceasta fiind ori
     * schimb pagina ori execut o actiune asupra unei pagini
     * @param currentPage
     * @param base
     * @param output
     */

    public void actions(CurrentPage currentPage, DataBase base, final ArrayNode output) {
        int ok = 0;
        for (Actions action : actions) {
            switch (action.getType()) {
                case "change page" -> {
                    if ((actions.get(0).getPage().equals("register")
                            || actions.get(0).getPage().equals("login")) && ok == 0) {
                        currentPage = new CurrentPage(action.getPage(), base, this.current,
                                this.currentUser, this.currentMovieList, this.memento, this.rates);
                        ok = 1;
                    } else if (ok == 1) {
                        if (currentPage.getUser() == null) {
                            this.currentUser = null;
                        } else {
                            this.currentUser = new CurrentUser(currentPage.getUser());
                        }
                        currentPage = new CurrentPage(action.getPage(), base, this.current,
                                this.currentUser, this.currentMovieList, this.memento, this.rates);
                    }
                    if (currentPage != null) {
                        currentPage.changePage(action, output, this.currentUser);
                    }
                    if (action.getPage().equals("logout") && currentPage.getSuccespage() == 1) {
                        this.current = "homepage";
                        this.currentUser = null;
                    }
                    if ((action.getPage().equals("movies")
                            || action.getPage().equals("see details"))
                            && currentPage.getSuccespage() == 1) {
                        this.current = currentPage.getGetCurrentPage().getCurrent();
                        if (currentPage.getCurrentMovieList() != null) {
                            currentMovieList = new CurrentMovieList(currentPage
                                    .getCurrentMovieList());
                        }
                    }
                    if (currentPage != null) {
                        this.memento = new MementoUse();
                        for (int i = 0; i < currentPage.getMemento().size(); i++) {
                            this.memento.add(currentPage.getMemento().getByIndex(i));
                        }
                    }
                }
                case "on page" -> {

                    if (currentPage != null) {
                        currentPage.executePageCommand(action, output, this.currentUser, base,
                                rates);
                    }
                    if (action.getFeature().equals("rate") && currentPage.getSucces() == 1) {
                        rates = new Rates(currentPage.getRates().getUsers());
                    }

                    if (currentPage.getSucces() == 1) {
                        this.current = currentPage.getGetCurrentPage().getCurrent();
                        if (currentPage.getUser() != null) {
                            this.currentUser = new CurrentUser(currentPage.getUser());
                        } else {
                            this.currentUser = null;
                        }
                        if (currentPage.getCurrentMovieList() != null) {
                            currentMovieList = new CurrentMovieList(currentPage
                                    .getCurrentMovieList());
                        }
                        base = new DataBase(currentPage.getDataBase());
                    }
                }
                case "database" -> {
                    if (currentPage != null)  {
                        currentPage.executeDatabase(action, output, this.currentUser);
                    }
                    if (currentPage != null) {
                        base = new DataBase(currentPage.getDataBase());
                    }
                }
                case "back" -> {
                    if (currentPage != null)  {
                        currentPage.backCommand(action, output, this.currentUser);
                        if (currentPage.getCurrentMovieList() != null) {
                            currentMovieList = new CurrentMovieList(currentPage
                                    .getCurrentMovieList());
                        }
                    }
                    if (currentPage != null) {
                        this.memento = new MementoUse();
                        for (int i = 0; i < currentPage.getMemento().size(); i++) {
                            this.memento.add(currentPage.getMemento().getByIndex(i));
                        }
                    }
                    if (currentPage != null) {
                        base = new DataBase(currentPage.getDataBase());
                    }
                }
            };
        }
        if (currentUser != null) {
            if (currentUser.user().getCredentials().getAccountType().equals("premium")) {
                Notifications notifications = new Notifications("No recommendation",
                        "Recommendation");
                if (!checkForRecommendation(base).equals("No recommendation")) {
                    notifications.setMovieName(checkForRecommendation(base));
                }
                if (currentUser.user().getNotifications() != null) {
                    currentUser.user().getNotifications().add(notifications);
                }
                OutCommand outCommand = new OutCommand(currentUser.user());
                output.addPOJO(outCommand);
            }
        }
        currentUser = null;
        current = "homepage";
    }

    /**
     * verifica daca exista vreo recomandare pentru user
     * @param base
     * @return
     */
    public String checkForRecommendation(DataBase base) {
        String recommendation = null;
        ArrayList<LikeForRecommendation> genres = new ArrayList<>();
        if (this.currentUser.user().getLikedMovies().size() != 0) {
            for (int i = 0; i < this.currentUser.user().getLikedMovies().size(); i++) {
                for (int j = 0; j < this.currentUser.user().getLikedMovies().get(i)
                        .getGenres().size(); j++) {
                    addGenre(this.currentUser.user().getLikedMovies().get(i)
                            .getGenres().get(j), genres);
                }
            }

            genres.sort(Comparator.comparingInt(LikeForRecommendation :: getNrlikes).reversed());
            recommendation = checkMovies(base, genres);
        } else {
            recommendation = "No recommendation";
        }
        return recommendation;
    }

    /**
     * functie ajutatoare pentru recomandare in care se verifica daca genul a mai aparut
     * pana acum si se stabileste numarul de aprecieri pentru fiecare
     * @param genre
     * @param genres
     */
    public void addGenre(final String genre, final ArrayList<LikeForRecommendation> genres) {
        boolean ok = false;
        for (LikeForRecommendation gen : genres) {
            if (gen.getGenre().equals(genre)) {
                ok = true;
                gen.setNrlikes(gen.getNrlikes() + 1);
                break;
            }
        }
        if (!ok) {
            LikeForRecommendation likeForRecommendation = new LikeForRecommendation(genre, 1);
            genres.add(likeForRecommendation);
        }
    }

    /**
     * se sorteaza filmele in functie de genuri si se gaseste recomandarea
     * @param dataBase
     * @param genres
     * @return
     */
    public String checkMovies(final DataBase dataBase,
                              final ArrayList<LikeForRecommendation> genres) {
        ArrayList<Movie> allmovies = new ArrayList<>();
        for (int i = 0; i < dataBase.getAllmovies().size(); i++) {
            boolean ok = true;
            for (int j = 0; j < dataBase.getAllmovies().get(i).getCountriesBanned().size(); j++) {
                if (dataBase.getAllmovies().get(i).getCountriesBanned().get(j).equals(this
                        .currentUser.user().getCredentials().getCountry())) {
                    ok = false;
                    break;
                }
            }
            if (ok) {
                allmovies.add(dataBase.getAllmovies().get(i));
            }
            for (int j = 0; j < this.currentUser.user().getWatchedMovies().size(); j++) {
                if (this.currentUser.user().getWatchedMovies().get(j).getName().equals(dataBase
                        .getAllmovies().get(i).getName())) {
                    allmovies.remove(dataBase.getAllmovies().get(i));
                    break;
                }
            }
        }


        for (Movie allmovie : allmovies) {
            for (LikeForRecommendation genre : genres) {
                for (int i = 0; i < allmovie.getGenres().size(); i++) {
                    if (allmovie.getGenres().get(i).equals(genre.getGenre())) {
                        allmovie.setNumLikeGenres(allmovie.getNumLikeGenres() + genre.getNrlikes());
                    }
                }
            }
        }
        allmovies.sort(Comparator.comparingInt(Movie :: getNumLikeGenres));
        int max = -1;
        int index = 0;
        Movie movieprev = null;
        for (int i = 0; i < allmovies.size(); i++) {
            if (allmovies.get(i).getNumLikeGenres() > max) {
                max = allmovies.get(i).getNumLikeGenres();
                movieprev = new Movie(allmovies.get(i));
                index = i;
            } else {
                if (allmovies.get(i).getNumLikeGenres() == max) {
                    if (movieprev != null) {
                        if (movieprev.getName()
                                .equals(allmovies.get(i - 1).getName())) {
                            for (int j = 0; j < allmovies.get(i).getGenres().size(); j++) {
                                for (int k = 0; k < movieprev.getGenres().size(); k++) {
                                    if (allmovies.get(i).getGenres().get(j).compareToIgnoreCase(
                                            movieprev.getGenres().get(k)) > 0) {
                                        index = i;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (allmovies.get(index) != null) {
            return allmovies.get(index).getName();
        }
        return "No recommendation";
    }
    /**
     */
    public ArrayList<Actions> getActions() {
        return actions;
    }
    /**
     */
    public void setActions(final ArrayList<Actions> actions) {
        this.actions = actions;
    }
    /**
     */
    public GetCurrentPage getGetCurrentPage() {
        return getCurrentPage;
    }
    /**
     */
    public void setGetCurrentPage(final GetCurrentPage getCurrentPage) {
        this.getCurrentPage = getCurrentPage;
    }
    /**
     */
    public CurrentUser getCurrentUser() {
        return currentUser;
    }
    /**
     */
    public void setCurrentUser(final CurrentUser currentUser) {
        this.currentUser = currentUser;
    }
    /**
     */
    public String getCurrent() {
        return current;
    }
    /**
     */
    public void setCurrent(final String current) {
        this.current = current;
    }
    /**
     */
    public CurrentMovieList getCurrentMovieList() {
        return currentMovieList;
    }
    /**
     */
    public void setCurrentMovieList(final CurrentMovieList currentMovieList) {
        this.currentMovieList = currentMovieList;
    }
}
