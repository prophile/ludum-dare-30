package uk.co.alynn.games.ld30.world;

import com.badlogic.gdx.math.Vector2;

public class PlayerShip {
    private final float m_x, m_y;
    private final float m_heading;

    public PlayerShip() {
        this(0.0f, 0.0f, 0.0f);
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
    
    public boolean isPhantom() {
        return false;
    }
    
    public float getHeading() {
        return m_heading;
    }
    
    public PlayerShip update(float dt) {
        float xPrime = m_x;
        float yPrime = m_y;
        xPrime += dt*Constants.SPEED*Math.cos(m_heading);
        yPrime += dt*Constants.SPEED*Math.sin(m_heading);
        xPrime = wrap(xPrime, Constants.STANDARD_RES_WIDTH);
        yPrime = wrap(yPrime, Constants.STANDARD_RES_HEIGHT);
        return new PlayerShip(xPrime, yPrime, m_heading);
    }
    
    public Vector2 getBindPoint() {
        return null;
    }
    
    public PlayerShip bind(float x, float y, int direction) {
        return new BoundPlayerShip(getX(), getY(), getHeading(), x, y, direction);
    }
    
    public PlayerShip unbind() {
        return this;
    }

    private static float wrap(float newValue, float maximum) {
        while (newValue > (maximum + Constants.WRAP_BUFFER)) {
            newValue -= (maximum + 2*Constants.WRAP_BUFFER);
        }
        while (newValue < -Constants.WRAP_BUFFER) {
            newValue += (maximum + 2*Constants.WRAP_BUFFER);
        }
        return newValue;
    }
}
