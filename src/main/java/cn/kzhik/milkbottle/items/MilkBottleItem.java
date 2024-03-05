package cn.kzhik.milkbottle.items;
import cn.kzhik.milkbottle.utils.Mod;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MilkBottleItem extends PotionItem {

    public static final String MOD_ID = Mod.getModId();
    public static final String MOD_NAME = "milk_bottle";

    public MilkBottleItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        user.clearStatusEffects();
        return super.finishUsing(stack, world, user);
    }

    public static void registerItem() {
        FabricItemSettings settings = new FabricItemSettings();

        settings.maxCount(12);

        Identifier id = new Identifier(MOD_ID, MOD_NAME);
        MilkBottleItem item = new MilkBottleItem(settings);
        Registry.register(Registries.ITEM, id, item);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(content -> {
            content.add(item);
        });
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.milk-bottle.milk_bottle.tooltip"));
    }
}
