package uk.co.alynn.games.ld30;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import uk.co.alynn.games.ld30.world.Adversary;
import uk.co.alynn.games.ld30.world.Bullet;
import uk.co.alynn.games.ld30.world.Constants;
import uk.co.alynn.games.ld30.world.DamagePlanet;
import uk.co.alynn.games.ld30.world.DeadPlayerShip;
import uk.co.alynn.games.ld30.world.Planet;
import uk.co.alynn.games.ld30.world.PlayerShip;
import uk.co.alynn.games.ld30.world.Wreckage;

public class LiveMode implements GameMode {
    private PlayerShip m_mainShip;
    private List<Bullet> m_bullets = new ArrayList<Bullet>();
    private List<Adversary> m_adversaries = new ArrayList<Adversary>();
    private List<Planet> m_planets = new ArrayList<Planet>();
    private boolean m_uded = false;
    private final Tutorial m_tutorial = new Tutorial();
    private float m_tractorCheck = 0.5f;
    
    private int m_wave = Constants.STARTING_WAVE;
    private int m_queuedTick = -2;
    private float m_waveTime = -2.0f;
    
    public LiveMode() {
        m_planets.add(new Planet(0.293f*Constants.STANDARD_RES_WIDTH, 0.391f*Constants.STANDARD_RES_HEIGHT, 100, "planet-1"));
        m_planets.add(new Planet(0.684f*Constants.STANDARD_RES_WIDTH, 0.651f*Constants.STANDARD_RES_HEIGHT, 100, "planet-2"));
        m_planets.add(new Planet(0.732f*Constants.STANDARD_RES_WIDTH, 0.273f*Constants.STANDARD_RES_HEIGHT, 100, "planet-3"));
        
        m_mainShip = new PlayerShip(50.0f, 400.0f, 0.0f);
    }
    
    public GameMode update() {
        AudioEngine.get().selectMainMusic();
        
        m_tutorial.update(Gdx.graphics.getDeltaTime());
        if (m_tutorial.isFinished()) {
            spawnNewAdversaries();
        }
        m_mainShip = m_mainShip.update(Gdx.graphics.getDeltaTime());
        updateBullets();
        
        updateAdversaries();
        
        collideAdversariesWithBullets();
        collideAdversariesWithPlayer();
        collideAdversariesWithPlanets();
        
        cullOutOfBoundsAdversaries();
        
        m_tractorCheck -= Gdx.graphics.getDeltaTime();
        if (m_tractorCheck < 0.0f) {
            m_tractorCheck = 1.0f;
            if (m_mainShip.getBindPoint() != null) {
                AudioEngine.get().play("tractor");
            }
        }
        
        if (m_uded) {
            AudioEngine.get().play("game-over");
            AudioEngine.get().stopAll("tractor");
        }
        
        return m_uded ? new TitleMode("game-over-screen", Constants.DEATH_HOLDOFF_TIME) : this;
    }
    
    private void cullOutOfBoundsAdversaries() {
        List<Adversary> newAdversaries = new ArrayList<Adversary>();
        final float LEFT_BOUND = -100.0f;
        final float RIGHT_BOUND = Constants.STANDARD_RES_WIDTH + 100.0f;
        final float TOP_BOUND = Constants.STANDARD_RES_HEIGHT + 100.0f;
        final float BOTTOM_BOUND = -100.0f;
        for (Adversary adversary : m_adversaries) {
            if (!(adversary.getX() > LEFT_BOUND))
                continue;
            if (!(adversary.getX() < RIGHT_BOUND))
                continue;
            if (!(adversary.getY() > BOTTOM_BOUND))
                continue;
            if (!(adversary.getY() < TOP_BOUND))
                continue;
            newAdversaries.add(adversary);
        }
        m_adversaries = newAdversaries;
    }

    private void collideAdversariesWithPlanets() {
        for (int i = 0; i < m_planets.size(); ++i) {
            collideAdversariesWithPlanetByIndex(i);
        }
    }

    private void collideAdversariesWithPlanetByIndex(final int i) {
        final Planet planet = m_planets.get(i);
        DamagePlanet damager = new DamagePlanet() {
            @Override
            public void damage(int x) {
                Planet newPlanet = new Planet(planet.getX(), planet.getY(), planet.getHealth() - x, planet.getSprite());
                m_planets.set(i, newPlanet);
                if (newPlanet.getHealth() <= 0) {
                    m_uded = true;
                }
            }
        };
        List<Adversary> newAdversaries = new ArrayList<Adversary>();
        for (Adversary adversary : m_adversaries) {
            if (Math.hypot(adversary.getX() - planet.getX(), adversary.getY() - planet.getY()) < Constants.PLANET_COLLIDE_RADIUS) {
                Adversary newAdv = adversary.hitPlanet(damager);
                if (newAdv != null) {
                    newAdversaries.add(adversary);
                }
            } else {
                newAdversaries.add(adversary);
            }
        }
        m_adversaries = newAdversaries;
    }

    public void render(Renderer renderer) {
        renderer.draw("background", (int)(Constants.STANDARD_RES_WIDTH / 2), (int)(Constants.STANDARD_RES_HEIGHT / 2));
        for (Planet planet : m_planets) {
            renderer.draw(planet.getSprite(), (int)planet.getX(), (int)planet.getY());
            int healthBarWidth = 20 + planet.getHealth();
            renderer.drawHealthBar((int)(planet.getX()) - (healthBarWidth / 2), (int)planet.getY() - 100, healthBarWidth, 17);
        }
        if (!m_mainShip.isPhantom())
            renderer.draw("ship", (int)m_mainShip.getX(), (int)m_mainShip.getY(), m_mainShip.getHeading());
        Vector2 boundPos = m_mainShip.getBindPoint();
        for (Bullet bullet : m_bullets) {
            renderer.draw("bullet", (int)bullet.getX(), (int)bullet.getY());
        }
        if (boundPos != null) {
            renderBindingWave(m_mainShip.getX(), m_mainShip.getY(), boundPos.x, boundPos.y, renderer);
        }
        for (Adversary adversary : m_adversaries) {
            renderer.draw(adversary.getImage(), (int)adversary.getX(), (int)adversary.getY(), adversary.getHeading());
        }
        m_tutorial.render(renderer);
        // WAVE indicator
        float waveAlpha = 0.0f;
        if (m_waveTime < 0.5f) {
            waveAlpha = m_waveTime / 0.5f;
        } else if (m_waveTime < 2.5f) {
            waveAlpha = 1.0f;
        } else if (m_waveTime < 3f) {
            waveAlpha = 1.0f - ((m_waveTime - 2.5f) / 0.5f);
        }
        if (waveAlpha > 0.0f) {
            renderer.text("Wave " + m_wave, Constants.STANDARD_RES_WIDTH / 2, (2 * Constants.STANDARD_RES_HEIGHT) / 3, waveAlpha);
        }
    }
    
    private void renderBindingWave(float x, float y, float x2, float y2,
            Renderer renderer) {
        renderer.bindingWave((int)x, (int)y, (int)x2, (int)y2);
    }

    public void leftClick(int xTarget, int yTarget) {
        if (m_tutorial.noBulletsYet() || m_mainShip.isPhantom()) {
            return;
        }
        float dx = xTarget - m_mainShip.getX();
        float dy = yTarget - m_mainShip.getY();
        // add bullet
        Bullet bullet = new Bullet(m_mainShip.getX(), m_mainShip.getY(), (float)Math.atan2(dy, dx), 0.0f);
        m_bullets.add(bullet);
        m_tutorial.didShoot();
        AudioEngine.get().play("shoot");
    }
    
    public void rightMouse(boolean down, int xTarget, int yTarget) {
        if (m_mainShip.isPhantom())
            return;
        if (down) {
            // calculate direction
            for (Planet planet : m_planets) {
                if (Math.hypot(xTarget - planet.getX(), yTarget - planet.getY()) < Constants.PLANET_BIND_RADIUS &&
                        Math.hypot(m_mainShip.getX() - planet.getX(), m_mainShip.getY() - planet.getY()) < Constants.PLANET_BIND_RADIUS) {
                    xTarget = (int)planet.getX();
                    yTarget = (int)planet.getY();
                    float bindingOffset = (float) ((xTarget - m_mainShip.getX())*Math.sin(m_mainShip.getHeading()) - (yTarget - m_mainShip.getY())*Math.cos(m_mainShip.getHeading()));
                    int direction;
                    if (bindingOffset < -Constants.BIND_LIMIT) {
                        direction = 1;
                    } else if (bindingOffset > Constants.BIND_LIMIT) {
                        direction = -1;
                    } else {
                        direction = 0;
                    }
                    if (direction != 0) {
                        if (m_mainShip.getBindPoint() == null) {
                            m_tutorial.didLatchToPlanet();
                            AudioEngine.get().play("tractor");
                            m_mainShip = m_mainShip.bind(xTarget, yTarget, direction);
                        }
                    }
                    return;
                }
            }
        } else {
            if (m_mainShip.getBindPoint() != null) {
                m_mainShip = m_mainShip.unbind();
                m_tutorial.didUnlatch();
                AudioEngine.get().stopAll("tractor");
            }
        }
    }

    private void collideAdversariesWithBullets() {
        List<Adversary> retainedAdversaries = new ArrayList<Adversary>();
        for (Adversary adv : m_adversaries) {
            Adversary advCurrent;
            if (m_mainShip.isPhantom()) {
                advCurrent = adv;
            } else {
                advCurrent = adv.seePlayer(m_mainShip.getX(), m_mainShip.getY());
            }
            List<Bullet> retainedBullets = new ArrayList<Bullet>();
            for (Bullet bullet : m_bullets) {
                if (advCurrent != null && Math.hypot(adv.getX() - bullet.getX(), adv.getY() - bullet.getY()) < 15.0f) {
                    advCurrent = advCurrent.hitBullet();
                } else {
                    retainedBullets.add(bullet);
                }
            }
            if (advCurrent != null) {
                retainedAdversaries.add(advCurrent);
            }
            m_bullets = retainedBullets;
        }
        m_adversaries = retainedAdversaries;
    }
    
    private void collideAdversariesWithPlayer() {
        if (m_mainShip.isPhantom())
            return;
        final List<Adversary> retainedAdversaries = new ArrayList<Adversary>();
        for (Adversary adv : m_adversaries) {
            if (Math.hypot(adv.getX() - m_mainShip.getX(), adv.getY() - m_mainShip.getY()) < 35.0f) {
                // do the collision dance
                Adversary adv_ = adv.hitPlayer(new Runnable() {

                    @Override
                    public void run() {
                        System.err.println("ASPLODE");
                        //m_uded = true; // NOT TODAY
                        retainedAdversaries.add(new Wreckage(m_mainShip.getX(), m_mainShip.getY(), 0.0f));
                        m_mainShip = new DeadPlayerShip(Constants.SHIP_DEAD_TIME);
                        AudioEngine.get().play("player-die");
                        AudioEngine.get().stopAll("tractor");
                    }
                    
                });
                if (adv_ != null) {
                    retainedAdversaries.add(adv_);
                }
            } else {
                retainedAdversaries.add(adv);
            }
        }
        
        m_adversaries = retainedAdversaries;
        
    }

    private void spawnNewAdversaries() {
        float dt = Gdx.graphics.getDeltaTime();
        m_waveTime += dt;
        int waveTick = (int)Math.floor(m_waveTime / Constants.SECONDS_PER_TICK);
        Wave wave = WaveData.getWave(m_wave);
        boolean endable = false;
        while (m_queuedTick <= waveTick) {
            if (m_queuedTick == 0) {
                AudioEngine.get().play("new-wave");
            }
            // spawning logic
            endable = wave.dispatch(m_planets, m_adversaries, m_queuedTick++);
        }
        // ending logic
        if (endable && m_adversaries.isEmpty()) {
            ++m_wave;
            m_queuedTick = 0;
            m_waveTime = 0.0f;
        }
    }

    private void updateAdversaries() {
        List<Adversary> newAdversaries = new ArrayList<Adversary>();
        for (Adversary adversary : m_adversaries) {
            Iterator<Adversary> returnedAdversaries = adversary.update(Gdx.graphics.getDeltaTime());
            while (returnedAdversaries.hasNext()) {
                newAdversaries.add(returnedAdversaries.next());
            }
        }
        m_adversaries = newAdversaries;
    }

    private void updateBullets() {
        List<Bullet> newBullets = new ArrayList<Bullet>();
        for (Bullet bullet : m_bullets) {
            Bullet bullet_ = bullet.update(Gdx.graphics.getDeltaTime());
            if (bullet_ != null) {
                newBullets.add(bullet_);
            }
        }
        m_bullets = newBullets;
    }
}
