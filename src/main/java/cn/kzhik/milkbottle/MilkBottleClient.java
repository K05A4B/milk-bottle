package cn.kzhik.milkbottle;

import cn.kzhik.milkbottle.client.ModPotionColorProvider;
import cn.kzhik.milkbottle.item.ModItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;


public class MilkBottleClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ColorProviderRegistry.ITEM.register(new ModPotionColorProvider(), ModItems.ANTIDOTE, ModItems.VACCINE);
    }
}

