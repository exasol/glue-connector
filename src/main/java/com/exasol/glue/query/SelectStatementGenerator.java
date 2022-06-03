package com.exasol.glue.query;

import java.util.List;
import java.util.Optional;

import com.exasol.sql.StatementFactory;
import com.exasol.sql.dql.select.Select;
import com.exasol.sql.dql.select.rendering.SelectRenderer;
import com.exasol.sql.expression.BooleanExpression;
import com.exasol.sql.rendering.StringRendererConfig;

/**
 * A class that generates an Exasol {@code SELECT} statement with column selection and filter clause.
 */
public final class SelectStatementGenerator {
    private static final StatementFactory factory = StatementFactory.getInstance();

    /**
     * Returns select SQL statement given list of column names and predicate.
     *
     * @param table     table to query
     * @param columns   list of column names to push column projection
     * @param predicate boolean predicate to push as a where clause
     * @return select statement string
     */
    public String getSelectStatement(final String table, final List<String> columns,
            final Optional<BooleanExpression> predicate) {
        final Select select = factory.select();
        select.from().table(table);
        addColumns(select, columns);
        addPredicate(select, predicate);
        return renderSelect(select);
    }

    private void addColumns(final Select select, final List<String> columns) {
        if (columns.isEmpty()) {
            select.all();
        } else {
            columns.forEach(column -> select.field(column));
        }
    }

    private void addPredicate(final Select select, final Optional<BooleanExpression> predicate) {
        if (predicate.isPresent()) {
            select.where(predicate.get());
        }
    }

    private String renderSelect(final Select select) {
        final StringRendererConfig rendererConfig = StringRendererConfig.builder().quoteIdentifiers(true).build();
        final SelectRenderer renderer = new SelectRenderer(rendererConfig);
        select.accept(renderer);
        return renderer.render();
    }

}
