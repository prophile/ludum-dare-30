package uk.co.alynn.games.ld30;

import java.util.List;
import uk.co.alynn.games.ld30.world.Planet;
import uk.co.alynn.games.ld30.world.Adversary;

public class SuperimposedWave extends Wave {
    
    private final Wave m_left;
    private final Wave m_right;
    
    public SuperimposedWave(Wave left, Wave right) {
        m_left = left;
        m_right = right;
    }
    
    @Override
    public boolean dispatch(List<Planet> planets, List<Adversary> adversaries, int tickID) {
        boolean ok = true;
        ok = m_left.dispatch(planets, adversaries, tickID) && ok;
        ok = m_right.dispatch(planets, adversaries, tickID) && ok;
        return ok;
    }

    @Override
    protected boolean tick(int tick) {
        throw new RuntimeException("Cannot tick a superwave");
    }
}

