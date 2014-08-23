package uk.co.alynn.games.ld30;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public class Renderer {
    private final SpriteBatch m_spriteBatch;
    private final Map<String, TextureRegion> m_sprites = new HashMap<String, TextureRegion>();
    
    public Renderer() {
        m_spriteBatch = new SpriteBatch();
    }
    
    public void frame(Runnable renderFrame) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        m_spriteBatch.begin();
        renderFrame.run();
        m_spriteBatch.end();
    }
    
    private void addSprite(String image, TextureRegion rg) {
        m_sprites.put(image, rg);
    }
    
    public void addSprite(String image, Texture rg) {
        addSprite(image, new TextureRegion(rg));
    }
    
    public void draw(String image,
                     int x, int y,
                     float rotation) {
        TextureRegion rg = m_sprites.get(image);
        int regionWidth = rg.getRegionWidth();
        int regionHeight = rg.getRegionHeight();
        
        float xOffset = 0.5f * regionWidth;
        float yOffset = 0.5f * regionHeight;
        m_spriteBatch.draw(rg, (float)x - xOffset, (float)y - yOffset, xOffset, yOffset, regionWidth, regionHeight, 1.0f, 1.0f, rotation * MathUtils.radiansToDegrees);
    }
    
    public void draw(String image,
                     int x, int y) {
        draw(image, x, y, 0.0f);
    }
}
