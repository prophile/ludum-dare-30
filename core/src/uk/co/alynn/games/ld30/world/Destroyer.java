package uk.co.alynn.games.ld30.world;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import uk.co.alynn.games.ld30.AudioEngine;

public class Destroyer implements Adversary {
    private final float m_x, m_y;
    private final float m_playerX, m_playerY;
    private final float m_cooldown;
    
    public Destroyer(float x, float y, float px, float py, float cooldown) {
        m_x = x;
        m_y = y;
        m_playerX = px;
        m_playerY = py;
        m_cooldown = cooldown;
    }

    @Override
    public String getImage() {
        return "destroyer";
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
    public Iterator<Adversary> update(float dt) {
        List<Adversary> nextAdversaries = new ArrayList<Adversary>();
        float diffX = m_playerX - getX();
        float diffY = m_playerY - getY();
        float distance = (float) Math.hypot(diffX, diffY);
        diffX /= distance;
        diffY /= distance;
        float x_ = getX() + diffX*dt*Constants.SPEED_DESTROYER;
        float y_ = getY() + diffY*dt*Constants.SPEED_DESTROYER;
        float cooldown_ = m_cooldown - dt;
        if (cooldown_ <= 0) {
            cooldown_ = Constants.DESTROYER_LATER_COOLDOWN;
            AudioEngine.get().play("invader-shoot");
            nextAdversaries.add(new InvaderBullet(getX(), getY(), getHeading()));
        }
        nextAdversaries.add(new Destroyer(x_, y_, m_playerX, m_playerY, cooldown_));
        return nextAdversaries.iterator();
    }

    @Override
    public Adversary hitPlayer(Runnable terminateGame) {
        terminateGame.run();
        return null;
    }

    @Override
    public Adversary hitBullet() {
        AudioEngine.get().play("hit");
        return new Wreckage(getX(), getY(), 0.0f);
    }

    @Override
    public float getHeading() {
        return (float) Math.atan2(m_playerY - getY(), m_playerX - getX());
    }

    @Override
    public Adversary hitPlanet(DamagePlanet damage) {
        return this;
    }

    @Override
    public Adversary seePlayer(float x, float y) {
        return new Destroyer(getX(), getY(), x, y, m_cooldown);
    }
    
}
