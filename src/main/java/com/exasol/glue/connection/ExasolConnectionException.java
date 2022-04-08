package com.exasol.glue.connection;

/**
 * An exception for Exasol JDCB connection issues.
 */
public class ExasolConnectionException extends RuntimeException {
    private static final long serialVersionUID = 2818034094289319833L;

    /**
     * Creates an instance of a {@link ExasolConnectionException}.
     *
     * @param message an error message
     * @param cause   an exception cause
     */
    public ExasolConnectionException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates an instance of a {@link ExasolConnectionException}.
     *
     * @param message an error message
     */
    public ExasolConnectionException(final String message) {
        super(message);
    }
}