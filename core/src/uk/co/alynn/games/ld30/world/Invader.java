package uk.co.alynn.games.ld30.world;

public abstract class Invader implements Adversary {

    private final float m_x, m_y;
    
    public Invader(float x, float y) {
        m_x = x;
        m_y = y;
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
    
    abstract public Adversary update(float dt);

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
