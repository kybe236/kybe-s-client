package de.kybe.client.core.util.renders.render2d;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.renderer.GameRenderer;

import java.awt.*;

import static de.kybe.client.core.util.renders.render2d.Circle.drawSector;

public class Rect {

	//Square
	public static void drawSquare(int x, int y, int width, int height, Color color) {
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.setShader(GameRenderer::getPositionColorShader);

		Tesselator tesselator = Tesselator.getInstance();
		BufferBuilder builder = tesselator.getBuilder();
		builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

		// Define the four corners of the rectangle (clockwise order)
		builder.vertex(x, y + height, 0)
				.color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())
				.endVertex();

		builder.vertex(x + width, y + height, 0)
				.color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())
				.endVertex();

		builder.vertex(x + width, y, 0)
				.color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())
				.endVertex();

		builder.vertex(x, y, 0)
				.color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())
				.endVertex();

		tesselator.end();

		RenderSystem.disableBlend();
	}


	//Outlined square
	public static void drawOutlinedSquare(int x, int y, int width, int height, int outlineWidth, Color fillColor, Color outlineColor) {
		drawSquare(x + outlineWidth, y + outlineWidth, width - 2 * outlineWidth, height - 2 * outlineWidth, fillColor);

		drawSquare(x, y, width, outlineWidth, outlineColor);
		drawSquare(x, y + height - outlineWidth, width, outlineWidth, outlineColor);
		drawSquare(x, y + outlineWidth, outlineWidth, height - 2 * outlineWidth, outlineColor);
		drawSquare(x + width - outlineWidth, y + outlineWidth, outlineWidth, height - 2 * outlineWidth, outlineColor);
	}

	//rounded
	public static void drawRoundedSquare(int x, int y, int width, int height, int cornerRadius, int precision, Color color) {
		drawMiddle(x, y, width, height, cornerRadius, precision, color);
		drawTop(x, y, width, height, cornerRadius, precision, color);
		drawBottom(x, y, width, height, cornerRadius, precision, color);

		//TODO: fix the corners only rendering 45 degrees so transparent colors look good.
		//corners
		drawSector(x + cornerRadius, y + height - cornerRadius, cornerRadius, 0, Math.PI * 2, precision, color); // bottom left
		drawSector(x + width - cornerRadius, y + height - cornerRadius, cornerRadius, 0, Math.PI * 2, precision, color); // bottom right
		drawSector(x + width - cornerRadius, y + cornerRadius, cornerRadius, 0, Math.PI * 2, precision, color); // top right
		drawSector(x + cornerRadius, y + cornerRadius, cornerRadius, 0, Math.PI * 2, precision, color); // top left
	}

	//outlined rounded
	public static void drawOutlinedRoundedSquare(int x, int y, int width, int height, int cornerRadius, int precision, int outlineWidth, Color color, Color outlinecolor) {
		drawRoundedSquare(x, y, width, height, cornerRadius, precision, outlinecolor);
		drawRoundedSquare(x + outlineWidth, y + outlineWidth, width - 2 * outlineWidth, height - 2 * outlineWidth, cornerRadius, precision, color);
	}

	//helper for rounded
	private static void drawMiddle(int x, int y, int width, int height, int cornerRadius, int precision, Color color) {
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.setShader(GameRenderer::getPositionColorShader);

		Tesselator tesselator = Tesselator.getInstance();
		BufferBuilder builder = tesselator.getBuilder();

		builder.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);

		builder.vertex(x, y + height - cornerRadius, 0)
				.color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())
				.endVertex();

		builder.vertex(x + width, y + height - cornerRadius, 0)
				.color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())
				.endVertex();

		builder.vertex(x + width, y + cornerRadius, 0)
				.color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())
				.endVertex();

		builder.vertex(x, y + cornerRadius, 0)
				.color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())
				.endVertex();

		tesselator.end();

		RenderSystem.disableBlend();
	}

	//helper for rounded
	private static void drawTop(int x, int y, int width, int height, int cornerRadius, int precision, Color color) {
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.setShader(GameRenderer::getPositionColorShader);

		Tesselator tesselator = Tesselator.getInstance();
		BufferBuilder builder = tesselator.getBuilder();
		builder.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);

		builder.vertex(x + cornerRadius, y, 0)
				.color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())
				.endVertex();

		builder.vertex(x + cornerRadius, y + cornerRadius, 0)
				.color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())
				.endVertex();

		builder.vertex(x + width - cornerRadius, y + cornerRadius, 0)
				.color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())
				.endVertex();

		builder.vertex(x + width - cornerRadius, y, 0)
				.color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())
				.endVertex();

		tesselator.end();

		RenderSystem.disableBlend();
	}

	//helper for rounded
	private static void drawBottom(int x, int y, int width, int height, int cornerRadius, int precision, Color color) {
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.setShader(GameRenderer::getPositionColorShader);

		Tesselator tesselator = Tesselator.getInstance();
		BufferBuilder builder = tesselator.getBuilder();
		builder.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);

		builder.vertex(x + cornerRadius, y + height - cornerRadius, 0)
				.color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())
				.endVertex();

		builder.vertex(x + cornerRadius, y + height, 0)
				.color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())
				.endVertex();

		builder.vertex(x + width - cornerRadius, y + height, 0)
				.color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())
				.endVertex();

		builder.vertex(x + width - cornerRadius, y + height - cornerRadius, 0)
				.color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())
				.endVertex();

		tesselator.end();

		RenderSystem.disableBlend();
	}
}
