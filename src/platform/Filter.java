package platform;

import sorts.ActorsSort;
import sorts.GenreSort;
import sorts.Sort;

import java.util.ArrayList;

public class Filter {
    private Sort sort;
    private Contains contains;
    public Filter() {
    }
    /**
     */
    public Sort getSort() {
        return this.sort;
    }

    /**
     * @return
     */
    public Contains getContains() {
        return this.contains;
    }

    /**
     * functie pentru a verifica daca sortarea se va face dupa actori, genuri
     * de film sau ambele
     * @param movies
     */
    public ArrayList<Movie> sortedByContains(final ArrayList<Movie> movies) {
        ArrayList<Movie> filtered = new ArrayList<>();
        if (this.contains.getActors() != null && this.contains.getGenre() != null) {
            filtered = this.contains.sortByContains(movies);
        } else if (this.contains.getActors() != null) {
            ActorsSort v = (ActorsSort) this.contains.sortByActors(movies);
            filtered = v.getTotalMovies();
        } else if (this.contains.getGenre() != null) {
            GenreSort genreSort = (GenreSort) this.contains.sortByGenre(movies);
            filtered = genreSort.getTotalMovies();
        }
        return filtered;
    }

    /**
     * functie cu ajutorul careia apelez functia care sorteaza atat dupa
     * nota pe care o are cat si dupa durata sa
     * @param movies
     */
    public ArrayList<Movie> sortByBoth(final ArrayList<Movie> movies) {
        ArrayList<Movie> sortedMovies;
        sortedMovies = this.sort.sort(movies);
        return sortedMovies;
    }

    /**
     * se cheama functia care sorteaza dupa evaluarea(rating) pe care o au
     * filmele
     * @param movies
     * @param rating
     * @return
     */
    public ArrayList<Movie> sortByRating(final ArrayList<Movie> movies, final String rating) {
        ArrayList<Movie> sortedMovies;
        sortedMovies = this.sort.sortByRating(movies, rating);
        return sortedMovies;
    }

    /**
     * este apelata functia care sorteaza dupa durata filmelor
     * @param movies
     * @param duration
     */
    public void sortByDuration(final ArrayList<Movie> movies, final String duration) {
        this.sort.sortByDuration(movies, duration);
    }
}
