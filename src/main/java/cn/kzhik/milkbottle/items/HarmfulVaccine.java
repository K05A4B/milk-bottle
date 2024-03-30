package cn.kzhik.milkbottle.items;

import cn.kzhik.milkbottle.effects.Effects;
import cn.kzhik.milkbottle.utils.Mod;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HarmfulVaccine extends VaccinePotion {
    public HarmfulVaccine() {
        super(new FabricItemSettings(), Effects.IMMUNE_HARMFUL, 20 * 60 * 8);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 20*6, 2));
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 20*60, 1));
        return super.finishUsing(stack, world, user);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.milk-bottle.harmful_vaccine.effect1").formatted(Formatting.AQUA));
        tooltip.add(Text.translatable("item.milk-bottle.harmful_vaccine.effect2").formatted(Formatting.AQUA));
    }

    public static HarmfulVaccine register() {
        Identifier id = new Identifier(Mod.getModId(), "harmful_vaccine");
        HarmfulVaccine item = new HarmfulVaccine();

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(content -> {
            content.add(item);
        });

        return Registry.register(Registries.ITEM, id, item);
    }
}
