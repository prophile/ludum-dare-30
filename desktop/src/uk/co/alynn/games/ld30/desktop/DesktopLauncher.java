package uk.co.alynn.games.ld30.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import uk.co.alynn.games.ld30.SpaceHams;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1152;
		config.height = 648;
		config.samples = 2;
		config.vSyncEnabled = true;
		config.title = "Space Hams";
		config.resizable = false;
		new LwjglApplication(new SpaceHams(), config);
	}
}
