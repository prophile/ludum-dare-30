package uk.co.alynn.games.ld30.world;

import java.util.Iterator;

import uk.co.alynn.games.ld30.AudioEngine;
import uk.co.alynn.games.ld30.IterTools;

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

    @SuppressWarnings("unchecked")
    @Override
    public Iterator<Adversary> update(float dt) {
        float x_ = (float) (m_x + Constants.ASTEROID_SPEED*dt*Math.cos(m_heading));
        float y_ = (float) (m_y + Constants.ASTEROID_SPEED*dt*Math.sin(m_heading));
        return IterTools.just(new Asteroid(x_, y_, m_heading));
    }

    @Override
    public Adversary hitPlayer(Runnable terminateGame) {
        terminateGame.run();
        return null;
    }

    @Override
    public Adversary hitBullet() {
        AudioEngine.get().play("hit");
        return null;
    }

    @Override
    public float getHeading() {
        return m_heading;
    }

    @Override
    public Adversary hitPlanet(DamagePlanet damage) {
        return this;
    }

}
