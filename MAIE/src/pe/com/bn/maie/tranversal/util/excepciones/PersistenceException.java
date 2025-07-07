package pe.com.bn.maie.tranversal.util.excepciones;

 public class PersistenceException extends Exception {
 

	public PersistenceException() {
		super();
		 
	}

	public PersistenceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
 
	}

	public PersistenceException(String message, Throwable cause) {
		super(message, cause);
		 
	}

	public PersistenceException(String message) {
		super(message);
		 
	}

	public PersistenceException(Throwable cause) {
		super(cause);
		 
	}

}
