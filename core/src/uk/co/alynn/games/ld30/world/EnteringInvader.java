package uk.co.alynn.games.ld30.world;

import java.util.Iterator;

import uk.co.alynn.games.ld30.IterTools;

public class EnteringInvader extends Invader {
    private final float m_targetX, m_targetY;
    
    public EnteringInvader(float x, float y, float targetX, float targetY) {
        super(x, y);
        m_targetX = targetX;
        m_targetY = targetY;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public Iterator<Adversary> update(float dt) {
        float offX = m_targetX - getX();
        float offY = m_targetY - getY();
        float distance = (float) Math.hypot(offX, offY);
        if (distance > Constants.INVADER_HOLDOFF) {
            float unitX = offX/distance;
            float unitY = offY/distance;
            float newX = getX() + unitX*dt*Constants.SPEED_INVADER;
            float newY = getY() + unitY*dt*Constants.SPEED_INVADER;
            return IterTools.just(new EnteringInvader(newX, newY, m_targetX, m_targetY));
        } else {
            return IterTools.just(new HoldoffInvader(getX(), getY(), Constants.INVADER_HOLDOFF_TIME));
        }
    }
}
