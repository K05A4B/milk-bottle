package cn.kzhik.milkbottle.block.entity;

import cn.kzhik.milkbottle.block.ModBlocks;
import cn.kzhik.milkbottle.utils.Mod;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntityBlockType {

    public static void registerEntityBlockType() {
    }    public static final BlockEntityType<MedicineStoveEntity> MEDICINE_STOVE_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            new Identifier(Mod.getModId(), "medicine_stove_entity"),
            FabricBlockEntityTypeBuilder.create(MedicineStoveEntity::new, ModBlocks.MEDICINE_STOVE).build()
    );


}
