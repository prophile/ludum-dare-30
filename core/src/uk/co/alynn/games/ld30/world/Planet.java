package uk.co.alynn.games.ld30.world;

public class Planet {
    private final float m_x, m_y;
    private final int m_health;
    private final String m_sprite;
    
    public Planet(float x, float y, int health, String sprite) {
        m_x = x;
        m_y = y;
        m_health = health;
        m_sprite = sprite;
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
        return m_sprite;
    }
}
