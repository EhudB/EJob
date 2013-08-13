package me.ehudblum.ejob.job;

public class EJMob 
{
	private String mobName;
	private double exp;
	
	public EJMob(String mobName, double exp)
	{
		this.mobName = mobName;
		this.exp = exp;
	}
	
	public String getMobName()
	{
		return this.mobName;
	}
	
	public double getExpForMob()
	{
		return this.exp;
	}
}
