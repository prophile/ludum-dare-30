package uk.co.alynn.games.ld30.world;

public class PlayerShip {
    private final float m_x, m_y;
    private final float m_heading;

    public PlayerShip() {
        m_x = 0.0f;
        m_y = 0.0f;
        m_heading = 0.0f;
    }
    
    public PlayerShip(float x, float y, float heading) {
        m_x = x;
        m_y = y;
        m_heading = heading;
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
    
    public PlayerShip update(float dt) {
        float xPrime = m_x;
        float yPrime = m_y;
        xPrime += dt*Constants.SPEED*Math.sin(m_heading);
        yPrime += dt*Constants.SPEED*Math.cos(m_heading);
        return new PlayerShip(xPrime, yPrime, m_heading);
    }
}
