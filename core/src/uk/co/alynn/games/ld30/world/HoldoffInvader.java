package uk.co.alynn.games.ld30.world;

import java.util.Iterator;

import uk.co.alynn.games.ld30.IterTools;

public class HoldoffInvader extends Invader {
    private final float m_remainingHoldoff;
    private final float m_targetX, m_targetY;
    
    public HoldoffInvader(float x, float y, float remainingHoldoff, float targetX, float targetY) {
        super(x, y);
        m_remainingHoldoff = remainingHoldoff;
        m_targetX = targetX;
        m_targetY = targetY;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public Iterator<Adversary> update(float dt) {
        float newHoldoff = m_remainingHoldoff - dt;
        if (newHoldoff < 0.0f) {
            return IterTools.just(new ShootingInvader(getX(), getY(), 0.0f, m_targetX, m_targetY));
        } else {
            return IterTools.just(new HoldoffInvader(getX(), getY(), newHoldoff, m_targetX, m_targetY));
        }
    }
}
