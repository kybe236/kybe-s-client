package de.kybe.client.core.gui.components;

import de.kybe.Kybe;
import de.kybe.client.core.gui.misc.TempColors;
import de.kybe.client.core.module.ModuleCategory;
import de.kybe.client.core.util.renders.font.FontUtils;
import de.kybe.client.core.util.renders.render2d.Rect;
import net.minecraft.client.gui.GuiGraphics;

import java.awt.*;
import java.util.ArrayList;

import static de.kybe.Kybe.mc;

public class MainComponent extends Component {

	public static ArrayList<CategoryComponent> categoryComponents = new ArrayList<>();
	public static CategoryComponent current_category;
	public static int categorySize;
	public static int workingSize;
	private boolean dragging;
	private boolean scaling;
	private final int min_width;
	private final int min_height;
	private final int max_width;
	private final int max_height;
	private double dragOffsetX, dragOffsetY;
	private double scaleOffsetX, scaleOffsetY;
	private final int screenWidth;
	private final int screenHeight;

	public MainComponent(int x, int y, int width, int height) {
		super(x, y, width, height);
		this.dragging = false;
		this.scaling = false;

		this.min_width = 300;
		this.min_height = 200;
		this.max_width = 700;
		this.max_height = 500;

		this.screenWidth = mc.screen.width;
		this.screenHeight = mc.screen.height;
	}

	public void init() {
		//add category components and adjust position
		addCategoryComponents();
		adjustCategoryComponents();

		//TODO: replace this with a config check and see what category the player had open, fallback to this if that fails
		if (current_category == null && !categoryComponents.isEmpty()) {
			current_category = categoryComponents.get(0);
		}

		//add module components and adjust position
		for (CategoryComponent categoryComponent : categoryComponents) {
			categoryComponent.addModuleComponents();
		}
	}

	@Override
	public void setSize(int width, int height) {
		this.width = Math.max(min_width, Math.min(width, max_width));
		this.height = Math.max(min_height, Math.min(height, max_height));
	}

	@Override
	public void drawScreen(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		//main and top rect + (client name)
		Rect.drawOutlinedSquare(x, y, width, height, 1, TempColors.color_background, TempColors.color_accent);
		Rect.drawOutlinedSquare(x, y, width, 15, 1, TempColors.color_background_darker, TempColors.color_accent);
		guiGraphics.drawString(mc.font, Kybe.CLIENT_NAME + " " + Kybe.CLIENT_VERSION, x + (15 - FontUtils.getStringHeight()) / 2, y + (15 - FontUtils.getStringHeight()) / 2, Color.WHITE.getRGB());

		//dragable corner
		Rect.drawOutlinedSquare(x + width - 10, y + height - 10, 10, 10, 1, TempColors.color_background, TempColors.color_accent);

		// Render the module area (left 1/3)
		Rect.drawSquare(x + categorySize + 1, y + 15, (width - categorySize + 1) / 3, height - 15 - 1, TempColors.color_background_darker);

		// No need to render setting area but thats the other 2/3 of the remaining area

		//draw category components
		for (CategoryComponent component : MainComponent.categoryComponents) {
			component.drawScreen(guiGraphics, mouseX, mouseY, partialTicks);
		}
	}

	@Override
	public void mouseClicked(double mouseX, double mouseY, int button) {
		if (button == 0 && contains_dragarea(mouseX, mouseY)) {
			dragging = true;
			dragOffsetX = mouseX - x;
			dragOffsetY = mouseY - y;
		}

		if (button == 0 && contains_resizable_corner(mouseX, mouseY)) {
			scaling = true;
			scaleOffsetX = mouseX - (x + width);
			scaleOffsetY = mouseY - (y + height);
		}

		//select category when clicked
		for (CategoryComponent categoryComponent : categoryComponents) {
			if (categoryComponent.isHovered((int) mouseX, (int) mouseY) && button == 0) {
				current_category = categoryComponent;
				current_category.addModuleComponents();
				CategoryComponent.firstVisibleModuleIndex = 0;
			}
			categoryComponent.mouseClicked(mouseX, mouseY, button);
		}
	}

	@Override
	public void mouseReleased(double mouseX, double mouseY, int button) {
		if (button == 0) {
			dragging = false;
			scaling = false;
		}
		adjustCategoryComponents();

		for (CategoryComponent categoryComponent : categoryComponents) {
			categoryComponent.mouseReleased(mouseX, mouseY, button);
		}
	}

	@Override
	public void mouseScrolled(double mouseX, double mouseY, double scrollAmount) {
		for (CategoryComponent categoryComponent : categoryComponents) {
			categoryComponent.mouseScrolled(mouseX, mouseY, scrollAmount);
		}
	}


	@Override
	public void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
		if (dragging && button == 0) {
			setPosition((int) (mouseX - dragOffsetX), (int) (mouseY - dragOffsetY));
			snapToScreenEdges(screenWidth, screenHeight);
		}

		if (scaling && button == 0) {
			int newWidth = (int) (mouseX - x - scaleOffsetX);
			int newHeight = (int) (mouseY - y - scaleOffsetY);

			if (x + newWidth <= screenWidth) {
				setSize(newWidth, height);
			}

			if (y + newHeight <= screenHeight) {
				setSize(width, newHeight);
			}

			snapToScreenEdges(screenWidth, screenHeight);
		}
		adjustCategoryComponents();

		for (CategoryComponent categoryComponent : categoryComponents) {
			categoryComponent.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
		}
	}

	public boolean contains_setting_area(double mouseX, double mouseY) {
		int contentYStart = y + 15;
		int moduleAreaWidth = (workingSize) / 3;
		int settingAreaStartX = x + categorySize + 1 + moduleAreaWidth;

		return mouseX >= settingAreaStartX && mouseX <= x + width && mouseY >= contentYStart && mouseY <= y + height;
	}


	public boolean contains_module_area(double mouseX, double mouseY) {
		int contentYStart = y + 15;
		int moduleAreaWidth = (workingSize) / 3;

		return mouseX >= x + categorySize + 1 && mouseX <= x + categorySize + 1 + moduleAreaWidth && mouseY >= contentYStart && mouseY <= y + height;
	}


	public boolean contains_dragarea(double mouseX, double mouseY) {
		return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + 15;
	}

	public boolean contains_resizable_corner(double mouseX, double mouseY) {
		return mouseX >= x + width - 15 && mouseX <= x + width && mouseY >= y + height - 10 && mouseY <= y + height;
	}

	public void snapToScreenEdges(int screenWidth, int screenHeight) {
		if (x < 0) {
			x = 0;
		}
		if (y < 0) {
			y = 0;
		}
		if (x + width > screenWidth) {
			x = screenWidth - width;
		}
		if (y + height > screenHeight) {
			y = screenHeight - height;
		}
	}

	public void addCategoryComponents() {
		categoryComponents.clear();
		for (ModuleCategory category : ModuleCategory.values()) {
			CategoryComponent c = new CategoryComponent(0, 0, 0, 0, category);
			categoryComponents.add(c);
		}
	}

	public void adjustCategoryComponents() {
		int numberOfCategories = categoryComponents.size();
		double categoryHeight = (double) (height - 15) / numberOfCategories;
		categorySize = (int) categoryHeight;
		workingSize = width - categorySize;

		for (int i = 0; i < numberOfCategories; i++) {
			int categoryY = (int) (y + 15 + categoryHeight * i);
			categoryComponents.get(i).setPosition(x, categoryY);
			categoryComponents.get(i).setSize((int) categoryHeight, (int) categoryHeight);
		}
	}
}