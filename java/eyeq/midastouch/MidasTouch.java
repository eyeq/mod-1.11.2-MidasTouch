package eyeq.midastouch;

import eyeq.midastouch.event.MidasTouchEventHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import static eyeq.midastouch.MidasTouch.MOD_ID;

@Mod(modid = MOD_ID, version = "1.0", dependencies = "after:eyeq_util")
public class MidasTouch {
    public static final String MOD_ID = "eyeq_midastouch";

    @Mod.Instance(MOD_ID)
    public static MidasTouch instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new MidasTouchEventHandler());
    }
}
