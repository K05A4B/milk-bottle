package cn.kzhik.milkbottle.client.gui.screen;

import cn.kzhik.milkbottle.screen.MedicineStoveScreenHandler;
import cn.kzhik.milkbottle.utils.Constants;
import cn.kzhik.milkbottle.utils.Mod;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

// 参考: https://fabricmc.net/wiki/tutorial:screenhandler
public class MedicineStoveScreen extends HandledScreen<ScreenHandler> {

    private static final Identifier TEXTURE = new Identifier(Mod.getModId(), "textures/gui/medicine_stove_gui.png");

    public MedicineStoveScreen(ScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);

        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
    }

    public void updateProgress(DrawContext context) {
        int u = 0;
        int v = 189;
        int maxWidth = 89;

        double widthDelta = (double) maxWidth / Constants.MEDICINE_STOVE_DELAY;

        MedicineStoveScreenHandler screenHandler = getScreenHandler();
        int waitingTick = screenHandler.getWaitingTick();

        int width = (int) (maxWidth - Math.round(waitingTick * widthDelta));
        if (width < 0 || width >= maxWidth) {
            width = 0;
        }

        context.drawTexture(TEXTURE, x + 45, y + 72, u, v, width, 9);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        updateProgress(context);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    @Override
    protected void init() {
        super.init();
        super.titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;

        super.backgroundWidth = 176;
        super.backgroundHeight = 189;

        super.playerInventoryTitleY = 95;
    }

    @Override
    public MedicineStoveScreenHandler getScreenHandler() {
        return (MedicineStoveScreenHandler) super.getScreenHandler();
    }
}
