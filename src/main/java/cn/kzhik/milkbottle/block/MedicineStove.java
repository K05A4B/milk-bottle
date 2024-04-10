package cn.kzhik.milkbottle.block;

import cn.kzhik.milkbottle.block.entity.MedicineStoveEntity;
import cn.kzhik.milkbottle.block.entity.ModEntityBlockType;
import cn.kzhik.milkbottle.utils.potion.ModPotionConverter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class MedicineStove extends BlockWithEntity {
    public static final DirectionProperty FACING = DirectionProperty.of("facing");
    public static final IntProperty WORK_STATE = IntProperty.of("work_state", 0, 1);

    public MedicineStove(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(WORK_STATE, 0));
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new MedicineStoveEntity(pos, state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ActionResult result = onOperate(state, world, pos, player, hand, hit);
        if (result == ActionResult.SUCCESS) {
            Objects.requireNonNull(world.getBlockEntity(pos)).markDirty();
        }
        return result;
    }

    private ActionResult onOperate(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            world.updateListeners(pos, state, state, Block.NOTIFY_LISTENERS);
            return ActionResult.SUCCESS;
        }

        MedicineStoveEntity entity = (MedicineStoveEntity) world.getBlockEntity(pos);
        if (entity == null) {
            return ActionResult.FAIL;
        }

        ModPotionConverter converter = entity.getConverter();
        ItemStack material = player.getMainHandStack();
        Item materialItem = material.getItem();
        PlayerInventory inventory = player.getInventory();

        if (material.isEmpty()) {
            return ActionResult.FAIL;
        }

        if (state.get(WORK_STATE).equals(MedicineStoveEntity.WorkState.WORKING.ordinal())) {
            return ActionResult.FAIL;
        }

        if (materialItem.equals(Items.COAL) || materialItem.equals(Items.CHARCOAL)) {
            // 不允许再没有主材料前激活药剂炉或者反复激活正在工作的药剂炉
            if (converter.getMainMaterials().isEmpty()) {
                return ActionResult.FAIL;
            }

            world.playSound(null, pos, SoundEvents.BLOCK_BEACON_ACTIVATE, SoundCategory.BLOCKS, 1.0f, 1.0f);

            inventory.removeStack(inventory.selectedSlot, 1);
            entity.startConverter();
        }

        if (materialItem.equals(Items.GLASS_BOTTLE)) {
            ItemStack result = converter.poll();
            if (result == null) {
                return ActionResult.FAIL;
            }

            world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0f, 1.0f);

            inventory.offerOrDrop(result);
            inventory.removeStack(inventory.selectedSlot, 1);
        }

        if (converter.addMaterial(material)) {
            if (ModPotionConverter.isMainMaterial(material)) {
                inventory.offerOrDrop(new ItemStack(Items.GLASS_BOTTLE));
                world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 10f, 1.0f);
            } else {
                world.playSound(null, pos, SoundEvents.ITEM_BONE_MEAL_USE, SoundCategory.BLOCKS, 10f, 1.0f);
            }

            inventory.removeStack(inventory.selectedSlot, 1);
        }

        return ActionResult.SUCCESS;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(WORK_STATE);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return super.getPlacementState(ctx)
                .with(FACING, ctx.getHorizontalPlayerFacing().getOpposite())
                .with(WORK_STATE, 0);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModEntityBlockType.MEDICINE_STOVE_ENTITY, MedicineStoveEntity::tick);
    }
}
