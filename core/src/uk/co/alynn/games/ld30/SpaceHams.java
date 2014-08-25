package uk.co.alynn.games.ld30;

import uk.co.alynn.games.ld30.world.Constants;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class SpaceHams extends ApplicationAdapter {
	private Renderer m_renderer;
	private GameMode m_mode;
	
	private Viewport m_viewport;
	
	@Override
	public void create () {
	    AudioEngine.get();
	    
	    m_viewport = new FitViewport(Constants.STANDARD_RES_WIDTH, Constants.STANDARD_RES_HEIGHT);
	    m_viewport.update(Constants.STANDARD_RES_WIDTH, Constants.STANDARD_RES_HEIGHT);
	    
	    TextureAtlas atlas = new TextureAtlas("atlases/sprites.atlas");
	    
	    m_renderer = new Renderer(atlas.createPatch("health"));
	    
	    m_renderer.addSprite("target", atlas.findRegion("target"), 1.0f);
        m_renderer.addSprite("planet-1", atlas.findRegion("planet-1"), 0.5f);
        m_renderer.addSprite("planet-2", atlas.findRegion("planet-2"), 0.5f);
        m_renderer.addSprite("planet-3", atlas.findRegion("planet-3"), 0.5f);
        
        Texture titleTexture = new Texture(Gdx.files.internal("title.png"), true);
        titleTexture.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.Linear);
        m_renderer.addSprite("title-screen", titleTexture, (float)Gdx.graphics.getWidth() / 1440.0f);
        
        Texture gameOverTexture = new Texture(Gdx.files.internal("game-over.png"), true);
        gameOverTexture.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.Linear);
        m_renderer.addSprite("game-over-screen", gameOverTexture, (float)Gdx.graphics.getWidth() / 1440.0f);
        
        for (int i = 0; i < 25; ++i) {
            m_renderer.addSprite("asteroid-" + i,
                                 atlas.findRegion("asteroid-" + i),
                                 1.0f);
        }
        m_renderer.addSprite("invader-0", atlas.findRegion("invader-0"), 1.0f);
        m_renderer.addSprite("invader-1", atlas.findRegion("invader-1"), 1.0f);
        m_renderer.addSprite("invader-2", atlas.findRegion("invader-2"), 1.0f);
        m_renderer.addSprite("invader-3", atlas.findRegion("invader-3"), 1.0f);
        m_renderer.addSprite("ship", atlas.findRegion("ship"), 0.15f);
        m_renderer.addSprite("bang", atlas.findRegion("explosion"), 1.0f);
        m_renderer.addSprite("bullet", atlas.findRegion("bullet"), 0.4f);
        m_renderer.addSprite("destroyer", atlas.findRegion("destroyer"), 0.1f);

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
	    m_viewport.apply();
	    Animate.update(Gdx.graphics.getDeltaTime());
	    m_mode.rightMouse(Gdx.input.isButtonPressed(Buttons.RIGHT), Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
	    GameMode nextMode = m_mode.update();
	    m_renderer.frame(new Runnable() {

            @Override
            public void run() {
                m_mode.render(m_renderer);
            }
	        
	    });
	    m_mode = nextMode;
	}
	
	@Override
	public void resize(int w, int h) {
	    m_viewport.update(w, h);
	}
}
