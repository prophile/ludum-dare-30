package uk.co.alynn.games.ld30.world;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import uk.co.alynn.games.ld30.AudioEngine;
import uk.co.alynn.games.ld30.IterTools;

public class ShootingInvader extends Invader {

    private final float m_cooldown;
    private final float m_targetX, m_targetY;
    
    public ShootingInvader(float x, float y, float cooldown, float targetX, float targetY) {
        super(x, y);
        m_targetX = targetX;
        m_targetY = targetY;
        m_cooldown = cooldown;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public Iterator<Adversary> update(float dt) {
        float newCooldown = m_cooldown - dt;
        if (newCooldown <= 0.0f) {
            List<Adversary> newEntities = new ArrayList<Adversary>();
            newEntities.add(new ShootingInvader(getX(), getY(), Constants.INVADER_SHOOT_PERIOD, m_targetX, m_targetY));
            newEntities.add(new InvaderBullet(getX(), getY(), (float)Math.atan2(m_targetY - getY(), m_targetX - getX())));
            AudioEngine.get().play("invader-shoot");
            return newEntities.iterator();
        } else {
            return IterTools.just(new ShootingInvader(getX(), getY(), newCooldown, m_targetX, m_targetY));
        }
    }

}
