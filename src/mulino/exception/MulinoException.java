package mulino.exception;

public class MulinoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MulinoException(Exception e) {
		super(e);
	}

}
