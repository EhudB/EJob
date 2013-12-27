package me.ehudblum.ejob.job;

import org.bukkit.Material;

public class EJBlock
{
	private Material mat;
	private double exp;
	
	public EJBlock(double exp, String name)
	{
		this.exp = exp;
		this.mat = Material.getMaterial(name);
	}
	
	
	public double getExpForBlock()
	{
		return this.exp;
	}
		
	public Material getMaterial()
	{
		return this.mat;
	}
}
