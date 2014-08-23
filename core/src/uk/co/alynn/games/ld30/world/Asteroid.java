package uk.co.alynn.games.ld30.world;

public class Asteroid implements Adversary {
    private final float m_x, m_y;
    private final float m_heading;
    
    public Asteroid(float x, float y, float heading) {
        m_x = x;
        m_y = y;
        m_heading = heading;
    }
    
    @Override
    public String getImage() {
        return "asteroid";
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
        float x_ = (float) (m_x + Constants.ASTEROID_SPEED*dt*Math.cos(m_heading));
        float y_ = (float) (m_y + Constants.ASTEROID_SPEED*dt*Math.sin(m_heading));
        return new Asteroid(x_, y_, m_heading);
    }

    @Override
    public Adversary hitPlayer() {
        return this;
    }

    @Override
    public Adversary hitBullet() {
        return this;
    }

    @Override
    public float getHeading() {
        return m_heading;
    }

}
