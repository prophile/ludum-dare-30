package uk.co.alynn.games.ld30.desktop;

import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;

public class PackTextures {

    public static void main(String[] args) {
        Settings settings = new Settings();
        settings.useIndexes = false;
        settings.pot = true;
        settings.filterMag = TextureFilter.Linear;
        settings.filterMin = TextureFilter.MipMapLinearLinear;
        settings.rotation = false;
        settings.stripWhitespaceX = false;
        settings.stripWhitespaceY = false;
        TexturePacker.process(settings , "../core/raw-sprites", "../core/assets/atlases", "sprites.atlas");
    }

}
