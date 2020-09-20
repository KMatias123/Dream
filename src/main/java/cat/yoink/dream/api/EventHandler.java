package cat.yoink.dream.api;

import cat.yoink.dream.Client;
import cat.yoink.dream.api.module.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class EventHandler
{
	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event)
	{
		if (!Keyboard.getEventKeyState()) return;

		for (Module module : Client.moduleManager.getModules())
		{
			if (module.getBind() == Keyboard.getEventKey())
			{
				module.toggle();
			}
		}
	}
}
