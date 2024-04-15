package cn.kzhik.milkbottle.client.gui.screen;

import cn.kzhik.milkbottle.screen.MedicineStoveScreenHandler;
import cn.kzhik.milkbottle.utils.Constants;
import cn.kzhik.milkbottle.utils.Mod;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.lang.reflect.Method;

// 参考: https://fabricmc.net/wiki/tutorial:screenhandler
public class MedicineStoveScreen extends HandledScreen<ScreenHandler> {

    private static final Identifier TEXTURE = new Identifier(Mod.getModId(), "textures/gui/medicine_stove_gui.png");

    public MedicineStoveScreen(ScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
    }

    // 修复 >=1.20.2 的版本找不到 renderBackground 的情况
    // >=1.20.2的HandledScreen删除了renderBackground方法
    @Override
    public void renderBackground(DrawContext context) {
        Method[] method = super.getClass().getDeclaredMethods();
        for (Method value : method) {
            String methodName = value.getName();

            // 修复 <=1.20.1 的背景透明问题
            // 实际环境中renderBackground叫method_25420, renderBackground 是反编译后的结果
            if (methodName.equals("renderBackground") || methodName.equals("method_25420")) {
                super.renderBackground(context);
            }
        }
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
