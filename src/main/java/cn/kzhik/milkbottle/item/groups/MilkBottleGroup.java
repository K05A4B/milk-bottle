package cn.kzhik.milkbottle.item.groups;

import cn.kzhik.milkbottle.item.ModItems;
import cn.kzhik.milkbottle.utils.Constants;
import cn.kzhik.milkbottle.utils.Mod;
import cn.kzhik.milkbottle.utils.potion.ModPotionData;
import cn.kzhik.milkbottle.utils.potion.ModPotionEffect;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

public class MilkBottleGroup {
    public static ItemGroup MILK_BOTTLE = FabricItemGroup.builder()
            .displayName(Text.translatable("item.milk-bottle.milk_bottle"))
            .icon(() -> new ItemStack(ModItems.MILK_BOTTLE))
            .entries(MilkBottleGroup::addItems)
            .build();

    private static void addItems(ItemGroup.DisplayContext context, ItemGroup.Entries entries) {
        entries.add(ModItems.MILK_BOTTLE);
        ArrayList<ItemStack> antidoteList = new ArrayList<>();
        ArrayList<ItemStack> vaccineList = new ArrayList<>();

        for (StatusEffect effect : Constants.VANILLA_HARMFUL_EFFECTS) {
            ModPotionData antidote = new ModPotionData(new ItemStack(ModItems.ANTIDOTE));
            ModPotionData vaccine = new ModPotionData(new ItemStack(ModItems.VACCINE));
            ModPotionEffect tmp = new ModPotionEffect(Constants.DEFAULT_DURATION, 0, effect);

            antidote.addEffect(tmp);
            vaccine.addEffect(tmp);

            antidoteList.add(antidote.stack().copy());
            vaccineList.add(vaccine.stack().copy());
        }

        ModPotionData template = new ModPotionData(ItemStack.EMPTY);
        template.setTargetedAt(StatusEffectCategory.HARMFUL);
        template.setDuration(Constants.DEFAULT_DURATION);

        ItemStack harmfulAntidote = new ItemStack(ModItems.ANTIDOTE);
        harmfulAntidote.setNbt(template.getRawNbt());

        ItemStack harmfulVaccine = new ItemStack(ModItems.VACCINE);
        harmfulVaccine.setNbt(template.getRawNbt());

        antidoteList.add(harmfulAntidote);
        vaccineList.add(harmfulVaccine);

        entries.addAll(antidoteList);
        entries.addAll(vaccineList);
    }

    public static void registerGroup() {
        Registry.register(Registries.ITEM_GROUP, new Identifier(Mod.getModId(), "milk-bottle_group"), MILK_BOTTLE);
    }
}
