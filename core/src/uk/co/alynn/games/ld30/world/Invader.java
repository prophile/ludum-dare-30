package uk.co.alynn.games.ld30.world;

public class Invader implements Adversary {

    private final float m_x, m_y;
    private final float m_targetX, m_targetY;
    
    public Invader(float x, float y, float targetX, float targetY) {
        m_x = x;
        m_y = y;
        m_targetX = targetX;
        m_targetY = targetY;
    }
    
    @Override
    public String getImage() {
        return "invader";
    }

    @Override
    public float getX() {
        return m_x;
    }

    @Override
    public float getY() {
        return m_y;
    }

    @Override
    public Adversary update(float dt) {
        float offX = m_targetX - m_x;
        float offY = m_targetY - m_y;
        float distance = (float) Math.hypot(offX, offY);
        if (distance > Constants.INVADER_HOLDOFF) {
            float unitX = offX/distance;
            float unitY = offY/distance;
            float newX = m_x + unitX*dt*Constants.SPEED_INVADER;
            float newY = m_y + unitY*dt*Constants.SPEED_INVADER;
            return new Invader(newX, newY, m_targetX, m_targetY);
        } else {
            return this;
        }
    }

    @Override
    public Adversary hitPlayer(Runnable terminateGame) {
        return this;
    }

    @Override
    public Adversary hitBullet() {
        return this;
    }

    @Override
    public float getHeading() {
        return 0.0f;
    }

}
