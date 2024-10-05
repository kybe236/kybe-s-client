package de.kybe.client.core.util.renders.render2d;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.renderer.GameRenderer;

import java.awt.*;

public class Circle {

	//part
	public static void drawSector(int x, int y, int r, double startAngle, double endAngle, int precision, Color color) {
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.setShader(GameRenderer::getPositionColorShader);

		Tesselator tesselator = Tesselator.getInstance();
		BufferBuilder builder = tesselator.getBuilder();

		builder.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);

		builder.vertex(x, y, 0)
				.color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())
				.endVertex();

		double precisionAngle = 2 * Math.PI / precision;
		for (int i = (int) (endAngle / precisionAngle) + 1; i >= (int) (startAngle / precisionAngle); i--) {
			builder.vertex((float) (x + r * Math.cos(i * precisionAngle)), (float) (y + r * Math.sin(i * precisionAngle)), 0)
					.color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())
					.endVertex();
		}

		tesselator.end();

		RenderSystem.disableBlend();
	}

	//Circle
	public static void drawCircle(int x, int y, int r, int precision, Color color) {
		drawSector(x, y, r, 0, 2 * Math.PI, precision, color);
	}

	//Outlined Circle
	public static void drawOutlinedCircle(int x, int y, int r, int outlineWidth, int precision, Color fillColor, Color outlineColor) {
		if (fillColor.getAlpha() > 0) {
			drawCircle(x, y, r - outlineWidth, precision, fillColor);
		}

		drawRing(x, y, r - outlineWidth, r, precision, outlineColor);
	}

	//Ring
	private static void drawRing(int x, int y, int innerRadius, int outerRadius, int precision, Color color) {
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.setShader(GameRenderer::getPositionColorShader);

		Tesselator tesselator = Tesselator.getInstance();
		BufferBuilder builder = tesselator.getBuilder();

		builder.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);

		double precisionAngle = 2 * Math.PI / precision;
		for (int i = 0; i <= precision; i++) {
			double angle = i * precisionAngle;

			//Outer
			builder.vertex(
					(float) (x + outerRadius * Math.cos(angle)),
					(float) (y + outerRadius * Math.sin(angle)),
					0
					)
					.color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())
					.endVertex();

			//Inner
			builder.vertex(
					(float) (x + innerRadius * Math.cos(angle)),
					(float) (y + innerRadius * Math.sin(angle)),
					0
				)
					.color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())
					.endVertex();
		}

		BufferUploader.drawWithShader(builder.end());
		RenderSystem.disableBlend();
	}
}
