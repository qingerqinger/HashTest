package net.armyao.projs.agrothim;

public class Poisson
{
	public static double Lamda;
	public int nextPoisson()
	{ 
		double b = 1, c = Math.exp(-Lamda), u;
		int x = 0;
		do
		{
			u = Math.random();
			b *= u;
			if (b >= c)
				x++;
		}
		while (b >= c);
		return x;
	}
	public Poisson( double lamda)
	{
		Lamda=lamda;
	}
}
