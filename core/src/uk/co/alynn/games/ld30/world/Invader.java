package uk.co.alynn.games.ld30.world;

import java.util.Iterator;

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
    
    abstract public Iterator<Adversary> update(float dt);

    @Override
    public Adversary hitPlayer(Runnable terminateGame) {
        terminateGame.run();
        return null;
    }

    @Override
    public Adversary hitBullet() {
        return null;
    }

    @Override
    public float getHeading() {
        return 0.0f;
    }

}
