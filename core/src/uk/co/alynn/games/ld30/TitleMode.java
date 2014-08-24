package uk.co.alynn.games.ld30;

import com.badlogic.gdx.Gdx;

public class TitleMode implements GameMode {

    private GameMode m_next = this;
    private final String m_screen;
    private final float m_holdoff;
    
    public TitleMode(String screen, float holdoff) {
        m_screen = screen;
        m_holdoff = holdoff;
    }
    
    @Override
    public GameMode update() {
        if (m_next != this) {
            return m_next;
        }
        return new TitleMode(m_screen, m_holdoff - Gdx.graphics.getDeltaTime());
    }

    @Override
    public void render(Renderer renderer) {
        renderer.draw(m_screen, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
    }

    @Override
    public void leftClick(int x, int y) {
        start();
    }

    private void start() {
        if (m_holdoff < 0.0f) {
            m_next = new LiveMode();
        }
    }

    @Override
    public void rightMouse(boolean down, int x, int y) {
        if (down) {
            start();
        }
    }

}
