package cn.kzhik.milkbottle.items;

import cn.kzhik.milkbottle.effects.ImmuneEffects;
import cn.kzhik.milkbottle.utils.Mod;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class HarmfulVaccine extends VaccinePotion {
    public HarmfulVaccine() {
        super(new FabricItemSettings(), ImmuneEffects.IMMUNE_HARMFUL, 20*60*8);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 20*6, 2));
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 20*60, 1));
        return super.finishUsing(stack, world, user);
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
