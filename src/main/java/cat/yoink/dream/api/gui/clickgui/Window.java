package cat.yoink.dream.api.gui.clickgui;

import cat.yoink.dream.api.gui.clickgui.button.ModuleButton;
import cat.yoink.dream.api.gui.clickgui.button.SettingButton;
import cat.yoink.dream.api.module.Category;
import cat.yoink.dream.api.module.Module;
import cat.yoink.dream.api.module.ModuleManager;
import cat.yoink.dream.api.util.font.FontUtil;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author yoink
 * @since 9/20/2020
 */
public class Window
{
	private final ArrayList<ModuleButton> buttons = new ArrayList<>();
	private final Category category;
	private final int W;
	private final int H;
	private final ArrayList<ModuleButton> buttonsBeforeClosing = new ArrayList<>();
	private int X;
	private int Y;
	private int dragX;
	private int dragY;
	private boolean open = true;
	private boolean dragging;
	private int showingButtonCount;
	private boolean opening;
	private boolean closing;

	public Window(final Category category, final int x, final int y, final int w, final int h)
	{
		this.category = category;
		X = x;
		Y = y;
		W = w;
		H = h;

		int yOffset = Y + H;

		for (final Module module : ModuleManager.INSTANCE.getModules(category))
		{
			final ModuleButton button = new ModuleButton(module, X, yOffset, W, H);
			buttons.add(button);
			yOffset += H;
		}
		showingButtonCount = buttons.size();
	}

	public void render(final int mX, final int mY)
	{
		if (dragging)
		{
			X = dragX + mX;
			Y = dragY + mY;
		}

		Gui.drawRect(X, Y, X + W, Y + H, new Color(203, 203, 203, 255).getRGB());
		FontUtil.drawString(category.getName(), X + 4, Y + 4, new Color(29, 29, 29, 232).getRGB());


		if (open || opening || closing)
		{
			int modY = Y + H;

			int moduleRenderCount = 0;
			for (final ModuleButton moduleButton : buttons)
			{
				moduleRenderCount++;

				if (moduleRenderCount < showingButtonCount + 1)
				{
					moduleButton.setX(X);
					moduleButton.setY(modY);

					moduleButton.render(mX, mY);

					if (!moduleButton.isOpen() && opening && buttonsBeforeClosing.contains(moduleButton))
					{
						moduleButton.processRightClick();
					}

					modY += H;

					if (moduleButton.isOpen() || moduleButton.isOpening() || moduleButton.isClosing())
					{

						int settingRenderCount = 0;
						for (final SettingButton settingButton : moduleButton.getButtons())
						{
							settingRenderCount++;

							if (settingRenderCount < moduleButton.getShowingModuleCount() + 1)
							{
								settingButton.setX(X);
								settingButton.setY(modY);

								settingButton.render(mX, mY);

								modY += H;
							}
						}
					}
				}
			}
		}

		if (opening)
		{
			showingButtonCount++;
			if (showingButtonCount == buttons.size())
			{
				opening = false;
				open = true;
				buttonsBeforeClosing.clear();
			}
		}

		if (closing)
		{
			showingButtonCount--;
			if (showingButtonCount == 0 || showingButtonCount == 1)
			{
				closing = false;
				open = false;
			}
		}

	}

	public void mouseDown(final int mX, final int mY, final int mB)
	{
		if (isHover(X, Y, W, H, mX, mY))
		{
			if (mB == 0)
			{
				dragging = true;
				dragX = X - mX;
				dragY = Y - mY;
			}
			else if (mB == 1)
			{
				if (open && !opening && !closing)
				{
					showingButtonCount = buttons.size();
					closing = true;
					for (final ModuleButton button : buttons)
					{
						if (button.isOpen())
						{
							button.processRightClick();
							buttonsBeforeClosing.add(button);
						}
					}
				}
				else if (!open && !opening && !closing)
				{
					showingButtonCount = 1;
					opening = true;
				}
			}
		}

		if (open)
		{
			for (final ModuleButton button : buttons)
			{
				button.mouseDown(mX, mY, mB);
			}
		}
	}

	public void mouseUp(final int mX, final int mY)
	{
		dragging = false;

		if (open)
		{
			for (final ModuleButton button : buttons)
			{
				button.mouseUp(mX, mY);
			}
		}
	}

	public void keyPress(final int key)
	{
		if (open)
		{
			for (final ModuleButton button : buttons)
			{
				button.keyPress(key);
			}
		}
	}

	public void close()
	{
		for (final ModuleButton button : buttons)
		{
			button.close();
		}
	}

	private boolean isHover(final int X, final int Y, final int W, final int H, final int mX, final int mY)
	{
		return mX >= X && mX <= X + W && mY >= Y && mY <= Y + H;
	}

	public int getY()
	{
		return Y;
	}

	public void setY(final int y)
	{
		Y = y;
	}
}
