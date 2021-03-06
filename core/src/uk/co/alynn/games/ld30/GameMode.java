package uk.co.alynn.games.ld30;

public interface GameMode {
    public GameMode update();
    public void render(Renderer renderer);
    
    public void leftClick(int x, int y);
    public void rightMouse(boolean down, int x, int y);
}
