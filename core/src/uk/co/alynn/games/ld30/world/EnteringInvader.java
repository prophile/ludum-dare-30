package uk.co.alynn.games.ld30.world;

public class EnteringInvader extends Invader {
    private final float m_targetX, m_targetY;
    
    public EnteringInvader(float x, float y, float targetX, float targetY) {
        super(x, y);
        m_targetX = targetX;
        m_targetY = targetY;
    }
    
    @Override
    public Adversary update(float dt) {
        float offX = m_targetX - getX();
        float offY = m_targetY - getY();
        float distance = (float) Math.hypot(offX, offY);
        if (distance > Constants.INVADER_HOLDOFF) {
            float unitX = offX/distance;
            float unitY = offY/distance;
            float newX = getX() + unitX*dt*Constants.SPEED_INVADER;
            float newY = getY() + unitY*dt*Constants.SPEED_INVADER;
            return new EnteringInvader(newX, newY, m_targetX, m_targetY);
        } else {
            return new HoldoffInvader(getX(), getY(), Constants.INVADER_HOLDOFF_TIME);
        }
    }
}
