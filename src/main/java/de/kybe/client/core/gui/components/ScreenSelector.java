package de.kybe.client.core.gui.components;

import de.kybe.client.core.gui.enums.AvailableScreens;
import de.kybe.client.core.util.renders.render2d.Rect;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

import java.awt.*;
import java.util.List;

import static de.kybe.Kybe.mc;

public class ScreenSelector {

    private List<AvailableScreens> options;
    private int selected;
    private final int screenWidth;
    private final int width = 80;
    private final int height = 15;
    private final int gap = 4;

    public ScreenSelector(List<AvailableScreens> options) {
        this.options = options;
        this.selected = 0;
        this.screenWidth = mc.getWindow().getGuiScaledWidth();
    }

    public void drawSelector(GuiGraphics guiGraphics, Font font, int yPos, Color selectedColor, Color unselectedColor, Color borderColor) {
        int totalWidth = options.size() * (width + gap);
        int xStart = (screenWidth / 2) - (totalWidth / 2);

        //render background
        Rect.drawOutlinedRoundedSquare(xStart-2, yPos-2, totalWidth, height+4, 5, 50, 1, unselectedColor, borderColor);

        //render each element
        for (int i = 0; i < options.size(); i++) {
            int xPos = xStart + i * (width + gap);
            boolean isSelected = (i == selected);
            Color color = isSelected ? selectedColor : unselectedColor;

            Rect.drawOutlinedRoundedSquare(xPos, yPos, width, height, 5, 50, 1, color, borderColor);

            String text = options.get(i).name();
            guiGraphics.drawCenteredString(font, text, xPos + width / 2, yPos + height / 2 - 4, Color.WHITE.getRGB());
        }
    }

    public void handleMouseClick(int mouseX, int mouseY, int yPos) {
        int totalWidth = options.size() * (width + gap);
        int xStart = (screenWidth / 2) - (totalWidth / 2);

        for (int i = 0; i < options.size(); i++) {
            int xPos = xStart + i * (width + gap);

            if (mouseX >= xPos && mouseX <= xPos + width && mouseY >= yPos && mouseY <= yPos + height) {
                selected = i;
                return;
            }
        }
    }

    public AvailableScreens getSelectedScreen() {
        return options.get(selected);
    }
}
