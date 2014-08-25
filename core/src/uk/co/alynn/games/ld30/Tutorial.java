package uk.co.alynn.games.ld30;

import uk.co.alynn.games.ld30.world.Constants;

public class Tutorial {
    private int m_tutorialStage = 0;
    private float m_timeSinceSwitch = 0.0f;
    // stage 0: need to latch
    // stage 1: need to unlatch
    // stage 2: need to shoot
    // stage 3: done
    
    private final static float TEXT_FADE_TIME = 0.3f;
    private final static float GL_TIME = 6.0f;

    public void update(float deltaTime) {
        m_timeSinceSwitch += deltaTime;
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
            m_timeSinceSwitch = 0.0f;
        }
    }

    public void didLatchToPlanet() {
        if (m_tutorialStage == 0) {
            m_tutorialStage = 1;
            m_timeSinceSwitch = 0.0f;
        }
    }

    public void didUnlatch() {
        if (m_tutorialStage == 1) {
            m_tutorialStage = 2;
            m_timeSinceSwitch = 0.0f;
        }
    }

    public void render(Renderer renderer) {
        float alpha;
        int stageText = m_tutorialStage;
        if (m_timeSinceSwitch > (2*TEXT_FADE_TIME)) {
            alpha = 1.0f;
        } else if (m_timeSinceSwitch > TEXT_FADE_TIME) {
            alpha = (m_timeSinceSwitch - TEXT_FADE_TIME) / TEXT_FADE_TIME;
        } else {
            alpha = (TEXT_FADE_TIME - m_timeSinceSwitch) / TEXT_FADE_TIME;
            --stageText;
        }
        
        if (stageText == 3 && m_timeSinceSwitch > TEXT_FADE_TIME + GL_TIME) {
            float displayTime = m_timeSinceSwitch - TEXT_FADE_TIME - GL_TIME;
            if (displayTime < TEXT_FADE_TIME) {
                alpha = (TEXT_FADE_TIME - displayTime) / TEXT_FADE_TIME;
            } else {
                alpha = 0.0f;
            }
        }
        
        String displayedText;
        switch (stageText) {
        case 0:
            displayedText = "Hold right-click to latch to a planet";
            break;
        case 1:
            displayedText = "Release to unlatch";
            break;
        case 2:
            displayedText = "Left-click to shoot";
            break;
        case 3:
            displayedText = "Protect these worlds. Good luck!";
            break;
        default:
            displayedText = "";
            break;
        }
        renderer.text(displayedText, Constants.STANDARD_RES_WIDTH / 2, (1 * Constants.STANDARD_RES_HEIGHT) / 4, alpha);
    }

}
