package me.ehudblum.ejob.util;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class EJUtil 
{
	private static final HashMap<String, String> colorMappings = new HashMap<String, String>();
	public static final char colorChar = '§';
	private static float[] SIN_TABLE;

	static
	{
		colorMappings.put("<black>", ChatColor.BLACK.toString());
	    colorMappings.put("<darkblue>", ChatColor.DARK_BLUE.toString());
	    colorMappings.put("<darkgreen>", ChatColor.DARK_GREEN.toString());
	    colorMappings.put("<darkaqua>", ChatColor.DARK_AQUA.toString());
	    colorMappings.put("<darkred>", ChatColor.DARK_RED.toString());
	    colorMappings.put("<darkpurple>", ChatColor.DARK_PURPLE.toString());
	    colorMappings.put("<gold>", ChatColor.GOLD.toString());
	    colorMappings.put("<gray>", ChatColor.GRAY.toString());
	    colorMappings.put("<darkgray>", ChatColor.DARK_GRAY.toString());
	    colorMappings.put("<blue>", ChatColor.BLUE.toString());
	    colorMappings.put("<green>", ChatColor.GREEN.toString());
	    colorMappings.put("<aqua>", ChatColor.AQUA.toString());
	  	colorMappings.put("<red>", ChatColor.RED.toString());
	  	colorMappings.put("<lightpurple>", ChatColor.LIGHT_PURPLE.toString());
	  	colorMappings.put("<yellow>", ChatColor.YELLOW.toString());
	  	colorMappings.put("<white>", ChatColor.WHITE.toString());
	  	colorMappings.put("<magic>", ChatColor.MAGIC.toString());
	  	colorMappings.put("<bold>", ChatColor.BOLD.toString());
	  	colorMappings.put("<strikethrough>", ChatColor.STRIKETHROUGH.toString());
	  	colorMappings.put("<underline>", ChatColor.UNDERLINE.toString());
	  	colorMappings.put("<italic>", ChatColor.ITALIC.toString());
	  	colorMappings.put("<reset>", ChatColor.RESET.toString());

	  	SIN_TABLE = new float[65536];
	  	for (int i = 0; i < 65536; i++)
	  	{
	  		SIN_TABLE[i] = (float)Math.sin(i * 3.141592653589793D * 2.0D / 65536.0D);
	  	}
	}
	
	public static void sendMessage(Player player, String message)
	{
		if ((player != null) && (player.isOnline()))
	    {
			String[] lines = message.split("@");
			for (String line : lines)
			{
				player.sendMessage(colorCode(line));
			}
	    }
	}
	
	@SuppressWarnings("rawtypes")
	public static String colorCode(String message)
	{
		for (Map.Entry colorMapping : colorMappings.entrySet())
		{
			message = message.replace((CharSequence)colorMapping.getKey(), (CharSequence)colorMapping.getValue());
		}
		return message;
	}
}
