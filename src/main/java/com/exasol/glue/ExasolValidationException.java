package com.exasol.glue;

/**
 * An exception for validation issues.
 */
public class ExasolValidationException extends RuntimeException {
    private static final long serialVersionUID = -4977196770624153905L;

    /**
     * Create an instance of a {@link ExasolValidationException}.
     *
     * @param message an error message
     * @param cause   an exception cause
     */
    public ExasolValidationException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Create an instance of a {@link ExasolValidationException}.
     *
     * @param message an error message
     */
    public ExasolValidationException(final String message) {
        super(message);
    }
}
