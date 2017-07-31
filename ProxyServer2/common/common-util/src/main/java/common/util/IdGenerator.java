package common.util;

import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {

    private final AtomicInteger idGenerator;
    private final int step;

    public IdGenerator(int step) {
        this.step = step;
        this.idGenerator = new AtomicInteger((int)(Math.random()*step));
    }
    public int generateId() {
        return idGenerator.addAndGet(step);
    }

}
