package Players;
import Structures.Color;

import java.util.Random;

public class PlayerAIRandom extends PlayerAI {
    private final long seed;
    private final Random generator;

    public PlayerAIRandom(Color c) {
        seed = System.currentTimeMillis();
        this.generator = new Random(seed);
        difficultyLevel = 0;
        col = c;
    }


    // This constructor will allow to do reproductible tests
    /*public PlayerAIRandom(long seed) {
        this.seed = seed;
        this.generator = new Random(seed);
    }*/
}
