package uk.co.alynn.games.ld30.world;

public final class Bullet {
    private final float m_x;
    private final float m_y;
    private final float m_heading;
    private final float m_flightTime;

    public Bullet(float x, float y, float heading, float flightTime) {
        m_x = x;
        m_y = y;
        m_heading = heading;
        m_flightTime = flightTime;
    }
    
    public float getX() {
        return m_x;
    }
    
    public float getY() {
        return m_y;
    }
    
    public float getHeading() {
        return m_heading;
    }
    
    public Bullet update(float dt) {
        float x_ = (float) (m_x + Constants.SPEED_BULLET*dt*Math.cos(m_heading));
        float y_ = (float) (m_y + Constants.SPEED_BULLET*dt*Math.sin(m_heading));
        if (m_flightTime > Constants.BULLET_FLIGHT_TIME) {
            return null;
        }
        return new Bullet(x_, y_, m_heading, m_flightTime + dt);
    }
}
