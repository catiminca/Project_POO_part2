package platform;

import java.util.ArrayList;

public class Rates {
    private ArrayList<User> users;
    private ArrayList<Double> currentRate;
    public Rates(final ArrayList<User> names) {
        this.users = new ArrayList<>();
        for (User user : names) {
                User usercop = new User(user);
                this.users.add(usercop);
        }
    }

    /**
     * se adauga un nou rating
     * @param username
     * @param rating
     * @param name
     */
    public void addRate(final String username, final Double rating, final String name) {
        for (User user : users) {
            if (user.getCredentials().getName().equals(username)) {
                for (int j = 0; j < user.getRatedMovies().size(); j++) {
                    if (user.getRatedMovies().get(j).getName().equals(name)) {
                        user.getRatedMovies().get(j).setRating(rating);
                    }
                }
            }
        }
    }

    /**
     * @return
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     * @param users
     */
    public void setUsers(final ArrayList<User> users) {
        this.users = users;
    }

    /**
     * @return
     */
    public ArrayList<Double> getCurrentRate() {
        return currentRate;
    }

    /**
     * @param currentRate
     */
    public void setCurrentRate(final ArrayList<Double> currentRate) {
        this.currentRate = currentRate;
    }
}
