package cat.yoink.dream.api.util.font;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * @see <a href="https://github.com/zeroeightysix/KAMI/blob/master/src/main/java/me/zeroeightsix/kami/gui/font/CFontRenderer.java">CFontRenderer</a>
 * Updated by yoink 9/21/2020
 * Updated by yoink 10/14/2020
 */
public class CustomFontRenderer extends CustomFont
{
	protected CharData[] boldChars = new CharData[256];
	protected CharData[] italicChars = new CharData[256];
	protected CharData[] boldItalicChars = new CharData[256];

	private final int[] colorCode = new int[32];

	public CustomFontRenderer(final Font font, final boolean antiAlias, final boolean fractionalMetrics)
	{
		super(font, antiAlias, fractionalMetrics);
		setupMinecraftColorcodes();
		setupBoldItalicIDs();
	}

	public void drawString(final String text, final float x, final float y, final int color)
	{
		drawString(text, x, y, color, false);
	}

	public void drawStringWithShadow(final String text, final double x, final double y, final int color)
	{
		drawString(text, x + 1D, y + 1D, color, true);
		drawString(text, x, y, color, false);
	}

	public void drawCenteredStringWithShadow(final String text, final float x, final float y, final int color)
	{
		drawStringWithShadow(text, x - getStringWidth(text) / 2f, y, color);
	}

	public void drawCenteredString(final String text, final float x, final float y, final int color)
	{
		drawString(text, x - getStringWidth(text) / 2f, y, color);
	}

	public void drawString(final String text, double x, double y, final int c, final boolean shadow)
	{
		int color = c;

		x -= 1;
		y -= 2;
		if (text == null) return;
		if (color == 553648127) color = 16777215;
		if ((color & 0xFC000000) == 0) color |= -16777216;

		if (shadow) color = (color & 0xFCFCFC) >> 2 | color & 0xFF000000;

		CharData[] currentData = this.charData;
		final float alpha = (color >> 24 & 0xFF) / 255.0F;
		boolean bold = false;
		boolean italic = false;
		boolean strikethrough = false;
		boolean underline = false;
		x *= 2.0D;
		y *= 2.0D;
		GL11.glPushMatrix();
		GlStateManager.scale(0.5D, 0.5D, 0.5D);
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(770, 771);
		GlStateManager.color((color >> 16 & 0xFF) / 255.0F, (color >> 8 & 0xFF) / 255.0F, (color & 0xFF) / 255.0F, alpha);
		final int size = text.length();
		GlStateManager.enableTexture2D();
		GlStateManager.bindTexture(tex.getGlTextureId());
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex.getGlTextureId());
		for (int i = 0; i < size; i++)
		{
			final char character = text.charAt(i);
			if (character == '§')
			{
				int colorIndex = 21;
				try
				{
					colorIndex = "0123456789abcdefklmnor".indexOf(text.charAt(i + 1));
				}
				catch (final Exception ignored)
				{
				}
				if (colorIndex < 16)
				{
					bold = false;
					italic = false;
					underline = false;
					strikethrough = false;
					GlStateManager.bindTexture(tex.getGlTextureId());
					currentData = this.charData;
					if (colorIndex < 0) colorIndex = 15;
					if (shadow) colorIndex += 16;
					final int cCode = this.colorCode[colorIndex];
					GlStateManager.color((cCode >> 16 & 0xFF) / 255.0F, (cCode >> 8 & 0xFF) / 255.0F, (cCode & 0xFF) / 255.0F, alpha);
				}
				else if (colorIndex == 17)
				{
					bold = true;
					if (italic)
					{
						GlStateManager.bindTexture(texItalicBold.getGlTextureId());
						currentData = this.boldItalicChars;
					}
					else
					{
						GlStateManager.bindTexture(texBold.getGlTextureId());
						currentData = this.boldChars;
					}
				}
				else if (colorIndex == 18) strikethrough = true;
				else if (colorIndex == 19) underline = true;
				else if (colorIndex == 20)
				{
					italic = true;
					if (bold)
					{
						GlStateManager.bindTexture(texItalicBold.getGlTextureId());
						currentData = this.boldItalicChars;
					}
					else
					{
						GlStateManager.bindTexture(texItalic.getGlTextureId());
						currentData = this.italicChars;
					}
				}
				else if (colorIndex == 21)
				{
					bold = false;
					italic = false;
					underline = false;
					strikethrough = false;
					GlStateManager.color((color >> 16 & 0xFF) / 255.0F, (color >> 8 & 0xFF) / 255.0F, (color & 0xFF) / 255.0F, alpha);
					GlStateManager.bindTexture(tex.getGlTextureId());
					currentData = this.charData;
				}
				i++;
			}
			else if (character < currentData.length)
			{
				GL11.glBegin(4);
				drawChar(currentData, character, (float) x, (float) y);
				GL11.glEnd();
				if (strikethrough)
					drawLine(x, y + currentData[character].height / 2f, x + currentData[character].width - 8.0D, y + currentData[character].height / 2f);
				if (underline)
					drawLine(x, y + currentData[character].height - 2.0D, x + currentData[character].width - 8.0D, y + currentData[character].height - 2.0D);
				x += currentData[character].width - 8 + this.charOffset;
			}
		}
		GL11.glHint(3155, 4352);
		GL11.glPopMatrix();
	}

	@Override
	public int getStringWidth(final String text)
	{
		if (text == null) return 0;

		int width = 0;
		final CharData[] currentData = this.charData;
		final int size = text.length();

		for (int i = 0; i < size; i++)
		{
			final char character = text.charAt(i);
			if (character == '§') i++;
			else if (character < currentData.length) width += currentData[character].width - 8 + this.charOffset;
		}

		return width / 2;
	}

	public void setFont(final Font font)
	{
		super.setFont(font);
		setupBoldItalicIDs();
	}

	public void setAntiAlias(final boolean antiAlias)
	{
		super.setAntiAlias(antiAlias);
		setupBoldItalicIDs();
	}

	public void setFractionalMetrics(final boolean fractionalMetrics)
	{
		super.setFractionalMetrics(fractionalMetrics);
		setupBoldItalicIDs();
	}

	protected DynamicTexture texBold;
	protected DynamicTexture texItalic;
	protected DynamicTexture texItalicBold;

	private void setupBoldItalicIDs()
	{
		texBold = setupTexture(this.font.deriveFont(Font.BOLD), this.antiAlias, this.fractionalMetrics, this.boldChars);
		texItalic = setupTexture(this.font.deriveFont(Font.ITALIC), this.antiAlias, this.fractionalMetrics, this.italicChars);
		texItalicBold = setupTexture(this.font.deriveFont(Font.BOLD | Font.ITALIC), this.antiAlias, this.fractionalMetrics, this.boldItalicChars);
	}

	private void drawLine(final double x, final double y, final double x1, final double y1)
	{
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glLineWidth((float) 1.0);
		GL11.glBegin(1);
		GL11.glVertex2d(x, y);
		GL11.glVertex2d(x1, y1);
		GL11.glEnd();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	private void setupMinecraftColorcodes()
	{
		for (int index = 0; index < 32; index++)
		{
			final int noClue = (index >> 3 & 0x1) * 85;
			int red = (index >> 2 & 0x1) * 170 + noClue;
			int green = (index >> 1 & 0x1) * 170 + noClue;
			int blue = (index & 0x1) * 170 + noClue;

			if (index == 6) red += 85;

			if (index >= 16)
			{
				red /= 4;
				green /= 4;
				blue /= 4;
			}

			this.colorCode[index] = ((red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF);
		}
	}
}
