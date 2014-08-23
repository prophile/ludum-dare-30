package uk.co.alynn.games.ld30;

import com.badlogic.gdx.Gdx;

public class TitleMode implements GameMode {

    private GameMode m_next = this;
    private final String m_screen;
    
    public TitleMode(String screen) {
        m_screen = screen;
    }
    
    @Override
    public GameMode update() {
        return m_next;
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
        m_next = new LiveMode();
    }

    @Override
    public void rightClick(int x, int y) {
        start();
    }

}
