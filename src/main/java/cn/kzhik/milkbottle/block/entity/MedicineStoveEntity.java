package cn.kzhik.milkbottle.block.entity;

import cn.kzhik.milkbottle.block.MedicineStove;
import cn.kzhik.milkbottle.utils.block.MedicineStoveTickProcessor;
import cn.kzhik.milkbottle.utils.potion.ModPotionConverter;
import cn.kzhik.milkbottle.utils.tick.TickProcessor;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;


public class MedicineStoveEntity extends BlockEntity {

    private final ModPotionConverter converter = new ModPotionConverter(3);
    public ArrayList<TickProcessor> processList = new ArrayList<>();
    protected BlockState blockState;
    public MedicineStoveEntity(BlockPos pos, BlockState state) {
        super(ModEntityBlockType.MEDICINE_STOVE_ENTITY, pos, state);
        this.blockState = state;
    }

    public static void tick(World world, BlockPos pos, BlockState state, MedicineStoveEntity be) {
        if (be.processList.isEmpty()) {
            return;
        }

        int size = be.processList.size();

        for (int i = 0; i < size; i++) {
            TickProcessor processor = be.processList.get(i);
            processor.process(world, pos, state, be);
            if (processor.processEnd()) {
                be.processList.remove(processor);
            }
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        NbtCompound extend = new NbtCompound();

        nbt.put("MedicineStove", converter.marshal());

        for (TickProcessor processor : processList) {
            if (processor instanceof MedicineStoveTickProcessor) {
                extend.putInt("waitingTick", ((MedicineStoveTickProcessor) processor).waitingTick);
            }
        }

        nbt.put("MedicineStoveExtend", extend);

        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        NbtCompound extend = (NbtCompound) nbt.get("MedicineStoveExtend");

        converter.unmarshal((NbtCompound) nbt.get("MedicineStove"));

        if (extend != null && extend.contains("waitingTick")) {
            this.processList.add(new MedicineStoveTickProcessor(converter, this, extend.getInt("waitingTick")));
        }

    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    public ModPotionConverter getConverter() {
        return converter;
    }

    public void startConverter() {
        if (getWorkState() != WorkState.RELEASE.ordinal()) {
            return;
        }

        setWorkState(WorkState.WORKING);

        TickProcessor processor = new MedicineStoveTickProcessor(converter, this);

        this.processList.add(processor);
    }

    public int getWorkState() {
        return this.blockState.get(MedicineStove.WORK_STATE);
    }

    public void setWorkState(WorkState state) {
        if (world != null) {
            world.setBlockState(pos, this.blockState.with(MedicineStove.WORK_STATE, state.ordinal()));
        }
    }

    public enum WorkState {
        RELEASE,
        WORKING
    }
}
