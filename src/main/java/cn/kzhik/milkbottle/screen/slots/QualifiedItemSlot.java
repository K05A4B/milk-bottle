package cn.kzhik.milkbottle.screen.slots;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

import java.util.ArrayList;
import java.util.List;

public class QualifiedItemSlot extends Slot {

    private final ArrayList<Item> qualifiedItems = new ArrayList<>();

    public QualifiedItemSlot(Inventory inventory, int index, int x, int y, Item... items) {
        super(inventory, index, x, y);

        qualifiedItems.addAll(List.of(items));
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return qualifiedItems.contains(stack.getItem());
    }

    @Override
    public int getMaxItemCount(ItemStack stack) {
        return 1;
    }
}
