package uk.co.alynn.games.ld30;

public interface GameMode {
    public void update();
    public void render(Renderer renderer);
    
    public void leftClick(int x, int y);
    public void rightClick(int x, int y);
}
