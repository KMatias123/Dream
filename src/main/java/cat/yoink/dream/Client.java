package cat.yoink.dream;

import cat.yoink.dream.api.EventHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = "dream", name = "Dream", version = "b1")
public class Client
{
	@Mod.EventHandler
	public void initialize(FMLInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(new EventHandler());
	}
}
