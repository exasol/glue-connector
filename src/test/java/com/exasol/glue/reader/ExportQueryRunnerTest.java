package com.exasol.glue.reader;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ExportQueryRunnerTest {
    private final String EXCEPTION_MESSAGE = "An exception";
    @Mock
    private Connection connectionMock;
    @Mock
    private Statement statementMock;

    @Test
    void testRunExportQuery() throws SQLException {
        final String query = "sql";
        when(this.connectionMock.createStatement()).thenReturn(this.statementMock);
        when(this.statementMock.executeUpdate(query)).thenReturn(10);
        assertThat(new ExportQueryRunner(this.connectionMock).runExportQuery(query), equalTo(10));
        verify(this.statementMock, times(1)).executeUpdate(query);
        verify(this.statementMock, times(1)).close();
    }

    @Test
    void testRunExportQueryCreateStatementThrows() throws SQLException {
        when(this.connectionMock.createStatement()).thenThrow(new SQLException(EXCEPTION_MESSAGE));
        final SQLException exception = assertThrows(SQLException.class,
                () -> new ExportQueryRunner(this.connectionMock).runExportQuery(""));
        assertThat(exception.getMessage(), equalTo(EXCEPTION_MESSAGE));
        verify(this.statementMock, times(0)).close();
    }

    @Test
    void testRunExportQueryUpdateQueryThrows() throws SQLException {
        when(this.connectionMock.createStatement()).thenReturn(this.statementMock);
        when(this.statementMock.executeUpdate("")).thenThrow(new SQLException(EXCEPTION_MESSAGE));
        final SQLException exception = assertThrows(SQLException.class,
                () -> new ExportQueryRunner(this.connectionMock).runExportQuery(""));
        assertThat(exception.getMessage(), equalTo(EXCEPTION_MESSAGE));
        verify(this.statementMock, times(1)).executeUpdate("");
        verify(this.statementMock, times(1)).close();
    }

}
