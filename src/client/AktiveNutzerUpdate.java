package client;

import java.io.Serializable;

public class AktiveNutzerUpdate extends Transport implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Nickname nick;
	private boolean hinzufuegen;


	public AktiveNutzerUpdate(Nickname nick, boolean hinzufuegen)
	{
		super("aktiveNutzerUpdate");
		this.nick = nick;
		this.hinzufuegen = hinzufuegen;
	}

	public Nickname getNick()
	{
		return nick;
	}

	public void setNick(Nickname nick)
	{
		this.nick = nick;
	}

	public boolean isHinzufuegen()
	{
		return hinzufuegen;
	}

	public void setHinzufuegen(boolean hinzufuegen)
	{
		this.hinzufuegen = hinzufuegen;
	}
	
}
