package uk.co.alynn.games.ld30.world;

public class Planet {
    private final float m_x, m_y;
    private final int m_health;
    
    public Planet(float x, float y, int health) {
        m_x = x;
        m_y = y;
        m_health = health;
    }
    
    public float getX() {
        return m_x;
    }
    
    public float getY() {
        return m_y;
    }
    
    public int getHealth() {
        return m_health;
    }
    
    public String getSprite() {
        return "planet-1";
    }
}
