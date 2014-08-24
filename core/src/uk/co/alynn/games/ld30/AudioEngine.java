package uk.co.alynn.games.ld30;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class AudioEngine {
    private final Map<String, Sound> m_fx = new HashMap<String, Sound>();
    private static AudioEngine s_sharedEngine = null;
    
    public AudioEngine() {
        loadEffect("shoot", "kick.wav");
        loadEffect("hit", "snare.wav");
        loadEffect("invader-warn", "meepmerp.wav");
        loadEffect("tractor", "tractor.wav");
    }
    
    private void loadEffect(String name, String file) {
        Sound effect = Gdx.audio.newSound(Gdx.files.internal("sfx/" + file));
        m_fx.put(name, effect);
    }

    public void play(String effect) {
        Sound fx = m_fx.get(effect);
        if (effect.equals("tractor")) {
            fx.play(0.1f);
        } else {
            fx.play();
        }
    }
    
    public void stopAll(String effect) {
        Sound fx = m_fx.get(effect);
        fx.stop();
    }
    
    public static AudioEngine get() {
        if (s_sharedEngine == null) {
            s_sharedEngine = new AudioEngine();
        }
        return s_sharedEngine;
    }
}