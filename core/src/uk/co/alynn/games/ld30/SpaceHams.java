package uk.co.alynn.games.ld30;

import java.util.ArrayList;
import java.util.List;

import uk.co.alynn.games.ld30.world.Adversary;
import uk.co.alynn.games.ld30.world.Asteroid;
import uk.co.alynn.games.ld30.world.Bullet;
import uk.co.alynn.games.ld30.world.Constants;
import uk.co.alynn.games.ld30.world.Planet;
import uk.co.alynn.games.ld30.world.PlayerShip;
import uk.co.alynn.games.ld30.world.WaveSpawner;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.math.Vector2;

public class SpaceHams extends ApplicationAdapter {
	private Renderer m_renderer;
	private PlayerShip m_mainShip;
	private List<Bullet> m_bullets = new ArrayList<Bullet>();
	private List<Adversary> m_adversaries = new ArrayList<Adversary>();
	private List<Planet> m_planets = new ArrayList<Planet>();
	private WaveSpawner m_waveSpawner;
	
	@Override
	public void create () {
	    m_planets.add(new Planet(300.0f, 300.0f));
	    m_planets.add(new Planet(700.0f, 800.0f));
	    
	    m_renderer = new Renderer();
        
        Texture targetTexture = new Texture("target.png");
        m_renderer.addSprite("target", targetTexture, 1.0f);
        
        Texture planetTexture = new Texture("planet-1.png");
        m_renderer.addSprite("planet-1", planetTexture, 1.0f);
        
        Texture asteroidTexture = new Texture("asteroid.png");
        m_renderer.addSprite("asteroid", asteroidTexture, 1.0f);
        
        Texture shipTexture = new Texture(Gdx.files.internal("ship.png"), true);
        shipTexture.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.Linear);
        m_renderer.addSprite("ship", shipTexture, 0.25f);
        
        Texture bgTexture = new Texture(Gdx.files.internal("background.png"), true);
        bgTexture.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.Linear);
        m_renderer.addSprite("background", bgTexture, 1.0f);
        
        m_mainShip = new PlayerShip(400.0f, 400.0f, (float) (Math.PI * 0.25f));
        m_adversaries.add(new Asteroid(700.0f, 400.0f, 0.0f));
        
        m_waveSpawner = new WaveSpawner();
        
        Gdx.input.setInputProcessor(new InputProcessor() {

            @Override
            public boolean keyDown(int keycode) {
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer,
                    int button) {
                float xTarget = (float)screenX;
                float yTarget = (float)(Gdx.graphics.getHeight() - screenY);
                if (button == Buttons.LEFT) {
                    float dx = xTarget - m_mainShip.getX();
                    float dy = yTarget - m_mainShip.getY();
                    // add bullet
                    Bullet bullet = new Bullet(m_mainShip.getX(), m_mainShip.getY(), (float)Math.atan2(dy, dx));
                    m_bullets.add(bullet);
                    return true;
                }
                if (button == Buttons.RIGHT) {
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
                                return true;
                            }
                        }
                        return false;
                    } else {
                        m_mainShip = m_mainShip.unbind();
                        return true;
                    }
                }
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer,
                    int button) {
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                return false;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                return false;
            }

            @Override
            public boolean scrolled(int amount) {
                return false;
            }
            
        });
	}

	@Override
	public void render () {
	    m_mainShip = m_mainShip.update(Gdx.graphics.getDeltaTime());
	    updateBullets();
	    
	    updateAdversaries();
	    
	    collideAdversariesWithBullets();
	    collideAdversariesWithPlayer();
	    
	    spawnNewAdversaries();
	    
	    m_renderer.frame(new Runnable() {

            @Override
            public void run() {
                m_renderer.draw("background", (int)(Gdx.graphics.getWidth() / 2), (int)(Gdx.graphics.getHeight() / 2));
                for (Planet planet : m_planets) {
                    m_renderer.draw(planet.getSprite(), (int)planet.getX(), (int)planet.getY());
                }
                m_renderer.draw("ship", (int)m_mainShip.getX(), (int)m_mainShip.getY(), m_mainShip.getHeading());
                Vector2 boundPos = m_mainShip.getBindPoint();
                for (Bullet bullet : m_bullets) {
                    m_renderer.draw("target", (int)bullet.getX(), (int)bullet.getY());
                }
                if (boundPos != null) {
                    m_renderer.draw("target", (int)boundPos.x, (int)boundPos.y);
                }
                for (Adversary adversary : m_adversaries) {
                    m_renderer.draw(adversary.getImage(), (int)adversary.getX(), (int)adversary.getY(), adversary.getHeading());
                }
            }
	        
	    });
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
        for (Adversary adv : m_waveSpawner.update(Gdx.graphics.getDeltaTime())) {
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
