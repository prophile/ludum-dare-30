package uk.co.alynn.games.ld30.world;

import java.util.Iterator;

import uk.co.alynn.games.ld30.IterTools;

public class InvaderBullet implements Adversary {
    private final float m_x, m_y, m_heading;
    
    public InvaderBullet(float x, float y, float heading) {
        m_x = x;
        m_y = y;
        m_heading = heading;
    }
    
    @Override
    public String getImage() {
        return "target";
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
        float x_ = (float) (m_x + Constants.SPEED_BULLET*dt*Math.cos(m_heading));
        float y_ = (float) (m_y + Constants.SPEED_BULLET*dt*Math.sin(m_heading));
        return IterTools.just(new InvaderBullet(x_, y_, m_heading));
    }

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
        return 0;
    }

}
