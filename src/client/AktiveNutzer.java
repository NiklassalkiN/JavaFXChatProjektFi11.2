package client;

import java.io.Serializable;
import java.util.ArrayList;

public class AktiveNutzer extends Transport implements Serializable
{
	private static final long serialVersionUID = 1L;
	private ArrayList<Nickname> benutzer;
	
	public AktiveNutzer()
	{
		super("aktiveNutzer");
		benutzer = new ArrayList<>();
	}

	public ArrayList<Nickname> getBenutzer()
	{
		return benutzer;
	}

	public void setBenutzer(ArrayList<Nickname> benutzer)
	{
		this.benutzer = benutzer;
	}
	
	
}
