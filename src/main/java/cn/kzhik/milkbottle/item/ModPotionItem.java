package cn.kzhik.milkbottle.item;

import cn.kzhik.milkbottle.utils.potion.ModPotionUtils;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ModPotionItem extends PotionItem {

    public ModPotionItem(Settings settings) {
        super(settings);
    }

    public String getSuffix(ItemStack stack) {
        return "";
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        String key = super.getOrCreateTranslationKey();
        String suffix = getSuffix(stack);

        if (suffix.isEmpty()) {
            return key;
        }

        return key + "." + suffix;
    }

    @Override
    public ItemStack getDefaultStack() {
        return super.getDefaultStack();
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        ModPotionUtils.generateTooltip(stack, tooltip);
    }
}
