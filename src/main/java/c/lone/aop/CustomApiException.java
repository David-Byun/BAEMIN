package c.lone.aop;

public class CustomApiException extends RuntimeException {

    /*
        private static final long serialVersionUID = 1L 이유는 ?
        - 일정한 값을 가지므로, CustomApiException 클래스에 변경사항이 있더라도 serialVersionUID는 일정하게 유지됨.
        - 이를 통해 CustomApiException 클래스의 이전에 직렬화된 객체도 클래스 정의가 변경되었더라도 올바르게 역직렬화될 수 있습니다.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public CustomApiException(String message) {
        super(message);
    }
}
