package de.kybe.client.core.util.renders.render2d;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;

import static de.kybe.Kybe.mc;

@SuppressWarnings("unused")
public class Image {

	//Image
	public static void drawNormalImage(int x, int y, int width, int height, ResourceLocation texture) {
		mc.getTextureManager().bindForSetup(texture);

		RenderSystem.setShaderTexture(0, texture);
		RenderSystem.bindTexture(0);

		Tesselator tesselator = Tesselator.getInstance();
		BufferBuilder builder = tesselator.getBuilder();
		builder.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);

		RenderSystem.disableBlend();
		RenderSystem.setShader(GameRenderer::getPositionTexShader);

		builder.vertex(x, y + height, 0)
				.uv(0.0F, 1.0F)
				.endVertex();

		builder.vertex(x + width, y + height, 0)
				.uv(1.0F, 1.0F)
				.endVertex();

		builder.vertex(x + width, y, 0)
				.uv(1.0F, 0.0F)
				.endVertex();

		builder.vertex(x, y, 0)
				.uv(0.0F, 0.0F)
				.endVertex();


		BufferUploader.drawWithShader(builder.end());
	}

	//Tinted Image
	public static void drawTintedImage(int x, int y, int width, int height, ResourceLocation texture, Color color) {
		mc.getTextureManager().bindForSetup(texture);

		RenderSystem.setShaderTexture(0, texture);
		RenderSystem.bindTexture(0);

		Tesselator tesselator = Tesselator.getInstance();
		BufferBuilder builder = tesselator.getBuilder();
		builder.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);

		RenderSystem.disableBlend();
		RenderSystem.setShader(GameRenderer::getPositionTexColorShader);

		int red = color.getRed();
		int green = color.getGreen();
		int blue = color.getBlue();
		int alpha = color.getAlpha();

		builder.vertex(x, y + height, 0).uv(0.0F, 1.0F)
				.color(red, green, blue, alpha)
				.endVertex();

		builder.vertex(x + width, y + height, 0).uv(1.0F, 1.0F)
				.color(red, green, blue, alpha)
				.endVertex();

		builder.vertex(x + width, y, 0).uv(1.0F, 0.0F)
				.color(red, green, blue, alpha)
				.endVertex();

		builder.vertex(x, y, 0).uv(0.0F, 0.0F)
				.color(red, green, blue, alpha)
				.endVertex();

		tesselator.end();

		RenderSystem.disableBlend();
	}

}
