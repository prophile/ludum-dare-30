package uk.co.alynn.games.ld30;

import uk.co.alynn.games.ld30.world.PlayerShip;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;

public class SpaceHams extends ApplicationAdapter {
	private Renderer m_renderer;
	private PlayerShip m_mainShip;
	
	@Override
	public void create () {
	    m_renderer = new Renderer();
	    
	    Texture testTexture = new Texture(Gdx.files.internal("badlogic.jpg"), true);
	    testTexture.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.Linear);
        m_renderer.addSprite("badlogic", testTexture);
        
        m_mainShip = new PlayerShip(100.0f, 100.0f, (float) (Math.PI * 0.25f));
	}

	@Override
	public void render () {
	    m_mainShip = m_mainShip.update(Gdx.graphics.getDeltaTime());
	    
	    m_renderer.frame(new Runnable() {

            @Override
            public void run() {
                m_renderer.draw("badlogic", 100, 100, (float)(0.0*Math.PI));
                m_renderer.draw("badlogic", (int)m_mainShip.getX(), (int)m_mainShip.getY(), m_mainShip.getHeading());
            }
	        
	    });
	}
}
