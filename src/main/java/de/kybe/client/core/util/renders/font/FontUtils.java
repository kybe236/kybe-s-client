package de.kybe.client.core.util.renders.font;

import static de.kybe.Kybe.mc;

public class FontUtils {

    public static int getStringWidth(String s) {
        return mc.font.width(s);
    }

    public static int getStringHeight() {
        return mc.font.lineHeight;
    }

}
