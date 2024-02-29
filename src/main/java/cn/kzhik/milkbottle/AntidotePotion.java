package cn.kzhik.milkbottle;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AntidotePotion extends PotionItem {

    private static final String MOD_ID = MilkBottle.MOD_ID;
    private StatusEffect type = null;
    private String name;

    public AntidotePotion(Settings settings, StatusEffect type, String name) {
        super(settings.maxCount(3));
        this.type = type;
        this.name = name;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (type != null) {
            user.removeStatusEffect(type);
        }

        return super.finishUsing(stack, world, user);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.milk-bottle." + name + ".tooltip"));
    }

    public static void registerPotion(String name, Settings settings, StatusEffect type) {
        Identifier id = new Identifier(MOD_ID, name);
        AntidotePotion item =  new AntidotePotion(settings, type, name);
        Registry.register(Registries.ITEM, id, item);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(content -> {
            content.add(item);
        });
    }
}
