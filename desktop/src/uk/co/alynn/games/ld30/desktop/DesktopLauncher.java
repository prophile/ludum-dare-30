package uk.co.alynn.games.ld30.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import uk.co.alynn.games.ld30.SpaceHams;
import uk.co.alynn.games.ld30.world.Constants;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Constants.STANDARD_RES_WIDTH;
		config.height = Constants.STANDARD_RES_HEIGHT;
		config.samples = 2;
		config.vSyncEnabled = true;
		config.title = "Space Hams";
		config.resizable = true;
		config.fullscreen = false;
		new LwjglApplication(new SpaceHams(), config);
	}
}
