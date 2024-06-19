package exceptions;

/**
 * Custom exception.  To be used in cases where InvalidKeyException would be used, but an unchecked exception is
 * preferred.  JavaDoc format copied from InvalidKeyException.
 *
 * @author Eliezer Meth
 * @version 1<br>
 * Start Date: 2024-06-06
 */
public class BadKeyException extends RuntimeException
{
    /**
     * Constructs a BadKeyException with no detail message.  A detail method is a String that describes this particular
     * exception.
     */
    public BadKeyException()
    {
        super();
    }

    /**
     * Constructs a BadKeyException with the specified detail message.  A detail message is a String that describes this
     * particular exception.
     * @param s the String that contains a detailed message
     */
    public BadKeyException(String s)
    {
        super(s);
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     * <br>
     * Note that the detail message associated with <code>cause</code> is <i>not</i> automatically incorporated in this
     * exception's detail message.
     * @param message the detail message (which is saved for later retrieval by the <code>Throwable.getMessage()</code>
     *                method).
     * @param cause the cause (which is saved for later retrieval by the <code>Throwable.getCause()</code> method).  (A
     *              <code>null</code> value is permitted, and indicates that the cause is nonexistent or unknown.
     */
    public BadKeyException(String message, Throwable cause)
    {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified cause and a detail message of <code>(cause==null ? null :
     * cause.toString() )</code> which typically contains the class and detail message of <code>cause</code>).  This
     * constructor is useful for exceptions that are little more than wrappers for other throwables (for example,
     * <code>java.security.PrivilegedActionException</code>).
     *
     * @param cause the cause (which is saved for later retrieval by the <code>Throwable.getCause()</code> method).  (A
     *              <code>null</code> value is permitted, and indicates that the cause is nonexistent or unknown.
     */
    public BadKeyException(Throwable cause)
    {
        super(cause);
    }
}
