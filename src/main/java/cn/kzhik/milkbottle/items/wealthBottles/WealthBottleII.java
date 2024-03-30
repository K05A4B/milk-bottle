package cn.kzhik.milkbottle.items.wealthBottles;

import cn.kzhik.milkbottle.utils.Mod;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class WealthBottleII extends WealthBottle {
    public WealthBottleII(Settings settings) {
        super(settings, 1, 10 * 20);
    }

    public static void register() {
        Identifier id = new Identifier(Mod.getModId(), WealthBottle.ITEM_NAME + "_ii");
        Registry.register(Registries.ITEM, id, new WealthBottleII(new FabricItemSettings()));
    }

    @Override
    public String getTranslationKey() {
        return "item.milk-bottle." + WealthBottle.ITEM_NAME;
    }
}
