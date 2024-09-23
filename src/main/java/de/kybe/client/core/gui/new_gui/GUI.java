package de.kybe.client.core.gui.new_gui;

import de.kybe.client.core.gui.new_gui.Components.ScreenSelector;
import de.kybe.client.core.gui.new_gui.colors.Colors;
import de.kybe.client.core.gui.new_gui.enums.AvailableScreens;
import de.kybe.client.core.gui.new_gui.screens.MainWindow;
import de.kybe.client.core.gui.gui.Gui;

import de.kybe.client.core.util.renders.render2d.Rect;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.awt.*;
import java.util.Arrays;

import static de.kybe.Kybe.mc;


public class GUI extends Screen {

    int screenWidth;
    int screenHeight;
    
    MainWindow mainWindow;
    ScreenSelector selector;

    public GUI() {
        super(Component.literal("GUI"));
        Gui.loadSettings();

        screenHeight = mc.getWindow().getGuiScaledHeight();
        screenWidth = mc.getWindow().getGuiScaledWidth();

        mainWindow = new MainWindow(100, 100, 600, 400, 400, 200, 800, 500);
        selector = new ScreenSelector(Arrays.asList(AvailableScreens.values()));
    }

    @Override
    public void onClose() {
        super.onClose();
        Gui.saveSettings();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        // always render selector
        selector.drawSelector(guiGraphics, this.font, 10, Colors.color_accent, Colors.color_background, Colors.color_accent);

        if(selector.getSelectedScreen() == AvailableScreens.CLICKGUI) {
            // clickgui window
            mainWindow.draw(guiGraphics, Colors.color_background, Colors.color_accent);
        } else if (selector.getSelectedScreen() == AvailableScreens.HUD) {
            // example another screen getting rendered
            Rect.drawSquare(10, 10, 200, 200, Color.BLUE);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        // always handle selector
        selector.handleMouseClick((int) mouseX, (int) mouseY, 10);

        if(selector.getSelectedScreen() == AvailableScreens.CLICKGUI) {
            // clickgui
            mainWindow.handleMouseClick((int) mouseX, (int) mouseY, button);
        }

        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        // main
        if (selector.getSelectedScreen() == AvailableScreens.CLICKGUI) {
            mainWindow.handleMouseRelease((int) mouseX, (int) mouseY, button);
        }

        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        // main
        if (selector.getSelectedScreen() == AvailableScreens.CLICKGUI) {
            mainWindow.handleMouseDrag((int) mouseX, (int) mouseY, button, screenWidth, screenHeight);
        }

        return false;
    }
}
