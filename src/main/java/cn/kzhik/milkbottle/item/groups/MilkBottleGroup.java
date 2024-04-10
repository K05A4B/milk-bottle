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

public class MilkBottleGroup {
    public static ItemGroup MILK_BOTTLE = FabricItemGroup.builder()
            .displayName(Text.translatable("item.milk-bottle.milk_bottle"))
            .icon(() -> new ItemStack(ModItems.MILK_BOTTLE))
            .entries(MilkBottleGroup::addItems)
            .build();

    private static void addItems(ItemGroup.DisplayContext context, ItemGroup.Entries entries) {

        entries.add(ModItems.MILK_BOTTLE);

        // 添加原版所有负面效果的解药
        for (StatusEffect harmfulEffect : Constants.VANILLA_HARMFUL_EFFECTS) {
            ModPotionData antidoteNbt = new ModPotionData(new ItemStack(ModItems.ANTIDOTE));
            antidoteNbt.setEffects(new ModPotionEffect(0, 0, harmfulEffect));
            entries.add(antidoteNbt.stack());
        }

        // 添加净化药水
        ModPotionData harmfulAntidote = new ModPotionData(new ItemStack(ModItems.ANTIDOTE));
        harmfulAntidote.setTargetedAt(StatusEffectCategory.HARMFUL);
        entries.add(harmfulAntidote.stack());

        // 一样是添加负面效果的疫苗（你可能决定这个循环没必要，但是我也不想这样写，我不知道该怎么样给物品组排序）
        for (StatusEffect harmfulEffect : Constants.VANILLA_HARMFUL_EFFECTS) {
            ModPotionData vaccineNbt = new ModPotionData(new ItemStack(ModItems.VACCINE));
            vaccineNbt.setEffects(new ModPotionEffect(Constants.VACCINE_DEFAULT_DURATION, 0, harmfulEffect));
            entries.add(vaccineNbt.stack());
        }

        // 添加免疫药水
        ModPotionData harmfulVaccine = new ModPotionData(new ItemStack(ModItems.VACCINE));
        harmfulVaccine.setTargetedAt(StatusEffectCategory.HARMFUL);
        harmfulVaccine.setDuration(Constants.VACCINE_DEFAULT_DURATION);
        entries.add(harmfulVaccine.stack());

        entries.add(ModItems.MEDICINE_STOVE);
    }

    public static void registerGroup() {
        Registry.register(Registries.ITEM_GROUP, new Identifier(Mod.getModId(), "milk-bottle_group"), MILK_BOTTLE);
    }
}
