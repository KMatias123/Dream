package cat.yoink.dream.impl.module.misc;

import cat.yoink.dream.api.module.Category;
import cat.yoink.dream.api.module.Module;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class SampleModule extends Module
{
	public SampleModule(String name, String description, Category category)
	{
		super(name, description, category);

		setBind(Keyboard.KEY_R);
	}

	@Override
	public void onEnable()
	{
		System.out.println("SampleModule.onEnable");
	}

	@Override
	public void onDisable()
	{
		System.out.println("SampleModule.onDisable");
	}

	@SubscribeEvent
	public void onDeath(LivingDeathEvent event)
	{
		System.out.println(event.getEntity().getName() + " just died");
	}
}
