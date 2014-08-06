/*******************************************************************************
 * Copyright 2011 Max Erik Rohde http://www.mxro.de
 * 
 * All rights reserved.
 ******************************************************************************/
package one.async.joiner;


public interface LocalCallback<GOutput> {
	
	public void onSuccess(GOutput response);
	public void onFailure(Throwable t);
	
}
