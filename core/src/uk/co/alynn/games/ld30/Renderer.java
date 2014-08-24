package uk.co.alynn.games.ld30;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class Renderer {
    private static class Sprite {
        public float scale;
        public TextureRegion region;
    }
    
    private final SpriteBatch m_spriteBatch;
    private final ShapeRenderer m_shapeRenderer;
    private final BitmapFont m_font;
    private final Map<String, Sprite> m_sprites = new HashMap<String, Sprite>();
    
    public Renderer() {
        m_spriteBatch = new SpriteBatch();
        m_shapeRenderer = new ShapeRenderer();
        m_font = loadFont();
    }
    
    private static BitmapFont loadFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 16;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();
        return font;
    }
    
    public void frame(Runnable renderFrame) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        m_spriteBatch.begin();
        renderFrame.run();
        text("FPS: " + Gdx.graphics.getFramesPerSecond(), 70, Gdx.graphics.getHeight() - 40, 0.7f);
        m_spriteBatch.end();
    }
    
    void addSprite(String image, TextureRegion rg, float scale) {
        Sprite spr = new Sprite();
        spr.region = rg;
        spr.scale = scale;
        m_sprites.put(image, spr);
    }
    
    public void addSprite(String image, Texture rg, float scale) {
        addSprite(image, new TextureRegion(rg), scale);
    }
    
    public void draw(String image,
                     int x, int y,
                     float rotation) {
        Sprite sprite = m_sprites.get(image);
        TextureRegion rg = sprite.region;
        int regionWidth = rg.getRegionWidth();
        int regionHeight = rg.getRegionHeight();
        
        float xOffset = 0.5f * regionWidth;
        float yOffset = 0.5f * regionHeight;
        m_spriteBatch.draw(rg, (float)x - xOffset, (float)y - yOffset, xOffset, yOffset, regionWidth, regionHeight, sprite.scale, sprite.scale, rotation * MathUtils.radiansToDegrees);
    }
    
    public void draw(String image,
                     int x, int y) {
        draw(image, x, y, 0.0f);
    }
    
    public void text(String text,
                     int x, int y,
                     float alpha) {
        m_font.setColor(1.0f, 1.0f, 1.0f, alpha);
        m_font.draw(m_spriteBatch, text, x, y);
    }

    public void bindingWave(int x, int y, int x2, int y2) {
        // what nao
        m_spriteBatch.end();
        m_shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        m_shapeRenderer.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        final int FLANGE = 60;
        float xDiff = (float)(x2 - x);
        float yDiff = (float)(y2 - y);
        float length = (float) Math.hypot(xDiff, yDiff);
        xDiff /= length;
        yDiff /= length;
        xDiff *= FLANGE;
        yDiff *= FLANGE;
        m_shapeRenderer.triangle((int)x, (int)y, x2 + yDiff, y2 - xDiff, x2 - yDiff, y2 + xDiff);
        m_shapeRenderer.end();
        m_spriteBatch.begin();
    }
}
