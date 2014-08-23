package uk.co.alynn.games.ld30.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import uk.co.alynn.games.ld30.SpaceHams;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1024;
		config.height = 768;
		config.samples = 2;
		config.vSyncEnabled = true;
		config.title = "Space Hams";
		new LwjglApplication(new SpaceHams(), config);
	}
}
