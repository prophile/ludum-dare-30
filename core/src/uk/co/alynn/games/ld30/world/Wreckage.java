package uk.co.alynn.games.ld30.world;

import java.util.Iterator;

import uk.co.alynn.games.ld30.IterTools;

public class Wreckage implements Adversary {
    private final float m_x, m_y;
    private final float m_age;
    
    public Wreckage(float x, float y, float age) {
        m_x = x;
        m_y = y;
        m_age = age;
    }
    
    @Override
    public String getImage() {
        return "bang";
    }

    @Override
    public float getX() {
        return m_x;
    }

    @Override
    public float getY() {
        return m_y;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Iterator<Adversary> update(float dt) {
        if (m_age > 0.4f) {
            return IterTools.zero();
        }
        return IterTools.just(new Wreckage(m_x, m_y, m_age + dt));
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
        return 0;
    }

    @Override
    public Adversary hitPlanet(DamagePlanet damage) {
        return this;
    }

}
