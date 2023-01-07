package platform;

import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.ArrayList;
import java.util.Objects;

public class CurrentPage {
    private User user;
    private String currentPage;
    private String name;
    private ArrayList<Movie> currentMovieList;
    private final CurrentMovieList currentList;
    private DataBase dataBase;
    private final GetCurrentPage getCurrentPage;
    private int succes;
    private User newuser;
    private String movie;
    private int succespage;
    private int purchaseOk;
    private int watchOk;
    private MementoUse memento = new MementoUse();
    private Rates rates;

    public CurrentPage(final String pageName, final DataBase database, final String nameprev,
                       final CurrentUser currentUser, final CurrentMovieList currentMovieList,
                       final MementoUse mementoUse, final Rates rate) {

        if (currentUser != null) {
            this.user = new User(currentUser.user());
        } else {
            this.user = null;
        }
        if (this.dataBase == null) {
            this.dataBase = new DataBase(database);
        }
        this.name = pageName;
        this.getCurrentPage = new GetCurrentPage(nameprev);
        if (currentMovieList != null) {
            this.currentList = new CurrentMovieList(currentMovieList.getMovielist());
        } else {
            this.currentList = new CurrentMovieList();
        }
        if (mementoUse != null) {
            for (int i = 0; i < mementoUse.size(); i++) {
                memento.add(mementoUse.getByIndex(i));
            }
        }
        if (rate != null) {
            this.rates = new Rates(rate.getUsers());
        }
    }

    /**
     * functie care schimba pagina in functie de actiunea data
     *
     * @param actions
     * @param output
     * @param currentUser
     */
    public void changePage(final Actions actions, final ArrayNode output,
                           final CurrentUser currentUser) {
        if (actions.getPage().equals("register")) {
            register(output, currentUser);
            return;
        }
        if (actions.getPage().equals("login")) {
            login(currentUser, output);
            return;
        }
        if (actions.getPage().equals("logout")) {
            logout(currentUser, output);
            return;
        }
        if (actions.getPage().equals("movies")) {
            movies(currentUser, output);
            return;
        }
        if (actions.getPage().equals("see details")) {
            seeDetails(actions, output);
            return;
        }
        if (actions.getPage().equals("upgrades")) {
            upgrades(output);
        }
    }

    /**
     * functia care schimba pagina de login
     *
     * @param currentUser
     * @param output
     */
    public void login(final CurrentUser currentUser, final ArrayNode output) {
        if (currentUser == null && getCurrentPage.getCurrent().equals("homepage")) {
            this.getCurrentPage.setCurrent("login");
            this.getCurrentPage.setLastPage("homepage");
            this.memento.add(getCurrentPage.saveStateToMemento());

        } else {
            OutCommand outCommand = new OutCommand("Error");
            output.addPOJO(outCommand);
        }
    }

    /**
     * functia care imi face logout
     *
     * @param currentUser
     * @param output
     */
    public void logout(final CurrentUser currentUser, final ArrayNode output) {
        this.succespage = 0;
        if (currentUser != null) {
            this.currentPage = "homepage";
            this.getCurrentPage.setCurrent("homepage");
            this.user = null;
            this.succespage = 1;
            this.memento.add(getCurrentPage.saveStateToMemento());
        } else {
            OutCommand outCommand = new OutCommand("Error");
            output.addPOJO(outCommand);
        }
    }

    /**
     * functie pentru inregistrarea unui nou user
     *
     * @param output
     * @param currentUser
     */
    public void register(final ArrayNode output, final CurrentUser currentUser) {
        if (currentUser == null && getCurrentPage.getCurrent().equals("homepage")) {
            this.getCurrentPage.setCurrent("register");
            this.getCurrentPage.setLastPage("homepage");
            this.memento.add(getCurrentPage.saveStateToMemento());
        } else {
            OutCommand outCommand = new OutCommand("Error");
            output.addPOJO(outCommand);
        }
    }

    /**
     * schimba pagina actuala in cea de filme
     *
     * @param currentUser
     * @param output
     */
    public void movies(final CurrentUser currentUser, final ArrayNode output) {
        this.succespage = 0;
        OutCommand outCommand;
        if (currentUser != null) {
            this.getCurrentPage.setCurrent("movies");
            this.getCurrentPage.setLastPage(this.currentPage);
            this.currentMovieList = new ArrayList<>();
            this.currentMovieList = sortByCountry(currentUser);
            if (!this.memento.getMem().getState().equals("movies")) {
                this.memento.add(getCurrentPage.saveStateToMemento());
            }
            this.succespage = 1;
            newuser = new User(user);
            outCommand = new OutCommand(null, currentMovieList, newuser);
        } else {
            outCommand = new OutCommand("Error");
        }
        output.addPOJO(outCommand);
    }

    /**
     * schimba pagina actuala in cea de see details
     *
     * @param actions
     * @param output
     */
    public void seeDetails(final Actions actions, final ArrayNode output) {
        this.succespage = 0;
        OutCommand outCommand;
        if (this.memento.getMem().getState().equals("movies")
                && currentList.getMovielist().size() > 0) {

            int ok = 0;
            if (checkMovie(actions)) {
                ok = 1;
            }
            if (this.movie == null || ok == 0) {
                outCommand = new OutCommand("Error");
                output.addPOJO(outCommand);
                return;
            } else {
                this.currentPage = "see details";
                this.currentMovieList = new ArrayList<>();
                for (int i = 0; i < currentList.getMovielist().size(); i++) {
                    if (currentList.getMovielist().get(i).getName().equals(this.movie)) {
                        this.currentMovieList.add(currentList.getMovielist().get(i));
                        break;
                    }
                }
                this.succespage = 1;
                this.getCurrentPage.setLastPage("movies");
                this.getCurrentPage.setCurrent("see details");
                this.memento.add(getCurrentPage.saveStateToMemento());
                newuser = new User(user);
                outCommand = new OutCommand(null, currentMovieList, user);
            }
        } else {
            outCommand = new OutCommand("Error");
        }
        output.addPOJO(outCommand);
    }

    /**
     * schimba pagina in upgrades
     *
     * @param output
     */
    public void upgrades(final ArrayNode output) {
        if (this.user != null) {
            this.getCurrentPage.setLastPage(this.currentPage);
            this.getCurrentPage.setCurrent("upgrades");
            this.memento.add(getCurrentPage.saveStateToMemento());
        } else {
            OutCommand outCommand = new OutCommand("Error");
            output.addPOJO(outCommand);
        }
    }

    /**
     * functie pentru realizarea comenzii de a te intoarce pe pagina anterioara
     * @param actions
     * @param output
     * @param currentUser
     */
    public void backCommand(final Actions actions, final ArrayNode output,
                            final CurrentUser currentUser) {
        if (this.memento.getMem().getState().equals("homepage")
                || this.memento.getMem().getState().equals("login")
                || this.memento.getMem().getState().equals("register")) {
            OutCommand outCommand = new OutCommand("Error");
            output.addPOJO(outCommand);
        } else {

                this.memento.remove(this.memento.getMem());
                getCurrentPage.setCurrent(this.memento.getMem().getState());
                this.currentMovieList = new ArrayList<>();
                this.currentMovieList = sortByCountry(currentUser);
                if (getCurrentPage.getCurrent().equals("movies")) {

                    movies(currentUser, output);
                    return;
                }
            }
//            if (getCurrentPage.getCurrent().equals("see details")) {
//                seeDetails(actions, );
//            }
    }
    /**
     * verifica daca un subsir este intr-un sir
     *
     * @param sequence
     * @param subsequence
     * @return
     */
    public int contains(final String sequence, final String subsequence) {
        return sequence.indexOf(subsequence);
    }

    /**
     * verifica daca un film este in lista de filme
     *
     * @param actions
     * @return
     */
    public boolean checkMovie(final Actions actions) {
        int ok = 0;
        for (int i = 0; i < currentList.getMovielist().size(); i++) {
            if (currentList.getMovielist().get(i).getName().equals(actions.getMovie())) {
                this.movie = actions.getMovie();
                ok = 1;
                break;
            }
        }
        return ok == 1;
    }


    /**
     * functie care trateaza cazurile in care vine o comanda de a
     * executa o actiune pe o anumita pagina
     *
     * @param actions
     * @param output
     * @param currentUser
     */
    public void executePageCommand(final Actions actions, final ArrayNode output,
                                   final CurrentUser currentUser, final DataBase base,
                                   final Rates rates) {
        if (actions.getFeature().equals("register")) {
            registerAction(actions, output);
            return;
        }
        if (actions.getFeature().equals("login")) {
            loginAction(actions, output, base);
            return;
        }
        if (actions.getFeature().equals("search")) {
            searchAction(currentUser, actions, output);
            return;
        }
        if (actions.getFeature().equals("filter")) {
            filterAction(actions, currentUser, output);
            return;
        }

        if (actions.getFeature().equals("buy tokens")) {
            buyTokens(actions, output);
            return;
        }
        if (actions.getFeature().equals("buy premium account")) {
            buyPremiumAcc(output);
            return;
        }
        if (actions.getFeature().equals("purchase")) {
            purchase(currentUser, output);
            return;
        }
        if (actions.getFeature().equals("watch")) {
            watch(output);
            return;
        }
        if (actions.getFeature().equals("like")) {
            like(output);
            return;
        }
        if (actions.getFeature().equals("rate")) {
            rate(actions, output);
            return;
        }
        if (actions.getFeature().equals("subscribe")) {
            executeSubcribe(actions, output);
            return;
        }
        OutCommand outCommand = new OutCommand("Error");
        output.addPOJO(outCommand);
    }

    /**
     * actiunea de register
     *
     * @param actions
     * @param output
     */
    public void registerAction(final Actions actions, final ArrayNode output) {
        OutCommand outCommand = null;
        this.succes = 0;
        if (user == null) {
            int ok = 0;
            for (int i = 0; i < dataBase.getAllusers().size(); i++) {
                if (Objects.equals(actions.getCredentials().getName(),
                        dataBase.getAllusers().get(i).getCredentials().getName())) {
                    ok = 1;
                    outCommand = new OutCommand("Error");
                    break;
                }
            }
            if (ok == 0) {
                this.newuser = new User(actions.getCredentials().getName(),
                        actions.getCredentials().getPassword(),
                        actions.getCredentials().getAccountType(), actions.getCredentials()
                        .getCountry(),
                        actions.getCredentials().getBalance());
                this.user = new User(newuser);
                dataBase.addWhenRegister(user);
                this.getCurrentPage.setCurrent("register");
                this.getCurrentPage.setLastPage("homepage");
                this.succes = 1;
                newuser = new User(user);
                if (currentMovieList == null) {
                    outCommand = new OutCommand(null, newuser);
                }
            }
        } else {
            outCommand = new OutCommand("Error");
        }
        output.addPOJO(outCommand);
    }

    /**
     * actiunea de login
     *
     * @param actions
     * @param output
     */
    public void loginAction(final Actions actions, final ArrayNode output, final DataBase base) {
        OutCommand outCommand = null;
        this.succes = 0;
        if (user == null) {
            int ok = 0;
            for (int i = 0; i < dataBase.getAllusers().size(); i++) {
                if (Objects.equals(actions.getCredentials().getName(),
                        dataBase.getAllusers().get(i).getCredentials().getName())
                        && Objects.equals(actions.getCredentials().getPassword(),
                                dataBase.getAllusers().get(i).getCredentials().getPassword())) {
                    this.newuser = new User(base.getAllusers().get(i));
                    this.user = new User(newuser);
                    this.getCurrentPage.setCurrent("login");
                    this.getCurrentPage.setLastPage("homepage");
                    this.succes = 1;
                    if (currentMovieList == null) {
                        newuser = new User(user);
                        outCommand = new OutCommand(null, newuser);
                    }
                    ok = 1;
                    break;
                }
            }
            if (ok == 0) {
                outCommand = new OutCommand("Error");
            }
        } else {
            outCommand = new OutCommand("Error");
        }
        output.addPOJO(outCommand);
    }

    /**
     * actiunea de search in care se cauta un film in lista
     * de filme curente
     *
     * @param currentUser
     * @param actions
     * @param output
     */
    public void searchAction(final CurrentUser currentUser, final Actions actions,
                             final ArrayNode output) {
        OutCommand outCommand;
        this.succes = 0;
        this.currentMovieList = new ArrayList<>();
        if (getCurrentPage.getCurrent().equals("movies")) {
            ArrayList<Movie> allMovies = new ArrayList<>();
            for (int i = 0; i < dataBase.getAllmovies().size(); i++) {
                if (contains(dataBase.getAllmovies().get(i).getName(), actions.getStartWith())
                        == 0) {
                    if (countryInvertCheck(currentUser, i)) {
                        allMovies.add(dataBase.getAllmovies().get(i));
                    }
                }
            }
            this.currentMovieList.addAll(allMovies);
            this.succes = 1;
            newuser = new User(user);
            outCommand = new OutCommand(null, currentMovieList, newuser);

        } else {
            outCommand = new OutCommand("Error");
        }
        output.addPOJO(outCommand);
    }

    /**
     * actiunea de filter in care se va actualiza lista de
     * filme curente in functie de preferinta
     *
     * @param actions
     * @param currentUser
     * @param output
     */
    public void filterAction(final Actions actions, final CurrentUser currentUser,
                             final ArrayNode output) {
        OutCommand outCommand = null;
        this.succes = 0;
        this.currentMovieList = new ArrayList<>();
        if (getCurrentPage.getCurrent().equals("movies")) {
            this.succes = 1;
            this.currentMovieList = sortByCountry(currentUser);
            Filter filter = actions.getFilters();
            if (filter.getContains() != null) {
                currentMovieList = filter.sortedByContains(currentMovieList);
            }
            if (filter.getSort() != null) {
                if (filter.getSort().getRating() != null && filter.getSort()
                        .getDuration() != null) {
                    currentMovieList = filter.sortByBoth(currentMovieList);
                } else if (filter.getSort().getRating() != null) {
                    currentMovieList = filter.sortByRating(currentMovieList, filter.getSort()
                            .getRating());
                } else if (filter.getSort().getDuration() != null) {
                    filter.sortByDuration(currentMovieList, filter.getSort().getDuration());
                }
            }
            if (filter.getSort() != null || filter.getContains() != null) {
                newuser = new User(user);
                outCommand = new OutCommand(null, currentMovieList, newuser);
            } else if (filter.getSort() == null || filter.getContains() == null) {
                outCommand = new OutCommand("Error");
            }
        } else {
            outCommand = new OutCommand("Error");
        }
        output.addPOJO(outCommand);
    }

    /**
     * actiunea in care se cumpara tokens
     *
     * @param actions
     * @param output
     */
    public void buyTokens(final Actions actions, final ArrayNode output) {
        OutCommand outCommand;
        this.succes = 0;
        if (getCurrentPage.getCurrent().equals("upgrades")) {
            user.setTokensCount(user.getTokensCount() + Integer.parseInt(actions.getCount()));
            int balance = Integer.parseInt(user.getCredentials().getBalance())
                    - Integer.parseInt(actions.getCount());
            for (int i = 0; i < dataBase.getAllusers().size(); i++) {
                if (dataBase.getAllusers().get(i).getCredentials().getName()
                        .equals(user.getCredentials().getName())) {
                    dataBase.getAllusers().get(i).getCredentials().setBalance(String
                            .valueOf(balance));
                    int num = dataBase.getAllusers().get(i).getTokensCount()
                            + Integer.parseInt(actions.getCount());
                    dataBase.getAllusers().get(i).setTokensCount(num);
                    break;
                }
            }
            user.getCredentials().setBalance(String.valueOf(balance));
            this.succes = 1;
        } else {
            outCommand = new OutCommand("Error");
            output.addPOJO(outCommand);
        }
    }

    /**
     * se cumpara cont de premium
     *
     * @param output
     */
    public void buyPremiumAcc(final ArrayNode output) {
        OutCommand outCommand;
        this.succes = 0;
        int aux = 10;
        if (getCurrentPage.getCurrent().equals("upgrades")) {
            user.getCredentials().setAccountType("premium");
            user.setTokensCount(user.getTokensCount() - aux);
            this.succes = 1;
            for (int i = 0; i < dataBase.getAllusers().size(); i++) {
                if (dataBase.getAllusers().get(i).getCredentials().getName()
                        .equals(user.getCredentials().getName())) {
                    dataBase.getAllusers().get(i).getCredentials().setAccountType("premium");
                    dataBase.getAllusers().get(i).setTokensCount(dataBase.getAllusers().get(i)
                            .getTokensCount() - aux);
                    break;
                }
            }
        } else {
            outCommand = new OutCommand("Error");
            output.addPOJO(outCommand);
        }
    }

    /**
     * se cumpara filmul ales
     * @param currentUser
     * @param output
     */
    public void purchase(final CurrentUser currentUser, final ArrayNode output) {
        OutCommand outCommand;
        this.purchaseOk = 0;
        this.succes = 0;
        if (getCurrentPage.getCurrent().equals("see details")) {
            if (user.getTokensCount() >= 0) {
                int ok = 0;
                this.currentMovieList = new ArrayList<>();
                for (int i = 0; i < dataBase.getAllmovies().size(); i++) {
                    if (dataBase.getAllmovies().get(i).getName().equals(this.movie)) {
                        if (countryInvertCheck(currentUser, i)) {
                            this.currentMovieList.add(dataBase.getAllmovies().get(i));
                            ok = 1;
                            break;
                        }
                    }
                }
                if (ok == 1) {
                    boolean haspurch = false;
                    for (int k = 0; k < this.user.getPurchasedMovies().size(); k++) {
                        if (this.user.getPurchasedMovies().get(k).getName()
                                .equals(currentMovieList.get(0).getName())) {
                            haspurch = true;
                            break;
                        }
                    }
                    if (!haspurch) {
                        this.purchaseOk = 1;
                        this.succes = 1;
                        if (this.user.getPurchasedMovies().size() == 0) {
                            this.user.setPurchasedMovies(currentMovieList);
                            for (int i = 0; i < dataBase.getAllusers().size(); i++) {
                                if (dataBase.getAllusers().get(i).getCredentials().getName()
                                        .equals(this.user.getCredentials().getName())) {
                                    dataBase.getAllusers().get(i)
                                            .setPurchasedMovies(currentMovieList);
                                    setTokensCountByUser(i);
                                }
                            }
                        } else {
                            this.user.getPurchasedMovies().add(currentMovieList.get(0));
                            for (int i = 0; i < dataBase.getAllusers().size(); i++) {
                                if (dataBase.getAllusers().get(i).getCredentials().getName()
                                        .equals(this.user.getCredentials().getName())) {
                                    dataBase.getAllusers().get(i).getPurchasedMovies()
                                            .add(currentMovieList.get(0));
                                    setTokensCountByUser(i);
                                }
                            }
                        }
                        if (user.getCredentials().getAccountType().equals("premium")) {
                            if (user.getNumFreePremiumMovies() == 0) {
                                user.setTokensCount(Math.max(user.getTokensCount() - 2, 0));
                            }
                            user.setNumFreePremiumMovies(Math.max(user
                                    .getNumFreePremiumMovies() - 1, 0));
                        } else {
                            user.setTokensCount(Math.max(user.getTokensCount() - 2, 0));
                        }
                        newuser = new User(user);
                        outCommand = new OutCommand(null, currentMovieList, newuser);
                    } else {
                        outCommand = new OutCommand("Error");
                    }

                } else {
                    outCommand = new OutCommand("Error");
                }
            } else {
                outCommand = new OutCommand("Error");
            }
        } else {
            outCommand = new OutCommand("Error");
        }
        output.addPOJO(outCommand);
    }

    /**
     * se adauga in lista de watch filmul ales
     *
     * @param output
     */
    public void watch(final ArrayNode output) {
        OutCommand outCommand = null;
        int ok = 0;
        this.watchOk = 0;
        this.succes = 0;

        if (user.getPurchasedMovies().size() > 0 && this.movie != null) {
            for (int i = 0; i < user.getPurchasedMovies().size(); i++) {
                if (user.getPurchasedMovies().get(i).getName().equals(this.movie)) {
                    ok = 1;
                    break;
                }
            }
            boolean haswatched = false;
            for (int k = 0; k < user.getWatchedMovies().size(); k++) {
                if (user.getWatchedMovies().get(k).getName().equals(this.movie)) {
                    haswatched = true;
                    break;
                }
            }
            if (!haswatched) {
                if (ok == 1) {
                    this.watchOk = 1;
                    if (this.user.getWatchedMovies().size() == 0) {
                        this.user.setWatchedMovies(currentMovieList);
                        for (int i = 0; i < dataBase.getAllusers().size(); i++) {
                            if (dataBase.getAllusers().get(i).getCredentials().getName()
                                    .equals(this.user.getCredentials().getName())) {
                                dataBase.getAllusers().get(i).setWatchedMovies(currentMovieList);
                                }
                            }
                        } else {
                            this.user.getWatchedMovies().add(currentMovieList.get(0));
                            for (int i = 0; i < dataBase.getAllusers().size(); i++) {
                                if (dataBase.getAllusers().get(i).getCredentials().getName()
                                        .equals(this.user.getCredentials().getName())) {
                                    dataBase.getAllusers().get(i).getWatchedMovies()
                                            .add(currentMovieList.get(0));
                                }
                            }
                        }
                        this.succes = 1;
                        newuser = new User(user);
                        outCommand = new OutCommand(null, currentMovieList, newuser);
                        output.addPOJO(outCommand);
                    } else {
                        outCommand = new OutCommand("Error");
                        output.addPOJO(outCommand);
                    }

            } else {
                this.succes = 1;
                newuser = new User(user);
                outCommand = new OutCommand(null, currentMovieList, newuser);
                output.addPOJO(outCommand);
            }
        } else {
            outCommand = new OutCommand("Error");
            output.addPOJO(outCommand);
        }

    }

    /**
     * daca filmul a fost cumparat si vazut, se poate da
     * like la film si acesta va fi adaugat in vectorul
     * asociat al utilizatorului
     *
     * @param output
     */
    public void like(final ArrayNode output) {
        this.succes = 0;
        OutCommand outCommand;
            int ok = 1;
            if (user.getPurchasedMovies().size() > 0 && user.getWatchedMovies().size() > 0
                    && this.movie != null) {
                for (int i = 0; i < user.getPurchasedMovies().size(); i++) {
                    if (user.getPurchasedMovies().get(i).getName().equals(this.movie)) {
                        if (user.getWatchedMovies().get(i).getName().equals(this.movie)) {
                            ok = 0;
                            break;
                        }
                    }
                }
                if (ok == 0) {
                    if (this.user.getLikedMovies().size() == 0) {
                        this.user.setLikedMovies(currentMovieList);
                        for (int i = 0; i < dataBase.getAllusers().size(); i++) {
                            if (dataBase.getAllusers().get(i).getCredentials().getName()
                                    .equals(this.user.getCredentials().getName())) {
                                dataBase.getAllusers().get(i).setLikedMovies(currentMovieList);
                            }
                        }
                        setNumLikes();
                    } else {
                        this.user.getLikedMovies().add(currentMovieList.get(0));
                        for (int i = 0; i < dataBase.getAllusers().size(); i++) {
                            if (dataBase.getAllusers().get(i).getCredentials().getName()
                                    .equals(this.user.getCredentials().getName())) {
                                dataBase.getAllusers().get(i).getLikedMovies()
                                        .add(currentMovieList.get(0));
                            }
                        }
                        setNumLikes();
                    }
                    this.succes = 1;
                    newuser = new User(user);
                    outCommand = new OutCommand(null, currentMovieList, newuser);

                } else {
                    outCommand = new OutCommand("Error");
                }

            } else {
                outCommand = new OutCommand("Error");
            }
        output.addPOJO(outCommand);
    }

    /**
     * Se fixeaza rating la fiecare film in functie de ce rating
     * a dat fiecare utilizator
     *
     * @param actions
     * @param output
     */
    public void rate(final Actions actions, final ArrayNode output) {
        OutCommand outCommand;
        this.succes = 0;
        if (actions.getRate() <= 5 && actions.getRate() >= 1 && this.movie != null) {
            int ok = 1;
            if (user.getPurchasedMovies().size() > 0 && user.getWatchedMovies().size() > 0) {
                for (int i = 0; i < user.getPurchasedMovies().size(); i++) {
                    if (user.getPurchasedMovies().get(i).getName().equals(this.movie)) {
                        for (int j = 0; j < user.getWatchedMovies().size(); i++) {
                            if (user.getWatchedMovies().get(i).getName().equals(this.movie)) {
                                ok = 0;
                                break;
                            }
                        }
                    }
                }
                boolean hasrated = false;
                if (ok == 0) {
                    if (user.getRatedMovies().size() == 0) {
                        this.user.setRatedMovies(currentMovieList);
                        for (int i = 0; i < dataBase.getAllusers().size(); i++) {
                            if (dataBase.getAllusers().get(i).getCredentials().getName()
                                    .equals(this.user.getCredentials().getName())) {
                                dataBase.getAllusers().get(i).setRatedMovies(currentMovieList);
                                setRatingByUser(actions, i);
                            }
                        }
                        putRating(actions);
                    } else {

                        int indexPurchased = 0;
                        for (int k = 0; k < this.user.getRatedMovies().size(); k++) {
                            if (user.getRatedMovies().get(k).getName().equals(this.movie)) {
                                hasrated = true;
                                indexPurchased = k;
                                break;
                            }
                        }
                        if (!hasrated) {
                            this.user.getRatedMovies().add(currentMovieList.get(0));
                            for (int i = 0; i < dataBase.getAllusers().size(); i++) {
                                if (dataBase.getAllusers().get(i).getCredentials().getName()
                                        .equals(this.user.getCredentials().getName())) {
                                    dataBase.getAllusers().get(i).getRatedMovies()
                                            .add(currentMovieList.get(0));
                                    setRatingByUser(actions, i);
                                    break;
                                }
                            }
                            putRating(actions);
                        } else {
                            for (int i = 0; i < rates.getUsers().size(); i++) {
                                if (rates.getUsers().get(i).getCredentials().getName()
                                        .equals(user.getCredentials().getName())) {
                                    rates.addRate(user.getCredentials().getName(),
                                            actions.getRate(), this.movie);
                                    break;
                                }
                            }

                            for (int i = 0; i < dataBase.getAllusers().size(); i++) {
                                if (dataBase.getAllusers().get(i).getCredentials().getName()
                                        .equals(this.user.getCredentials().getName())) {
                                    dataBase.getAllusers().get(i).getRatedMovies()
                                            .get(indexPurchased).setRating(actions.getRate());
                                    break;
                                }

                            }
                            putRating(actions);
                        }
                    }
                    Double ratingMovie = null;
                    int numRatingsMovie = 0;
                    int nrpurchased = 0;
                    for (int i = 0; i < dataBase.getAllmovies().size(); i++) {
                        if (dataBase.getAllmovies().get(i).getName().equals(this.movie)) {
                            Double rating = null;
                            if (!hasrated) {
                                dataBase.getAllmovies().get(i).setNumRatings(dataBase
                                        .getAllmovies().get(i).getNumRatings() + 1);
                                rating = calculateRating(this.movie);
                            } else {
                                rating = calculateNewRate(user, this.movie);
                            }

                            ratingMovie = rating;
                            numRatingsMovie = dataBase.getAllmovies().get(i).getNumRatings();
                            dataBase.getAllmovies().get(i).setRating(rating);
                            setRatingCurrentUser(rating, numRatingsMovie);
                            for (Movie value : this.currentMovieList) {
                                value.setNumLikes(dataBase.getAllmovies().get(i).getNumLikes());
                            }
                        }
                    }
                    setRatingDatabase(ratingMovie, numRatingsMovie);

                    this.succes = 1;
                    for (int i = 0; i < dataBase.getAllusers().size(); i++) {
                        if (dataBase.getAllusers().get(i).getCredentials().getName()
                                .equals(user.getCredentials().getName())) {
                            newuser = new User(dataBase.getAllusers().get(i));
                        }
                    }
                    if (currentMovieList == null) {
                        outCommand = new OutCommand(null, newuser);
                    } else {
                        outCommand = new OutCommand(null, currentMovieList, newuser);
                    }
                } else {
                    outCommand = new OutCommand("Error");
                }

            } else {
                outCommand = new OutCommand("Error");
            }
        } else {
            outCommand = new OutCommand("Error");
        }
        output.addPOJO(outCommand);
    }

    /**
     * acesta metoda pune in baza de date rating-ul si numarul de rating-uri noi
     * @param ratingMovie
     * @param numRatingsMovie
     */
    private void setRatingDatabase(final double ratingMovie, final int numRatingsMovie) {
        for (int i = 0; i < dataBase.getAllusers().size(); i++) {
            for (int j = 0; j < dataBase.getAllusers().get(i)
                    .getPurchasedMovies().size(); j++) {
                if (dataBase.getAllusers().get(i).getPurchasedMovies().get(j)
                        .getName().equals(currentMovieList.get(0).getName())) {
                    dataBase.getAllusers().get(i).getPurchasedMovies().get(j)
                            .setRating(ratingMovie);
                    dataBase.getAllusers().get(i).getPurchasedMovies().get(j)
                            .setNumRatings(numRatingsMovie);
                }
            }
            for (int j = 0; j < dataBase.getAllusers().get(i)
                    .getWatchedMovies().size(); j++) {
                if (dataBase.getAllusers().get(i).getWatchedMovies().get(j)
                        .getName().equals(currentMovieList.get(0).getName())) {
                    dataBase.getAllusers().get(i).getWatchedMovies().get(j)
                            .setRating(ratingMovie);
                    dataBase.getAllusers().get(i).getWatchedMovies().get(j)
                            .setNumRatings(numRatingsMovie);
                }
            }
            for (int j = 0; j < dataBase.getAllusers().get(i)
                    .getLikedMovies().size(); j++) {
                if (dataBase.getAllusers().get(i).getLikedMovies().get(j)
                        .getName().equals(currentMovieList.get(0).getName())) {
                    dataBase.getAllusers().get(i).getLikedMovies().get(j)
                            .setRating(ratingMovie);
                    dataBase.getAllusers().get(i).getLikedMovies().get(j)
                            .setNumRatings(numRatingsMovie);
                }
            }
            for (int j = 0; j < dataBase.getAllusers().get(i)
                    .getRatedMovies().size(); j++) {
                if (dataBase.getAllusers().get(i).getRatedMovies().get(j)
                        .getName().equals(currentMovieList.get(0).getName())) {
                    dataBase.getAllusers().get(i).getRatedMovies().get(j)
                            .setRating(ratingMovie);
                    dataBase.getAllusers().get(i).getRatedMovies().get(j)
                            .setNumRatings(numRatingsMovie);
                }
            }
        }
    }

    /**
     * metoda ajutatoare pentru a pastra cu ajutorul clasei Rates valorile
     * citite de la input pentru fiecare rating dat
     * @param actions
     */
    private void putRating(final Actions actions) {
        for (int i = 0; i < rates.getUsers().size(); i++) {
            if (rates.getUsers().get(i).getCredentials().getName()
                    .equals(user.getCredentials().getName())) {
                Movie movierated = new Movie(currentMovieList.get(0));
                rates.getUsers().get(i).getRatedMovies()
                        .add(movierated);
                rates.getUsers().get(i).getRatedMovies().get(rates
                        .getUsers().get(i).getRatedMovies().size() - 1)
                        .setRating(actions.getRate());
                break;
            }
        }
    }

    /**
     * seteaza rating-ul si pentru utilizatorul curent
     * @param ratingMovie
     * @param numRatingsMovie
     */
    private void setRatingCurrentUser(final double ratingMovie, final int numRatingsMovie) {
        for (int k = 0; k < this.user.getPurchasedMovies().size(); k++) {
            if (this.user.getPurchasedMovies().get(k).getName().equals(this.movie)) {
                this.user.getPurchasedMovies().get(k).setRating(ratingMovie);
                this.user.getPurchasedMovies().get(k).setNumRatings(numRatingsMovie);
            }
        }
        for (int k = 0; k < this.user.getWatchedMovies().size(); k++) {
            if (this.user.getWatchedMovies().get(k).getName().equals(this.movie)) {
                this.user.getWatchedMovies().get(k).setRating(ratingMovie);
                this.user.getWatchedMovies().get(k).setNumRatings(numRatingsMovie);
            }
        }
        for (int k = 0; k < this.user.getLikedMovies().size(); k++) {
            if (this.user.getLikedMovies().get(k).getName().equals(this.movie)) {
                this.user.getLikedMovies().get(k).setRating(ratingMovie);
                this.user.getLikedMovies().get(k).setNumRatings(numRatingsMovie);
            }
        }
        for (int k = 0; k < this.user.getRatedMovies().size(); k++) {
            if (this.user.getRatedMovies().get(k).getName().equals(this.movie)) {
                this.user.getRatedMovies().get(k).setRating(ratingMovie);
                this.user.getRatedMovies().get(k).setNumRatings(numRatingsMovie);
            }
        }
    }

    /**
     * metoda este apelata in momentul in care un film primeste un nou rating si
     * trebuie recalculat totalul
     * @param user
     * @param name
     * @return
     */
    private Double calculateNewRate(final User user, final String name) {
        Double rating = null;
        Double sum = 0.0;
        int num = 0;
        for (int i = 0; i < dataBase.getAllusers().size(); i++) {
            if (dataBase.getAllusers().get(i).getCredentials().getName().equals(user
                    .getCredentials().getName())) {
                for (int j = 0; j < dataBase.getAllusers().get(i).getRatedMovies().size(); j++) {
                    if (dataBase.getAllusers().get(i).getRatedMovies().get(j).getName()
                            .equals(name)) {
                        sum += dataBase.getAllusers().get(i).getRatedMovies().get(j).getRating();
                        num++;
                        break;
                    }
                }
            } else {
                for (int j = 0; j < rates.getUsers().size(); j++) {
                    if (rates.getUsers().get(j).getCredentials().getName()
                            .equals(dataBase.getAllusers().get(i).getCredentials().getName())) {
                        for (int k = 0; k < dataBase.getAllusers().get(i)
                                .getRatedMovies().size(); k++) {
                            if (dataBase.getAllusers().get(i).getRatedMovies().get(k).getName()
                                    .equals(name)) {
                                sum += rates.getUsers().get(j).getRatedMovies().get(k).getRating();
                                num++;
                                break;
                            }
                        }
                    }
                }
            }
        }
        rating = sum / num;
        return rating;
    }

    private void setRatingByUser(final Actions actions, final int i) {
        if (dataBase.getAllusers().get(i).getRatedMovies().size() > 0) {
                dataBase.getAllusers().get(i).getRatedMovies().get(dataBase.getAllusers()
                        .get(i).getRatedMovies().size() - 1).setRating(actions.getRate());

        } else {
            dataBase.getAllusers().get(i).getRatedMovies().get(0).setRating(actions.getRate());
        }
    }

    /**
     * se va schimba in baza de date curenta numarul de tokeni
     * ai utilizatorului in functie de tipul de cont
     *
     * @param i
     */
    private void setTokensCountByUser(final int i) {
        if (dataBase.getAllusers().get(i).getCredentials().getAccountType().equals("premium")) {
            if (dataBase.getAllusers().get(i).getNumFreePremiumMovies() == 0) {
                dataBase.getAllusers().get(i).setTokensCount(Math.max(dataBase
                        .getAllusers().get(i).getTokensCount() - 2, 0));
            }
            dataBase.getAllusers().get(i).setNumFreePremiumMovies(Math.max(dataBase
                    .getAllusers().get(i).getNumFreePremiumMovies() - 1, 0));
        } else {
            dataBase.getAllusers().get(i).setTokensCount(Math.max(dataBase
                    .getAllusers().get(i).getTokensCount() - 2, 0));
        }
    }

    /**
     * se seteaza in baza date actuala numarul de aprecieri al
     * filmului
     */
    private void setNumLikes() {
        for (int i = 0; i < dataBase.getAllmovies().size(); i++) {
            if (dataBase.getAllmovies().get(i).getName().equals(this.movie)) {
                dataBase.getAllmovies().get(i).setNumLikes(dataBase
                        .getAllmovies().get(i).getNumLikes() + 1);
                for (Movie value : this.currentMovieList) {
                    value.setNumLikes(dataBase.getAllmovies().get(i).getNumLikes());
                }
            }
        }
    }

    /**
     * se verifica daca tara lui este o tara din care nu se
     * poate vedea filmul  repectiv
     */
    private boolean countryInvertCheck(final CurrentUser currentUser, final int index) {
        for (int j = 0; j < dataBase.getAllmovies().get(index).getCountriesBanned().size(); j++) {
            if (currentUser.user().getCredentials().getCountry().contains(dataBase
                    .getAllmovies().get(index).getCountriesBanned().get(j))) {
                return false;
            }
        }
        return true;
    }

    /**
     * se calculeaza rating-ul
     */
    private Double calculateRating(final String name) {
        int ratings = 0, numrating = 0;
        double total = 0.0;
        for (int i = 0; i < dataBase.getAllusers().size(); i++) {
            for (int j = 0; j < dataBase.getAllusers().get(i).getRatedMovies().size(); j++) {
                if (dataBase.getAllusers().get(i).getRatedMovies().get(j).getName().equals(name)) {
                    ratings += dataBase.getAllusers().get(i).getRatedMovies().get(j).getRating();
                    numrating++;
                }
            }
        }
        if (numrating != 0) {
            total = (double) ratings / numrating;
        }
        return total;
    }

    /**
     * se sorteaza dupa tara pe care o are utilizatorul
     *
     * @param currentUser
     * @return
     */
    private ArrayList<Movie> sortByCountry(final CurrentUser currentUser) {
        ArrayList<Movie> allmovies = new ArrayList<>();
        for (int i = 0; i < dataBase.getAllmovies().size(); i++) {
            if (countryInvertCheck(currentUser, i)) {
                Movie movie = new Movie(dataBase.getAllmovies().get(i));
                allmovies.add(movie);
            }
        }
        return allmovies;
    }

    /**
     * metoda pentru actiunea de abonare
     * @param actions
     * @param output
     */
    public void executeSubcribe(final Actions actions, final ArrayNode output) {
        OutCommand outCommand;
        if (this.getCurrentPage.getCurrent().equals("see details")) {
            boolean ok = false;
            boolean hasgenre = false;
            for (int i = 0; i < dataBase.getAllmovies().size(); i++) {
                if (dataBase.getAllmovies().get(i).getName().equals(this.movie)) {
                    for (int j = 0; j < dataBase.getAllmovies().get(i).getGenres().size(); j++) {
                        if (actions.getSubscribedGenre().equals(dataBase
                                .getAllmovies().get(i).getGenres().get(j))) {
                            for (int k = 0; k < user.getSubscribe().size(); k++) {
                                if (user.getSubscribe().get(k)
                                        .equals(actions.getSubscribedGenre())) {
                                    hasgenre = true;
                                    break;
                                }
                            }
                        }
                    }
                    if (!hasgenre) {
                        ok = true;
                    } else {
                        outCommand = new OutCommand("Error");
                        output.addPOJO(outCommand);
                        return;
                    }
                }
            }
            if (ok) {
                user.getSubscribe().add(actions.getSubscribedGenre());
                for (int i = 0; i < dataBase.getAllusers().size(); i++) {
                    if (dataBase.getAllusers().get(i).getCredentials().getName()
                            .equals(user.getCredentials().getName())) {
                        dataBase.getAllusers().get(i).getSubscribe().add(actions
                                .getSubscribedGenre());
                    }
                }
            } else {
                outCommand = new OutCommand("Error");
                output.addPOJO(outCommand);
                return;
            }
        } else {
            outCommand = new OutCommand("Error");
            output.addPOJO(outCommand);
            return;
        }
    }

    /**
     * metoda pentru a adauga un nou film sau a sterge unul din baza de date
     * @param actions
     * @param output
     * @param currentUser
     */
    public void executeDatabase(final Actions actions, final ArrayNode output,
                                final CurrentUser currentUser) {
        if (actions.getFeature().equals("add")) {
            add(actions, output);
        } else if (actions.getFeature().equals("delete")) {
            delete(actions, output, currentUser);
        }
    }

    /**
     * adaugarea unui nou film in baza de date
     * @param actions
     * @param output
     */
    private void add(final Actions actions, final ArrayNode output) {
        for (int i = 0; i < dataBase.getAllmovies().size(); i++) {
            if (dataBase.getAllmovies().get(i).getName().equals(actions.getAddedMovie()
                    .getName())) {
                OutCommand outCommand = new OutCommand("Error");
                output.addPOJO(outCommand);
                return;
            }
        }
        dataBase.addMovie(actions.getAddedMovie());
        for (int i = 0; i < dataBase.getAllusers().size(); i++) {
            boolean ok = false;
            for (int j = 0; j < actions.getAddedMovie().getCountriesBanned().size(); j++) {
                if (dataBase.getAllusers().get(i).getCredentials().getCountry()
                        .contains(actions.getAddedMovie().getCountriesBanned().get(j))) {
                    ok = true;
                    break;
                }
            }
            if (!ok) {
                boolean finish = true;
                for (int j = 0; j < dataBase.getAllusers().get(i).getSubscribe().size(); j++) {
                    for (int k = 0; k < actions.getAddedMovie().getGenres().size(); k++) {
                        if (dataBase.getAllusers().get(i).getSubscribe().get(j)
                                .contains(actions.getAddedMovie().getGenres().get(k))) {
                            Notifications notification = new Notifications(actions
                                    .getAddedMovie().getName(), "ADD");
                            dataBase.getAllusers().get(i).getNotifications().add(notification);
                            this.user.getNotifications().add(notification);
                            finish = false;
                            break;
                        }
                    }
                    if (!finish) {
                        break;
                    }
                }
            }
        }
    }
    private void delete(final Actions actions, final ArrayNode output,
                        final CurrentUser currentUser) {
        boolean ok = false;
        for (int i = 0; i < dataBase.getAllmovies().size(); i++) {
            if (dataBase.getAllmovies().get(i).getName().equals(actions.getDeletedMovie())) {
                ok = true;
                break;
            }
        }
        if (!ok) {
            OutCommand outCommand = new OutCommand("Error");
            output.addPOJO(outCommand);
            return;
        } else {
            Movie moviecop = null;
            for (int j = 0; j < dataBase.getAllmovies().size(); j++) {
                if (dataBase.getAllmovies().get(j).getName().equals(actions
                        .getDeletedMovie())) {
                    moviecop = new Movie(dataBase.getAllmovies().get(j));
                    break;
                }
            }
            dataBase.getAllmovies().remove(moviecop);
            for (int i = 0; i < dataBase.getAllusers().size(); i++) {
                for (int j = 0; j < dataBase.getAllusers().get(i).getPurchasedMovies()
                        .size(); j++) {
                    if (dataBase.getAllusers().get(i).getPurchasedMovies().get(j).getName()
                            .equals(actions.getDeletedMovie())) {
                        Notifications notification = new Notifications(actions
                                .getDeletedMovie(),
                                "DELETE");
                        dataBase.getAllusers().get(i).getNotifications().add(notification);
                        this.user.getNotifications().add(notification);
                        if (!dataBase.getAllusers().get(i).getCredentials().getName()
                                .equals(currentUser.user().getCredentials().getName())) {
                            if (dataBase.getAllusers().get(i).getCredentials()
                                    .getAccountType().equals("premium")) {
                                dataBase.getAllusers().get(i).setNumFreePremiumMovies(dataBase
                                        .getAllusers().get(i).getNumFreePremiumMovies() + 1);
                            } else {
                                dataBase.getAllusers().get(i).setTokensCount(dataBase
                                        .getAllusers()
                                        .get(i).getTokensCount() + 2);
                            }
                        }
                        dataBase.getAllusers().get(i).getPurchasedMovies()
                                .remove(moviecop);
                        break;
                    }
                }
                removeMovie(moviecop, actions);
                if (dataBase.getAllusers().get(i).getCredentials().getName()
                        .equals(this.user.getCredentials().getName())) {
                    if (this.user.getCredentials().getAccountType().equals("premium")) {
                        this.user.setNumFreePremiumMovies(this.user.getNumFreePremiumMovies()
                                + 1);
                        dataBase.getAllusers().get(i).setNumFreePremiumMovies(dataBase
                                .getAllusers().get(i).getNumFreePremiumMovies() + 1);
                    } else {
                        this.user.setTokensCount(this.user.getTokensCount() + 2);
                        dataBase.getAllusers().get(i).setTokensCount(dataBase.getAllusers()
                                .get(i).getTokensCount() + 2);
                    }
                }
            }
        }
    }
    /**
     * sterge filmul din rubricile de filme care au fost vazute,
     * apreciate si evaluate
     * @param moviecop
     * @param actions
     */
    private void removeMovie(final Movie moviecop, final Actions actions) {
        for (int i = 0; i < dataBase.getAllusers().size(); i++) {
            for (int j = 0; j < dataBase.getAllusers().get(i).getWatchedMovies()
                    .size(); j++) {
                if (dataBase.getAllusers().get(i).getWatchedMovies().get(j).getName()
                        .equals(actions.getDeletedMovie())) {
                    dataBase.getAllusers().get(i).getWatchedMovies()
                            .remove(moviecop);
                    break;
                }
            }
            for (int j = 0; j < dataBase.getAllusers().get(i).getLikedMovies()
                    .size(); j++) {
                if (dataBase.getAllusers().get(i).getLikedMovies().get(j).getName()
                        .equals(actions.getDeletedMovie())) {
                    dataBase.getAllusers().get(i).getLikedMovies()
                            .remove(moviecop);
                    break;
                }
            }
            for (int j = 0; j < dataBase.getAllusers().get(i).getRatedMovies()
                    .size(); j++) {
                if (dataBase.getAllusers().get(i).getRatedMovies().get(j).getName()
                        .equals(actions.getDeletedMovie())) {
                    dataBase.getAllusers().get(i).getRatedMovies()
                            .remove(moviecop);
                    break;
                }
            }
        }
    }
    /**
     * @return
     */
    public ArrayList<Movie> getCurrentMovieList() {
        return currentMovieList;
    }

    /**
     * @return
     */
    public DataBase getDataBase() {
        return dataBase;
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
    public User getUser() {
        return user;
    }

    /**
     * @return
     */
    public GetCurrentPage getGetCurrentPage() {
        return getCurrentPage;
    }

    /**
     * @return
     */
    public int getSucces() {
        return succes;
    }

    /**
     * @return
     */
    public String getMovie() {
        return movie;
    }

    /**
     * @param movie
     */
    public void setMovie(final String movie) {
        this.movie = movie;
    }

    /**
     * @return
     */
    public int getSuccespage() {
        return succespage;
    }

    /**
     * @return
     */
    public MementoUse getMemento() {
        return memento;
    }

    /**
     * @param memento
     */
    public void setMemento(final MementoUse memento) {
        this.memento = memento;
    }

    /**
     * @return
     */
    public Rates getRates() {
        return rates;
    }

    /**
     * @param rates
     */
    public void setRates(final Rates rates) {
        this.rates = rates;
    }
}

