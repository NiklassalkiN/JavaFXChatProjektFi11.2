package client;

import java.io.Serializable;

public class PrivateNachricht extends Transport implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Nickname absender;
	private Nickname empfaenger;
	private String nachricht;

	public PrivateNachricht(Nickname absender, Nickname empfaenger, String nachricht)
	{
		super("privateNachricht");
		this.absender = absender;
		this.empfaenger = empfaenger;
		this.nachricht = nachricht;
	}

	public Nickname getAbsender()
	{
		return absender;
	}

	public void setAbsender(Nickname absender)
	{
		this.absender = absender;
	}
	
	public Nickname getEmpfaenger()
	{
		return empfaenger;
	}

	public void setEmpfaenger(Nickname empfaenger)
	{
		this.empfaenger = empfaenger;
	}

	public String getNachricht()
	{
		return nachricht;
	}

	public void setNachricht(String nachricht)
	{
		this.nachricht = nachricht;
	}

	@Override
	public String toString()
	{
		return absender.getName() + ": " + nachricht;
	}
	
	
}
