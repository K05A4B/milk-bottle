package cn.kzhik.milkbottle.block;

import cn.kzhik.milkbottle.utils.Mod;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static Block MEDICINE_STOVE = registerBlock(MedicineStove.BLOCK_ID, new MedicineStove(FabricBlockSettings.copyOf(Blocks.OBSIDIAN)));

    private static Block registerBlock(String id, Block entry) {
        return Registry.register(Registries.BLOCK, new Identifier(Mod.getModId(), id), entry);
    }

    public static void registerBlocks() {
        Mod.LOGGER.info("Registering blocks for milk-bottle mod");
    }
}
