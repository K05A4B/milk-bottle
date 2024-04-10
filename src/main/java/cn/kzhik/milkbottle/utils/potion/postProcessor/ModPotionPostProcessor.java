package cn.kzhik.milkbottle.utils.potion.postProcessor;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashSet;
import java.util.Queue;

public interface ModPotionPostProcessor {
    Queue<ItemStack> process(Queue<ItemStack> bufferQueue, HashSet<Item> ancillaryMaterials);
}
