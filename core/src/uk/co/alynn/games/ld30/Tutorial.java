package uk.co.alynn.games.ld30;

public class Tutorial {
    private int m_tutorialStage = 0;
    // stage 0: need to latch
    // stage 1: need to unlatch
    // stage 2: need to shoot
    // stage 3: done

    public void update(float deltaTime) {
    }

    public boolean isFinished() {
        return m_tutorialStage == 3;
    }

    public boolean noBulletsYet() {
        return m_tutorialStage < 2;
    }

    public void didShoot() {
        if (m_tutorialStage == 2) {
            m_tutorialStage = 3;
        }
    }

    public void didLatchToPlanet() {
        if (m_tutorialStage == 0) {
            m_tutorialStage = 1;
        }
    }

    public void didUnlatch() {
        if (m_tutorialStage == 1) {
            m_tutorialStage = 2;
        }
    }

    public void render(Renderer renderer) {
        switch (m_tutorialStage) {
        case 0:
            renderer.text("Right-click to latch to a planet", 10, 16, 1.0f);
            break;
        case 1:
            renderer.text("Right-click again to unlatch", 10, 16, 1.0f);
            break;
        case 2:
            renderer.text("Left-click to shoot", 10, 16, 1.0f);
            break;
        default:
            break;
        }
    }

}
