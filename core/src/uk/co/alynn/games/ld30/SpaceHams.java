package uk.co.alynn.games.ld30;

import java.util.ArrayList;
import java.util.List;

import uk.co.alynn.games.ld30.world.Adversary;
import uk.co.alynn.games.ld30.world.Asteroid;
import uk.co.alynn.games.ld30.world.Bullet;
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
	private WaveSpawner m_waveSpawner;
	
	@Override
	public void create () {
	    m_renderer = new Renderer();
	    
	    Texture testTexture = new Texture(Gdx.files.internal("badlogic.jpg"), true);
	    testTexture.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.Linear);
        m_renderer.addSprite("badlogic", testTexture);
        
        Texture targetTexture = new Texture("target.png");
        m_renderer.addSprite("target", targetTexture);
        
        Texture asteroidTexture = new Texture("asteroid.png");
        m_renderer.addSprite("asteroid", asteroidTexture);
        
        Texture shipTexture = new Texture(Gdx.files.internal("ship.png"), true);
        shipTexture.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.Linear);
        m_renderer.addSprite("ship", shipTexture);
        
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
                        m_mainShip = m_mainShip.bind(xTarget, yTarget);
                    } else {
                        m_mainShip = m_mainShip.unbind();
                    }
                    return true;
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
	    List<Bullet> newBullets = new ArrayList<Bullet>();
	    for (Bullet bullet : m_bullets) {
	        Bullet bullet_ = bullet.update(Gdx.graphics.getDeltaTime());
	        if (bullet_ != null) {
	            newBullets.add(bullet_);
	        }
	    }
	    m_bullets = newBullets;
	    
	    List<Adversary> newAdversaries = new ArrayList<Adversary>();
	    for (Adversary adversary : m_adversaries) {
	        Adversary adversary_ = adversary.update(Gdx.graphics.getDeltaTime());
	        if (adversary_ != null) {
	            newAdversaries.add(adversary_);
	        }
	    }
	    m_adversaries = newAdversaries;
	    
	    for (Adversary adv : m_waveSpawner.update(Gdx.graphics.getDeltaTime())) {
	        m_adversaries.add(adv);
	    }
	    
	    m_renderer.frame(new Runnable() {

            @Override
            public void run() {
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
}
