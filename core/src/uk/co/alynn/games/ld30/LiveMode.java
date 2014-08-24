package uk.co.alynn.games.ld30;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import uk.co.alynn.games.ld30.world.Adversary;
import uk.co.alynn.games.ld30.world.Asteroid;
import uk.co.alynn.games.ld30.world.Bullet;
import uk.co.alynn.games.ld30.world.Constants;
import uk.co.alynn.games.ld30.world.Planet;
import uk.co.alynn.games.ld30.world.PlayerShip;
import uk.co.alynn.games.ld30.world.WaveSpawner;

public class LiveMode implements GameMode {
    private PlayerShip m_mainShip;
    private List<Bullet> m_bullets = new ArrayList<Bullet>();
    private List<Adversary> m_adversaries = new ArrayList<Adversary>();
    private List<Planet> m_planets = new ArrayList<Planet>();
    private WaveSpawner m_waveSpawner;
    private boolean m_uded = false;
    
    public LiveMode() {
        m_planets.add(new Planet(300.0f, 300.0f));
        m_planets.add(new Planet(700.0f, 500.0f));
        m_planets.add(new Planet(750.0f, 250.0f));
        
        m_mainShip = new PlayerShip(50.0f, 400.0f, 0.0f);
        
        m_waveSpawner = new WaveSpawner();
    }
    
    public GameMode update() {
        m_mainShip = m_mainShip.update(Gdx.graphics.getDeltaTime());
        updateBullets();
        
        updateAdversaries();
        
        collideAdversariesWithBullets();
        collideAdversariesWithPlayer();
        
        spawnNewAdversaries();
        
        return m_uded ? new TitleMode("game-over-screen", Constants.DEATH_HOLDOFF_TIME) : this;
    }
    
    public void render(Renderer renderer) {
        renderer.draw("background", (int)(Gdx.graphics.getWidth() / 2), (int)(Gdx.graphics.getHeight() / 2));
        for (Planet planet : m_planets) {
            renderer.draw(planet.getSprite(), (int)planet.getX(), (int)planet.getY());
        }
        renderer.draw("ship", (int)m_mainShip.getX(), (int)m_mainShip.getY(), m_mainShip.getHeading());
        Vector2 boundPos = m_mainShip.getBindPoint();
        for (Bullet bullet : m_bullets) {
            renderer.draw("target", (int)bullet.getX(), (int)bullet.getY());
        }
        if (boundPos != null) {
            renderer.draw("target", (int)boundPos.x, (int)boundPos.y);
        }
        for (Adversary adversary : m_adversaries) {
            renderer.draw(adversary.getImage(), (int)adversary.getX(), (int)adversary.getY(), adversary.getHeading());
        }
    }
    
    public void leftClick(int xTarget, int yTarget) {
        float dx = xTarget - m_mainShip.getX();
        float dy = yTarget - m_mainShip.getY();
        // add bullet
        Bullet bullet = new Bullet(m_mainShip.getX(), m_mainShip.getY(), (float)Math.atan2(dy, dx));
        m_bullets.add(bullet);
    }
    
    public void rightClick(int xTarget, int yTarget) {
        if (m_mainShip.getBindPoint() == null) {
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
                        m_mainShip = m_mainShip.bind(xTarget, yTarget, direction);
                    }
                    return;
                }
            }
        } else {
            m_mainShip = m_mainShip.unbind();
        }
    }

    private void collideAdversariesWithBullets() {
        List<Adversary> retainedAdversaries = new ArrayList<Adversary>();
        for (Adversary adv : m_adversaries) {
            Adversary advCurrent = adv;
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
        List<Adversary> retainedAdversaries = new ArrayList<Adversary>();
        for (Adversary adv : m_adversaries) {
            if (Math.hypot(adv.getX() - m_mainShip.getX(), adv.getY() - m_mainShip.getY()) < 35.0f) {
                // do the collision dance
                Adversary adv_ = adv.hitPlayer(new Runnable() {

                    @Override
                    public void run() {
                        System.err.println("GAME OVER MAN");
                        m_uded = true;
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
        for (Adversary adv : m_waveSpawner.update(Gdx.graphics.getDeltaTime(), m_planets)) {
            m_adversaries.add(adv);
        }
    }

    private void updateAdversaries() {
        List<Adversary> newAdversaries = new ArrayList<Adversary>();
        for (Adversary adversary : m_adversaries) {
            Adversary adversary_ = adversary.update(Gdx.graphics.getDeltaTime());
            if (adversary_ != null) {
                newAdversaries.add(adversary_);
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
