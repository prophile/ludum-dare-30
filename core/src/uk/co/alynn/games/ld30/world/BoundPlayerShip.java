package uk.co.alynn.games.ld30.world;

import com.badlogic.gdx.math.Vector2;

class BoundPlayerShip extends PlayerShip {
    private final float m_boundX, m_boundY;
    
    public BoundPlayerShip(float x, float y, float heading, float bindX, float bindY) {
        super(x, y, heading);
        m_boundX = bindX;
        m_boundY = bindY;
    }
    
    @Override
    public PlayerShip unbind() {
        return new PlayerShip(getX(), getY(), getHeading());
    }
    
    @Override
    public Vector2 getBindPoint() {
        return new Vector2(m_boundX, m_boundY);
    }
    
    @Override
    public PlayerShip update(float dt) {
        float translatedX = getX() - m_boundX;
        float translatedY = getY() - m_boundY;
        float boundRadius = (float) Math.hypot(translatedX, translatedY);
        float boundAngle = (float) Math.atan2(translatedY, translatedX);
        float posStep = Constants.SPEED*dt;
        float calX = (2*boundRadius*boundRadius - posStep) / (2*boundRadius);
        //float calY = (float) (0.5f*Math.sqrt((posStep * (4*boundRadius*boundRadius - posStep)) / (boundRadius*boundRadius)));
        float calY = -(float)Math.sqrt(boundRadius*boundRadius - calX*calX);
        float rotatedX = (float) (calX*Math.cos(boundAngle) - calY*Math.sin(boundAngle));
        float rotatedY = (float) (calX*Math.sin(boundAngle) + calY*Math.cos(boundAngle));
        float finalX = rotatedX + m_boundX;
        float finalY = rotatedY + m_boundY;
        return new BoundPlayerShip(finalX, finalY, getHeading(), m_boundX, m_boundY);
    }
}
