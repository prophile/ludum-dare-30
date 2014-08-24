package uk.co.alynn.games.ld30.world;

import java.util.Iterator;

public interface Adversary {
    public String getImage();
    public float getX();
    public float getY();
    public Iterator<Adversary> update(float dt);
    public Adversary hitPlayer(Runnable terminateGame); // plus some way of marking the player dead
    public Adversary hitBullet();
    public float getHeading();
    public Adversary hitPlanet(DamagePlanet damage);
}
