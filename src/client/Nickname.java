package client;

import java.io.Serializable;

public class Nickname extends Transport implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String email;
	private String name;

	public Nickname(String email, String name)
	{
		super("Nickname");
		this.email = email;
		this.name = name;
	}
	
	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public String toString()
	{
		return email;
	}

}
