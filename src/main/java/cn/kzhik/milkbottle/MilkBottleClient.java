package cn.kzhik.milkbottle;

import cn.kzhik.milkbottle.client.ModPotionColorProvider;
import cn.kzhik.milkbottle.client.gui.screen.MedicineStoveScreen;
import cn.kzhik.milkbottle.item.ModItems;
import cn.kzhik.milkbottle.screen.ModScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;


public class MilkBottleClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ColorProviderRegistry.ITEM.register(new ModPotionColorProvider(), ModItems.ANTIDOTE, ModItems.VACCINE);
        ScreenRegistry.register(ModScreenHandlers.MEDICINE_STOVE_SCREEN_HANDLER, MedicineStoveScreen::new);
    }
}

