package io.github.ilyalisov.common.repository.specification;

import lombok.NoArgsConstructor;
import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.dialect.PostgreSQLDialect;

@NoArgsConstructor
public class CustomPostgresSQLDialect extends PostgreSQLDialect {

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
