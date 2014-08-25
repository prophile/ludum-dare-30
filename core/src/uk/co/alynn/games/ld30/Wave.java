package uk.co.alynn.games.ld30;

import java.util.List;

import uk.co.alynn.games.ld30.world.Adversary;
import uk.co.alynn.games.ld30.world.Asteroid;
import uk.co.alynn.games.ld30.world.Constants;
import uk.co.alynn.games.ld30.world.Destroyer;
import uk.co.alynn.games.ld30.world.EnteringInvader;
import uk.co.alynn.games.ld30.world.Planet;
import com.badlogic.gdx.math.MathUtils;

public abstract class Wave {
    private List<Planet> m_planets;
    private List<Adversary> m_adversaries;
    private boolean m_spawnedDangerousEnemy;

    private final float x(float percent) {
        return percent*0.01f*Constants.STANDARD_RES_WIDTH;
    }
    
    private final float y(float percent) {
        return percent*0.01f*Constants.STANDARD_RES_HEIGHT;
    }
    
    public final boolean dispatch(List<Planet> planets, List<Adversary> adversaries, int tick) {
        m_planets = planets;
        m_adversaries = adversaries;
        m_spawnedDangerousEnemy = false;
        boolean okToFinish = tick(tick);
        if (m_spawnedDangerousEnemy) {
            AudioEngine.get().play("invader-warn");
        }
        return okToFinish;
    }
    
    private final void spawn(Adversary adv) {
        m_adversaries.add(adv);
    }

    protected final void spawnAsteroid(float xP, float yP, float angleDeg) {
        float angleRad = angleDeg * MathUtils.degreesToRadians;
        spawn(new Asteroid(x(xP), y(yP), angleRad));
    }
    
    protected final void spawnInvader(float xP, float yP, int targetPlanet) {
        Planet tPlanet = m_planets.get(targetPlanet);
        spawn(new EnteringInvader(x(xP), y(yP), tPlanet.getX(), tPlanet.getY()));
    }
    
    protected final void spawnDestroyer(float xP, float yP) {
        spawn(new Destroyer(x(xP), y(yP), x(50), y(50), Constants.DESTROYER_INITIAL_COOLDOWN));
    }
    
    abstract protected boolean tick(int tick);
}
