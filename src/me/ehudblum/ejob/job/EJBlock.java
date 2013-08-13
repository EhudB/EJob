package me.ehudblum.ejob.job;

public class EJBlock
{
	private int id;
	private double exp;
	private int data;
	
	public EJBlock(int id, double exp, int data)
	{
		this.id = id;
		this.exp = exp;
		this.data = data;
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public double getExpForBlock()
	{
		return this.exp;
	}
	
	public int getData()
	{
		return this.data;
	}
}
