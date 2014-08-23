package uk.co.alynn.games.ld30.world;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

public class WaveSpawner {
    private int m_lastWaveSpawned = 0;
    private float m_totalTime = 0.0f;
    private List<Adversary> m_newAdversaries = new ArrayList<Adversary>();
    
    public List<Adversary> update(float dt) {
        m_newAdversaries.clear();
        m_totalTime += dt;
        float tickID = m_totalTime / Constants.SECONDS_PER_TICK;
        int tickIDI = (int)tickID;
        while (tickIDI > m_lastWaveSpawned) {
            spawnWave(++m_lastWaveSpawned);
        }
        return m_newAdversaries;
    }
    
    private void spawn(Adversary adversary) {
        m_newAdversaries.add(adversary);
    }
    
    private void spawnAsteroidLeft() {
        float yPosition = (float)MathUtils.random(0, Gdx.graphics.getHeight());
        float angle = MathUtils.random((float)(-0.25*Math.PI), (float) (0.25*Math.PI));
        spawn(new Asteroid(-15.0f, yPosition, angle));
    }
    
    private void spawnInvaderTop() {
        spawn(new EnteringInvader(MathUtils.random(20.0f, Gdx.graphics.getWidth() - 20.0f), Gdx.graphics.getHeight() + 15.0f, 300.0f, 300.0f));
    }
    
    private void spawnWave(int waveID) {
        if (waveID % 6 == 3) {
            spawnAsteroidLeft();
        }
        if (waveID > 20 && waveID % 15 == 7) {
            spawnInvaderTop();
        }
    }
}
