package sorts;

public interface Visitor {
    /**
     * metoda visit pentru sortarea dupa actori
     * @param actorsSort
     */
    void visit(ActorsSort actorsSort);

    /**
     * metoda visit pentru sortarea dupa genuri
     * @param genreSort
     */
    void visit(GenreSort genreSort);
}
