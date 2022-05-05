package Players;
import java.util.Random;

public class PlayerAIRandom extends PlayerAI {
    private final long seed;
    private final Random generator;

    public PlayerAIRandom() {
        seed = System.currentTimeMillis();
        this.generator = new Random(seed);
    }


    // This constructor will allow to do reproductible tests
    public PlayerAIRandom(long seed) {
        this.seed = seed;
        this.generator = new Random(seed);
    }
}
