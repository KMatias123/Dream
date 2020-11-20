package cat.yoink.dream.api.gui.clickgui;

import cat.yoink.dream.api.module.Category;
import cat.yoink.dream.api.module.ModuleManager;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;

/**
 * @author yoink
 * @since 9/20/2020
 */
public class ClickGUI extends GuiScreen
{
	private final ArrayList<Window> windows = new ArrayList<>();

	public ClickGUI()
	{
		int xOffset = 3;

		for (final Category category : Category.values())
		{
			final Window window = new Window(category, xOffset, 3, 105, 15);
			windows.add(window);
			xOffset += 110;
		}
	}

	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks)
	{
		doScroll();

		for (final Window window : windows)
		{
			window.render(mouseX, mouseY);
		}
	}

	@Override
	protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton)
	{
		for (final Window window : windows)
		{
			window.mouseDown(mouseX, mouseY, mouseButton);
		}
	}

	@Override
	protected void mouseReleased(final int mouseX, final int mouseY, final int state)
	{
		for (final Window window : windows)
		{
			window.mouseUp(mouseX, mouseY);
		}
	}

	@Override
	protected void keyTyped(final char typedChar, final int keyCode)
	{
		for (final Window window : windows)
		{
			window.keyPress(keyCode);
		}

		if (keyCode == Keyboard.KEY_ESCAPE)
		{
			mc.displayGuiScreen(null);

			if (mc.currentScreen == null)
			{
				mc.setIngameFocus();
			}
		}
	}

	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}

	public void drawGradient(final int left, final int top, final int right, final int bottom, final int startColor, final int endColor)
	{
		drawGradientRect(left, top, right, bottom, startColor, endColor);
	}

	@Override
	public void onGuiClosed()
	{
		for (final Window window : windows)
		{
			window.close();
		}

		ModuleManager.INSTANCE.getModule("ClickGUI").disable();
	}

	private void doScroll()
	{
		final int w = Mouse.getDWheel();
		if (w < 0)
		{
			for (final Window window : windows)
			{
				window.setY(window.getY() - 8);
			}
		}
		else if (w > 0)
		{
			for (final Window window : windows)
			{
				window.setY(window.getY() + 8);
			}
		}
	}
}
