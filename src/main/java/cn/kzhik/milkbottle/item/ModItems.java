package cn.kzhik.milkbottle.item;

import cn.kzhik.milkbottle.block.ModBlocks;
import cn.kzhik.milkbottle.utils.Mod;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static Item MILK_BOTTLE = registerItem("milk_bottle", new MilkBottle(new FabricItemSettings()));
    public static Item ANTIDOTE = registerItem("antidote", new Antidote(new FabricItemSettings()));
    public static Item VACCINE = registerItem("vaccine", new Vaccine(new FabricItemSettings()));
    public static Item MEDICINE_STOVE = registerItem("medicine_stove", new BlockItem(ModBlocks.MEDICINE_STOVE, new FabricItemSettings()));
    public ModItems() {
    }

    public static void registerItems() {
        Mod.LOGGER.info("Registering items for milk-bottle mod");
    }

    private static Item registerItem(String id, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(Mod.getModId(), id), item);
    }
}
