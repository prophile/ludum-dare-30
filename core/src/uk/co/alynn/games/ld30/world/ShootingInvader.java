package uk.co.alynn.games.ld30.world;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import uk.co.alynn.games.ld30.IterTools;

public class ShootingInvader extends Invader {

    private final float m_cooldown;
    
    public ShootingInvader(float x, float y, float cooldown) {
        super(x, y);
        m_cooldown = cooldown;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public Iterator<Adversary> update(float dt) {
        float newCooldown = m_cooldown - dt;
        if (newCooldown <= 0.0f) {
            List<Adversary> newEntities = new ArrayList<Adversary>();
            newEntities.add(new ShootingInvader(getX(), getY(), Constants.INVADER_SHOOT_PERIOD));
            newEntities.add(new EnteringInvader(getX(), getY(), 0.0f, 0.0f));
            return newEntities.iterator();
        } else {
            return IterTools.just(new ShootingInvader(getX(), getY(), newCooldown));
        }
    }

}
