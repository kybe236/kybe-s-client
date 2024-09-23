package de.kybe.utils.renders.render2d;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;

import static de.kybe.constants.Globals.mc;

public class Image {

    //Image
    public static void drawNormalImage(int x, int y, int width, int height, ResourceLocation texture) {
        mc.getTextureManager().bindForSetup(texture);

        RenderSystem.setShaderTexture(0, texture);
        RenderSystem.bindTexture(0);

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder builder = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

        RenderSystem.disableBlend();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);

        builder.addVertex(x, y + height, 0).setUv(0.0F, 1.0F);
        builder.addVertex(x + width, y + height, 0).setUv(1.0F, 1.0F);
        builder.addVertex(x + width, y, 0).setUv(1.0F, 0.0F);
        builder.addVertex(x, y, 0).setUv(0.0F, 0.0F);

        BufferUploader.drawWithShader(builder.buildOrThrow());
    }

    //Tinted Image
    public static void drawTintedImage(int x, int y, int width, int height, ResourceLocation texture, Color color) {
        mc.getTextureManager().bindForSetup(texture);

        RenderSystem.setShaderTexture(0, texture);
        RenderSystem.bindTexture(0);

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder builder = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);

        RenderSystem.disableBlend();
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);

        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        int alpha = color.getAlpha();

        builder.addVertex(x, y + height, 0).setUv(0.0F, 1.0F).setColor(red, green, blue, alpha);
        builder.addVertex(x + width, y + height, 0).setUv(1.0F, 1.0F).setColor(red, green, blue, alpha);
        builder.addVertex(x + width, y, 0).setUv(1.0F, 0.0F).setColor(red, green, blue, alpha);
        builder.addVertex(x, y, 0).setUv(0.0F, 0.0F).setColor(red, green, blue, alpha);

        BufferUploader.drawWithShader(builder.buildOrThrow());
        RenderSystem.disableBlend();
    }

}
