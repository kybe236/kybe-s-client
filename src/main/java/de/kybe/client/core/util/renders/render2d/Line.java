package de.kybe.client.core.util.renders.render2d;

public class Line {

    /* Doesnt work idk
    public static void drawLine(int x1, int y1, int x2, int y2, int width, Color color) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableDepthTest();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        RenderSystem.lineWidth(width);

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder builder = tesselator.begin(VertexFormat.Mode.LINES, DefaultVertexFormat.POSITION_COLOR);

        builder.addVertex(x1, y1, 0).setColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        builder.addVertex(x2, y2, 0).setColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());

        BufferUploader.drawWithShader(builder.buildOrThrow());

        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
    }*/

}
