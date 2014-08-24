package uk.co.alynn.games.ld30;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class SpaceHams extends ApplicationAdapter {
	private Renderer m_renderer;
	private GameMode m_mode;
	
	@Override
	public void create () {
	    m_renderer = new Renderer();
        
	    TextureAtlas atlas = new TextureAtlas("atlases/sprites.atlas");
	    
	    m_renderer.addSprite("target", atlas.findRegion("target"), 1.0f);
        m_renderer.addSprite("planet-1", atlas.findRegion("planet-1"), 0.5f);
        
        Texture titleTexture = new Texture(Gdx.files.internal("title.png"), true);
        titleTexture.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.Linear);
        m_renderer.addSprite("title-screen", titleTexture, (float)Gdx.graphics.getWidth() / 1440.0f);
        
        Texture gameOverTexture = new Texture(Gdx.files.internal("game-over.png"), true);
        gameOverTexture.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.Linear);
        m_renderer.addSprite("game-over-screen", gameOverTexture, (float)Gdx.graphics.getWidth() / 1440.0f);
        
        m_renderer.addSprite("asteroid", atlas.findRegion("asteroid"), 1.0f);
        m_renderer.addSprite("invader", atlas.findRegion("invader"), 1.0f);
        m_renderer.addSprite("ship", atlas.findRegion("ship"), 0.15f);
        m_renderer.addSprite("bullet", atlas.findRegion("bullet"), 0.4f);
        
        Texture bgTexture = new Texture(Gdx.files.internal("background.png"), true);
        bgTexture.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.Linear);
        m_renderer.addSprite("background", bgTexture, 1.0f);
        
        m_mode = new TitleMode("title-screen", 0.0f);
        
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
                int xTarget = screenX;
                int yTarget = (Gdx.graphics.getHeight() - screenY);
                if (button == Buttons.LEFT) {
                    m_mode.leftClick(xTarget, yTarget);
                    return true;
                }
                if (button == Buttons.RIGHT) {
                    m_mode.rightClick(xTarget, yTarget);
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
	    GameMode nextMode = m_mode.update();
	    m_renderer.frame(new Runnable() {

            @Override
            public void run() {
                m_mode.render(m_renderer);
            }
	        
	    });
	    m_mode = nextMode;
	}
}
