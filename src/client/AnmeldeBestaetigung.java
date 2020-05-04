package client;

import java.io.Serializable;

public class AnmeldeBestaetigung extends Transport implements Serializable
{
	private static final long serialVersionUID = 1L;
	private boolean erfolgreichAngemeldet;
	private Nickname nickname;
	
	public AnmeldeBestaetigung(boolean erfolgreichAngemeldet, Nickname nickname)
	{
		super("AnmeldeBestaetigung");
		this.erfolgreichAngemeldet = erfolgreichAngemeldet;
		this.nickname = nickname;
	}

	public Nickname getNickname()
	{
		return nickname;
	}
	
	
}
