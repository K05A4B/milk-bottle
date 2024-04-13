package cn.kzhik.milkbottle.screen;

import cn.kzhik.milkbottle.block.entity.MedicineStoveEntity;
import cn.kzhik.milkbottle.screen.slots.MedicineStoveMaterialSlot;
import cn.kzhik.milkbottle.screen.slots.QualifiedItemSlot;
import cn.kzhik.milkbottle.utils.potion.ModPotionConverter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryChangedListener;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MedicineStoveScreenHandler extends ScreenHandler implements InventoryChangedListener {
    private final Inventory inventory;
    protected PropertyDelegate propertyDelegate;
    private MedicineStoveEntity blockEntity = null;

    public MedicineStoveScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(6), new ArrayPropertyDelegate(1));
    }

    public MedicineStoveScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(ModScreenHandlers.MEDICINE_STOVE_SCREEN_HANDLER, syncId);
        checkSize(inventory, 6);
        this.inventory = inventory;
        this.propertyDelegate = propertyDelegate;
        inventory.onOpen(playerInventory.player);

        if (inventory instanceof SimpleInventory) {
            ((SimpleInventory) inventory).addListener(this);
        }

        this.addProperties(propertyDelegate);

        // 材料槽位的xy轴
        int[][] slotPosition = {
                {68, 23},
                {89, 23},
                {110, 23}
        };

        // 处理材料插槽
        for (int i = 0; i < 3; i++) {
            int x = slotPosition[i][0];
            int y = slotPosition[i][1];
            this.addSlot(new MedicineStoveMaterialSlot(this, this.inventory, i, x, y));
        }

        // 处理燃料插槽和取药插槽
        this.addSlot(new QualifiedItemSlot(this.inventory, 4, 141, 23, Items.COAL, Items.CHARCOAL));
        this.addSlot(new QualifiedItemSlot(this.inventory, 5, 141, 79, Items.GLASS_BOTTLE));

        for (int m = 0; m < 3; ++m) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 107 + m * 18));
            }
        }

        for (int m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 165));
        }
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    public int getWaitingTick() {
        return propertyDelegate.get(0);
    }

    public MedicineStoveScreenHandler setBlockEntity(MedicineStoveEntity entity) {
        this.blockEntity = entity;
        return this;
    }

    public MedicineStoveEntity getMedicineStoveEntity() {
        if (this.blockEntity == null) {
            return null;
        }

        return this.blockEntity;
    }

    @Override
    public void onClosed(PlayerEntity player) {
        for (int i = 0; i < this.inventory.size(); i++) {
            ItemStack itemStack = this.inventory.getStack(i);

            if (!itemStack.isEmpty()) {
                player.getInventory().offerOrDrop(itemStack);
            }
        }
        super.onClosed(player);
    }

    @Override
    public void onInventoryChanged(Inventory sender) {
        if (sender != this.inventory) {
            return;
        }

        MedicineStoveEntity blockEntity = this.getMedicineStoveEntity();
        if (blockEntity == null) {
            return;
        }

        World world = blockEntity.getWorld();
        BlockPos pos = blockEntity.getPos();
        ModPotionConverter converter = blockEntity.getConverter();
        boolean isRelease = blockEntity.getWorkState() == MedicineStoveEntity.WorkState.RELEASE.ordinal();

        // 处理材料插槽
        for (int i = 0; i < 3; i++) {
            if (!isRelease) {
                break;
            }

            ItemStack material = sender.getStack(i);
            if (material.isEmpty()) {
                continue;
            }

            if (converter.canAddMaterial(material)) {
                converter.addMaterial(material);

                if (world != null) {
                    if (ModPotionConverter.isMainMaterial((material))) {
                        world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 10f, 1.0f);
                    } else {
                        world.playSound(null, pos, SoundEvents.ITEM_BONE_MEAL_USE, SoundCategory.BLOCKS, 10f, 1.0f);
                    }
                }

                if (ModPotionConverter.isMainMaterial(material)) {
                    sender.setStack(i, new ItemStack(Items.GLASS_BOTTLE));
                } else {
                    sender.removeStack(i);
                }
            }
        }

        // 取药插槽
        ItemStack outSlot = sender.getStack(5);
        if (outSlot.getItem() == Items.GLASS_BOTTLE) {
            ItemStack out = converter.poll();
            if (out == null) {
                return;
            }

            sender.setStack(5, out);
        }

        if (converter.getMainMaterials().isEmpty()) {
            return;
        }

        // 燃料插槽
        ItemStack fuel = sender.getStack(4);
        if (isRelease) {
            if (!fuel.isEmpty()) {
                sender.removeStack(4);
                blockEntity.startConverter();
            }
        }
    }
}
