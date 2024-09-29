package de.kybe.client.core.gui.components;

import de.kybe.Kybe;
import de.kybe.client.core.gui.GUI;
import de.kybe.client.core.gui.misc.TempColors;
import de.kybe.client.core.module.Module;
import de.kybe.client.core.module.ModuleCategory;
import de.kybe.client.core.module.ModuleManager;
import de.kybe.client.core.util.renders.render2d.Rect;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static de.kybe.Kybe.mc;
import static de.kybe.client.core.gui.components.MainComponent.categorySize;

public class CategoryComponent extends Component {

    public ModuleCategory category;
    public ModuleComponent current_module;

    public static int firstVisibleModuleIndex = 0;
    public static int maxVisibleModules;

    private static final int module_height = 15; //TODO make this load from config
    public static ArrayList<ModuleComponent> category_modules = new ArrayList<>();

    public CategoryComponent(int x, int y, int width, int height, ModuleCategory category) {
        super(x, y, width, height);
        this.category = category;
    }

    @Override
    public void mouseScrolled(double mouseX, double mouseY, double scrollAmount) {
        if(GUI.mainComponent.contains_module_area(mouseX,mouseY)) {
            int totalModules = category_modules.size();

            if (totalModules > maxVisibleModules) {
                if (scrollAmount > 0 && firstVisibleModuleIndex > 0) {
                    firstVisibleModuleIndex = Math.max(0, firstVisibleModuleIndex - 1);
                } else if (scrollAmount < 0 && firstVisibleModuleIndex < totalModules - maxVisibleModules) {
                    firstVisibleModuleIndex = Math.min(totalModules - maxVisibleModules, firstVisibleModuleIndex + 1);
                }

                adjustModuleComponents();
            }
        }
        for(ModuleComponent moduleComponent : category_modules) {
            moduleComponent.mouseScrolled(mouseX, mouseY, scrollAmount);
        }
    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {
        adjustModuleComponents();
        for (ModuleComponent moduleComponent : category_modules) {
            moduleComponent.mouseReleased(mouseX, mouseY, button);
        }
    }

    @Override
    public void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        adjustModuleComponents();
        for (ModuleComponent moduleComponent : category_modules) {
            moduleComponent.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        }
    }

    @Override
    public void drawScreen(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath(Kybe.MOD_ID, category.getIcon());

        Rect.drawOutlinedSquare(x, y, width, height, 1, TempColors.color_background_darker, TempColors.color_accent);
        if (MainComponent.current_category == this) {
            guiGraphics.drawString(mc.font, category.getName(), x + 3, y + 3, TempColors.color_accent.getRGB());
        } else {
            guiGraphics.drawString(mc.font, category.getName(), x + 3, y + 3, Color.white.getRGB());
        }

        if (MainComponent.current_category == this) {
            for (int i = 0; i < maxVisibleModules; i++) {
                int moduleIndex = firstVisibleModuleIndex + i;

                if (moduleIndex < 0 || moduleIndex >= category_modules.size()) {
                    break;
                }

                ModuleComponent module = category_modules.get(moduleIndex);
                module.drawScreen(guiGraphics, mouseX, mouseY, partialTicks);
            }
        }
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        adjustModuleComponents();
        for (ModuleComponent module : category_modules) {
            module.mouseClicked(mouseX, mouseY, button);

            if (isHovered((int) mouseX, (int) mouseY) && button == 1) {
                this.current_module = module;
                firstVisibleModuleIndex = 0;
            }
        }
        super.mouseClicked(mouseX, mouseY, button);
    }

    public void addModuleComponents() {
        this.category_modules.clear();

        List<Module> moduleList = ModuleManager.getModulesFromCategory(this.category);
        Collections.sort(moduleList, Comparator.comparing(Module::getName));

        for (Module m : moduleList) {
            ModuleComponent moduleComponent = new ModuleComponent(0, 0, 100, module_height, Color.BLUE, m);
            this.category_modules.add(moduleComponent);
        }

        if (current_module == null && !category_modules.isEmpty()) {
            current_module = category_modules.get(0);
            ModuleComponent.firstVisibleSettingIndex = 0;
        }

        adjustModuleComponents();
    }

    public void adjustModuleComponents() {
        int padding_h = 2;
        int padding_v = 2;

        if (GUI.mainComponent != null) {
            int x_pos = GUI.mainComponent.getX() + categorySize + padding_h;
            int y_pos = GUI.mainComponent.getY() + 15 + padding_v;

            maxVisibleModules = Math.max(1, (GUI.mainComponent.height - 15) / (module_height + padding_v));

            for (int i = 0; i < maxVisibleModules; i++) {
                int moduleIndex = firstVisibleModuleIndex + i;

                if (moduleIndex < 0 || moduleIndex >= category_modules.size()) {
                    break;
                }

                ModuleComponent module = category_modules.get(moduleIndex);
                module.setSize(MainComponent.workingSize / 3 - padding_h, module_height);
                module.setPosition(x_pos, y_pos);
                y_pos += module.height + padding_v;
            }
        }
    }
}
