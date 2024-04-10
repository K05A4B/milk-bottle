package cn.kzhik.milkbottle;

import cn.kzhik.milkbottle.block.ModBlocks;
import cn.kzhik.milkbottle.block.entity.ModEntityBlockType;
import cn.kzhik.milkbottle.effects.ModEffects;
import cn.kzhik.milkbottle.item.ModItems;
import cn.kzhik.milkbottle.item.groups.MilkBottleGroup;
import cn.kzhik.milkbottle.utils.Mod;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MilkBottle implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger(Mod.getModId());

    @Override
	public void onInitialize() {
        LOGGER.info("Initializing milk-bottle mod");
        ModEffects.registerEffects();
        MilkBottleGroup.registerGroup();
        ModItems.registerItems();
        ModEntityBlockType.registerEntityBlockType();
        ModBlocks.registerBlocks();
    }
}