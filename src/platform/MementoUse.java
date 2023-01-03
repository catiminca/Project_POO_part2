package platform;

import java.util.ArrayList;
import java.util.List;

public class MementoUse {
    private List<Memento> memetoList = new ArrayList<>();

    /**
     * adaugare a noii stari
     * @param state
     */
    public void add(final Memento state) {
        memetoList.add(state);
    }

    /**
     * @return
     */
    public Memento getMem() {
        int index = memetoList.size() - 1;
        return memetoList.get(index);
    }
    public Memento getByIndex(int index) {
        //int index = memetoList.size() - 1;
        return memetoList.get(index);
    }

    /**
     * @param memento
     */
    public void remove(final Memento memento) {
        memetoList.remove(memento);
    }

    public int size() {
        return memetoList.size();
    }
}

