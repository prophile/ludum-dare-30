package uk.co.alynn.games.ld30.world;

public class HoldoffInvader extends Invader {
    private final float m_remainingHoldoff;
    
    public HoldoffInvader(float x, float y, float remainingHoldoff) {
        super(x, y);
        m_remainingHoldoff = remainingHoldoff;
    }
    
    @Override
    public Adversary update(float dt) {
        float newHoldoff = m_remainingHoldoff - dt;
        if (newHoldoff < 0.0f) {
            return null;
        } else {
            return new HoldoffInvader(getX(), getY(), newHoldoff);
        }
    }
}
