package cn.kzhik.milkbottle;

import cn.kzhik.milkbottle.block.ModBlocks;
import cn.kzhik.milkbottle.block.entity.ModEntityBlockType;
import cn.kzhik.milkbottle.effects.ModEffects;
import cn.kzhik.milkbottle.item.ModItems;
import cn.kzhik.milkbottle.item.groups.MilkBottleGroup;
import cn.kzhik.milkbottle.utils.Mod;
import net.fabricmc.api.ModInitializer;

public class MilkBottle implements ModInitializer {

    @Override
    public void onInitialize() {
        Mod.LOGGER.info("Initializing milk-bottle mod");
        ModEffects.registerEffects();
        MilkBottleGroup.registerGroup();
        ModItems.registerItems();
        ModEntityBlockType.registerEntityBlockType();
        ModBlocks.registerBlocks();
    }
}