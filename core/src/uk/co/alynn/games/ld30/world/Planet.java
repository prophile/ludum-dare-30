package uk.co.alynn.games.ld30.world;

public class Planet {
    private final float m_x, m_y;
    
    public Planet(float x, float y) {
        m_x = x;
        m_y = y;
    }
    
    public float getX() {
        return m_x;
    }
    
    public float getY() {
        return m_y;
    }
    
    public String getSprite() {
        return "planet-1";
    }
}
