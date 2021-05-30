package com.mercadolibre.mutants.exception;

public class ChallengeException extends  IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public ChallengeException (String message) {
        super (message);
    }

    public ChallengeException (Throwable cause) {    	
        super (getCause(cause));
    }

    public ChallengeException (String message, Throwable cause) {
        super (message, cause);       
    }
    
    public static String getCause(Throwable e) {
		String msg = "";
		if(e!=null) {
			msg = " " + e.getMessage();
			msg += getCause(e.getCause());
		}		
		return msg;
	}
}

