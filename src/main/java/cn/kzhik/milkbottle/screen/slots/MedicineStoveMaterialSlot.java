package cn.kzhik.milkbottle.screen.slots;

import cn.kzhik.milkbottle.block.entity.MedicineStoveEntity;
import cn.kzhik.milkbottle.screen.MedicineStoveScreenHandler;
import cn.kzhik.milkbottle.utils.potion.ModPotionConverter;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class MedicineStoveMaterialSlot extends Slot {

    private final MedicineStoveScreenHandler handler;

    public MedicineStoveMaterialSlot(MedicineStoveScreenHandler handler, Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
        this.handler = handler;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        MedicineStoveEntity blockEntity = this.handler.getMedicineStoveEntity();

        if (blockEntity == null) {
            return false;
        }

        ModPotionConverter converter = blockEntity.getConverter();
        return converter.canAddMaterial(stack);
    }

    @Override
    public int getMaxItemCount(ItemStack itemStack) {
        return 1;
    }
}
