package domain.support;

public class IDGenerator {
    private static IDGenerator instance = null;

    public static IDGenerator getInstance() {
        if (instance == null) {
            instance = new IDGenerator();
        }

        return instance;
    }

    private int nextId = 0;

    private IDGenerator () {};

    public int nextId() {
        return this.nextId++;
    }
}
