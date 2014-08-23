package uk.co.alynn.games.ld30;

import uk.co.alynn.games.ld30.world.PlayerShip;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.math.Vector2;

public class SpaceHams extends ApplicationAdapter {
	private Renderer m_renderer;
	private PlayerShip m_mainShip;
	
	@Override
	public void create () {
	    m_renderer = new Renderer();
	    
	    Texture testTexture = new Texture(Gdx.files.internal("badlogic.jpg"), true);
	    testTexture.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.Linear);
        m_renderer.addSprite("badlogic", testTexture);
        
        Texture targetTexture = new Texture("target.png");
        m_renderer.addSprite("target", targetTexture);
        
        Texture shipTexture = new Texture(Gdx.files.internal("ship.png"), true);
        shipTexture.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.Linear);
        m_renderer.addSprite("ship", shipTexture);
        
        m_mainShip = new PlayerShip(400.0f, 400.0f, (float) (Math.PI * 0.25f));
        m_mainShip = m_mainShip.bind(500.0f, 300.0f);
	}

	@Override
	public void render () {
	    System.out.println("-- FRAME --");
	    System.out.println("Ship position: " + m_mainShip.getX() + " " + m_mainShip.getY());
	    m_mainShip = m_mainShip.update(Gdx.graphics.getDeltaTime());
	    System.out.println("DT = " + Gdx.graphics.getDeltaTime());
	    System.out.println("Ship position: " + m_mainShip.getX() + " " + m_mainShip.getY());
	    
	    if (Gdx.input.isButtonPressed(Buttons.RIGHT)) {
	        // binding behaviour
	        m_mainShip = m_mainShip.bind(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
	    }
	    
	    m_renderer.frame(new Runnable() {

            @Override
            public void run() {
                m_renderer.draw("ship", (int)m_mainShip.getX(), (int)m_mainShip.getY(), m_mainShip.getHeading());
                Vector2 boundPos = m_mainShip.getBindPoint();
                if (boundPos != null) {
                    m_renderer.draw("target", (int)boundPos.x, (int)boundPos.y);
                }
            }
	        
	    });
	}
}
