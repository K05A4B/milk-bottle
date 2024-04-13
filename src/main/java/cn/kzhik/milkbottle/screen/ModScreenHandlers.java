package cn.kzhik.milkbottle.screen;

import cn.kzhik.milkbottle.utils.Mod;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {
    public static final ScreenHandlerType<MedicineStoveScreenHandler> MEDICINE_STOVE_SCREEN_HANDLER;

    static {
        MEDICINE_STOVE_SCREEN_HANDLER = registerScreenHandler("medicine_stove_screen_handler", MedicineStoveScreenHandler::new);
    }

    private static <T extends ScreenHandler> ScreenHandlerType<T> registerScreenHandler(String id, ScreenHandlerRegistry.SimpleClientHandlerFactory<T> factory) {
        return ScreenHandlerRegistry.registerSimple(new Identifier(Mod.getModId(), id), factory);
    }
}
