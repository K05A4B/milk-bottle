package cn.kzhik.milkbottle.resources;

import cn.kzhik.milkbottle.resources.data.medicineStove.MedicineStoveReloadListener;
import cn.kzhik.milkbottle.utils.Mod;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;

public class Resources {

    public static void registerReloadListener() {
        Mod.LOGGER.info("Registering resource reload listener for milk-bottle mod");
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new MedicineStoveReloadListener());
    }
}
