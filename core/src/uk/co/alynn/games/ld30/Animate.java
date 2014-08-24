package uk.co.alynn.games.ld30;

public final class Animate {
    private static float s_time = 0.0f;
    
    public static void update(float dt) {
        s_time += dt;
    }
    
    public static String animate(String base, int nframes, float period) {
        int bees = (int)(s_time / period);
        int frame = bees % nframes;
        return base + "-" + frame;
    }
}
