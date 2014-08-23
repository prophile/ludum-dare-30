package uk.co.alynn.games.ld30.world;

public interface Adversary {
    public String getImage();
    public float getX();
    public float getY();
    public Adversary update(float dt);
    public Adversary hitPlayer(Runnable terminateGame); // plus some way of marking the player dead
    public Adversary hitBullet();
    public float getHeading();
}
