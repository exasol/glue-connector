package com.exasol.glue;

import com.exasol.errorreporting.ExaError;

/**
 * A class that implements object representing a column.
 */
public class ColumnDescription {
    private final String name;
    private final int type;
    private final int precision;
    private final int scale;
    private final boolean isSigned;
    private final boolean isNullable;

    private ColumnDescription(final Builder builder) {
        this.name = builder.name;
        this.type = builder.type;
        this.precision = builder.precision;
        this.scale = builder.scale;
        this.isSigned = builder.isSigned;
        this.isNullable = builder.isNullable;
    }

    /**
     * Gets the column name.
     *
     * @return column name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the column type.
     *
     * @return column type
     */
    public int getType() {
        return this.type;
    }

    /**
     * Gets the column precision.
     *
     * @return column precision
     */
    public int getPrecision() {
        return this.precision;
    }

    /**
     * Gets the column scale.
     *
     * @return column scale
     */
    public int getScale() {
        return this.scale;
    }

    /**
     * Gets the column signnedness check.
     *
     * @return {@code true} if column is signed
     */
    public boolean isSigned() {
        return this.isSigned;
    }

    /**
     * Gets the column nullable check.
     *
     * @return {@code true} if column is nullable
     */
    public boolean isNullable() {
        return this.isNullable;
    }

    /**
     * Creates a new builder for {@link ColumnDescription}.
     *
     * @return builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link ColumnDescription}.
     */
    public static class Builder {
        private String name = null;
        private Integer type = null;
        private int precision = 0;
        private int scale = 0;
        private boolean isSigned = false;
        private boolean isNullable = false;

        /**
         * Sets the column name.
         *
         * @param name a column name
         * @return builder instance for fluent programming
         */
        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the column type.
         *
         * @param type a column type
         * @return builder instance for fluent programming
         */
        public Builder type(final int type) {
            this.type = type;
            return this;
        }

        /**
         * Sets the precision for numeric columns.
         *
         * @param precision a precision for numeric column
         * @return builder instance for fluent programming
         */
        public Builder precision(final int precision) {
            this.precision = precision;
            return this;
        }

        /**
         * Sets the scale for numeric columns.
         *
         * @param scale a scale for numeric column
         * @return builder instance for fluent programming
         */
        public Builder scale(final int scale) {
            this.scale = scale;
            return this;
        }

        /**
         * Sets the signnedness for numeric columns.
         *
         * @param isSigned a boolean indicating if column is signed or not
         * @return builder instance for fluent programming
         */
        public Builder isSigned(final boolean isSigned) {
            this.isSigned = isSigned;
            return this;
        }

        /**
         * Sets the boolean for nullable columns.
         *
         * @param isNullable a boolean indicating if a column nullable or not
         * @return builder instance for fluent programming
         */
        public Builder isNullable(final boolean isNullable) {
            this.isNullable = isNullable;
            return this;
        }

        /**
         * Builds a new instance of {@link ColumnDescription}.
         *
         * @return new instance of {@link ColumnDescription}
         */
        public ColumnDescription build() {
            validate();
            return new ColumnDescription(this);
        }

        private void validate() {
            if (this.name == null) {
                throw new IllegalArgumentException(ExaError.messageBuilder("E-EGC-8")
                        .message("Failed to build the column description because column name is missing.")
                        .mitigation("Please set the column name when building column description.").toString());
            }
            if (this.type == null) {
                throw new IllegalArgumentException(ExaError.messageBuilder("E-EGC-9")
                        .message("Failed to build the column description because column type is missing.")
                        .mitigation("Please set the column type for column {{name}}.", this.name).toString());
            }
        }
    }

}
