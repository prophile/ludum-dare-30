package uk.co.alynn.games.ld30;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;

public class SpaceHams extends ApplicationAdapter {
	private Renderer m_renderer;
	
	@Override
	public void create () {
	    m_renderer = new Renderer();
	    
	    Texture testTexture = new Texture(Gdx.files.internal("badlogic.jpg"), true);
	    testTexture.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.Linear);
        m_renderer.addSprite("badlogic", testTexture);
	}

	@Override
	public void render () {
	    m_renderer.frame(new Runnable() {

            @Override
            public void run() {
                m_renderer.draw("badlogic", 100, 100, (float)(0.0*Math.PI));
            }
	        
	    });
	}
}
