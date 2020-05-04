package client;

import java.io.Serializable;

public class Transport implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String identifier;
	
	public Transport(String identifier)
	{
		this.identifier = identifier;
	}

	public String getIdentifier()
	{
		return identifier;
	}
	public void setIdentifier(String identifier)
	{
		this.identifier = identifier;
	}
	
	
}
