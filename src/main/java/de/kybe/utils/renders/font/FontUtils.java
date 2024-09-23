package de.kybe.utils.renders.font;

import static de.kybe.constants.Globals.mc;

public class FontUtils {

    public static int getStringWidth(String s) {
        return mc.font.width(s);
    }

    public static int getStringHeight() {
        return mc.font.lineHeight;
    }

}
