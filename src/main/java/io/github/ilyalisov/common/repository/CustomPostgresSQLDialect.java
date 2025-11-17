package io.github.ilyalisov.common.repository;

import lombok.NoArgsConstructor;
import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.dialect.PostgreSQLDialect;

/**
 * Custom PostgreSQL dialect that extends the standard Hibernate PostgreSQL
 * dialect with additional functions and features specific to the application's
 * needs. Currently, adds full-text search capabilities using PostgreSQL's
 * tsvector functionality.
 *
 * @author Ilya Lisov
 * @since 0.1.0
 *
 * @see PostgreSQLDialect
 * @see FunctionContributions
 */
@NoArgsConstructor
public class CustomPostgresSQLDialect extends PostgreSQLDialect {

    /**
     * Initializes the function registry by registering custom SQL functions
     * in addition to the standard PostgreSQL functions. Currently, registers
     * a custom function for full-text search using PostgreSQL's tsvector
     * matching operator (@@).
     *
     * @param functionContributions the function contributions object used to
     *        register custom functions with Hibernate
     *
     * @see FunctionContributions
     */
    @Override
    public void initializeFunctionRegistry(
            final FunctionContributions functionContributions
    ) {
        super.initializeFunctionRegistry(functionContributions);
        var functionRegistry = functionContributions.getFunctionRegistry();
        functionRegistry.registerPattern(
                "tsvector_match",
                "?1 @@ ?2"
        );
    }

}
