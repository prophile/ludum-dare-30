package uk.co.alynn.games.ld30.world;

import uk.co.alynn.games.ld30.AudioEngine;

public class DeadPlayerShip extends PlayerShip {
    private final float m_deathTimer;
    
    public DeadPlayerShip(float deathTimer) {
        super();
        m_deathTimer = deathTimer;
    }
    
    @Override
    public PlayerShip update(float dt) {
        float newTimer = m_deathTimer - dt;
        if (newTimer < 0.0f) {
            AudioEngine.get().play("player-respawn");
            return new PlayerShip(Constants.STANDARD_RES_WIDTH / 3, Constants.STANDARD_RES_HEIGHT / 2, 0);
        } else {
            return new DeadPlayerShip(newTimer);
        }
    }
    
    @Override
    public boolean isPhantom() {
        return true;
    }
}
