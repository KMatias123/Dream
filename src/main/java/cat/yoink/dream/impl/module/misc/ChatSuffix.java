package cat.yoink.dream.impl.module.misc;

import cat.yoink.dream.api.module.Category;
import cat.yoink.dream.api.module.Module;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;

public class ChatSuffix extends Module
{
	public ChatSuffix(final String name, final String description, final Category category)
	{
		super(name, description, category);
	}

	@SubscribeEvent
	public void onChat(final ClientChatEvent event)
	{
		for (final String s : Arrays.asList("/", ".", "-", ",", ":", ";", "'", "\"", "+", "\\"))
		{
			if (event.getMessage().startsWith(s)) return;
		}

		event.setMessage(event.getMessage() + " ｜ ᴅʀᴇᴀᴍ");
	}
}
