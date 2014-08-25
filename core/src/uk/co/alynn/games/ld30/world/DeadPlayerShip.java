package uk.co.alynn.games.ld30.world;

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
            return new PlayerShip(Constants.STANDARD_RES_WIDTH / 3, Constants.STANDARD_RES_HEIGHT / 2, 0);
        } else {
            return new DeadPlayerShip(newTimer - dt);
        }
    }
    
    @Override
    public boolean isPhantom() {
        return true;
    }
}
