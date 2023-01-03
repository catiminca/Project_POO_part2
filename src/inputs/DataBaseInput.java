package inputs;

import java.util.ArrayList;

/**
 * database input
 */
public final class DataBaseInput {
    private ArrayList<UserInput> users;
    private ArrayList<MovieInput> movies;
    private ArrayList<ActionInput> actions;

    private static DataBaseInput instance = null;

    private DataBaseInput() { }

    /**
     * Singleton pentru database
     * @return
     */
    public static DataBaseInput getInstance() {
        if (instance == null) {
            instance = new DataBaseInput();
        }
        return instance;
    }

    public ArrayList<UserInput> getUsers() {
        return this.users;
    }

    public void setUsers(final ArrayList<UserInput> users) {
        this.users = users;
    }

    public ArrayList<MovieInput> getMovies() {
        return this.movies;
    }

    public void setMovies(final ArrayList<MovieInput> movies) {
        this.movies = movies;
    }

    public ArrayList<ActionInput> getActions() {
        return this.actions;
    }

    public void setActions(final ArrayList<ActionInput> actions) {
        this.actions = actions;
    }
}

