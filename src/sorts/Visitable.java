package sorts;

public interface Visitable {
    /**
     * metoda accept necesara design pattern-ului visitor
     * @param vistor
     */
    void accept(Visitor vistor);
}
