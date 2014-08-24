package uk.co.alynn.games.ld30.world;

import java.util.Iterator;

import uk.co.alynn.games.ld30.IterTools;

public class HoldoffInvader extends Invader {
    private final float m_remainingHoldoff;
    
    public HoldoffInvader(float x, float y, float remainingHoldoff) {
        super(x, y);
        m_remainingHoldoff = remainingHoldoff;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public Iterator<Adversary> update(float dt) {
        float newHoldoff = m_remainingHoldoff - dt;
        if (newHoldoff < 0.0f) {
            return IterTools.just(new ShootingInvader(getX(), getY(), 0.0f));
        } else {
            return IterTools.just(new HoldoffInvader(getX(), getY(), newHoldoff));
        }
    }
}
