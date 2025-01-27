package decide.application;

import decide.core.Decider;

import java.io.IOException;

public class DeciderApplication {
    public static void main(String[] args) throws IOException {
        Decider decider = new Decider();
        decider.run();
    }
}
