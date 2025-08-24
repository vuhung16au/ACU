package com.acu.graphql;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;

@Component
public class GraphQLErrorHandler extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        if (ex instanceof RuntimeException) {
            String message = ex.getMessage();
            
            // Handle specific error messages
            if (message.contains("not found")) {
                return GraphqlErrorBuilder.newError()
                        .errorType(ErrorType.NOT_FOUND)
                        .message(message)
                        .path(env.getExecutionStepInfo().getPath())
                        .build();
            } else if (message.contains("already reviewed")) {
                return GraphqlErrorBuilder.newError()
                        .errorType(ErrorType.BAD_REQUEST)
                        .message(message)
                        .path(env.getExecutionStepInfo().getPath())
                        .build();
            } else if (message.contains("can only")) {
                return GraphqlErrorBuilder.newError()
                        .errorType(ErrorType.FORBIDDEN)
                        .message(message)
                        .path(env.getExecutionStepInfo().getPath())
                        .build();
            } else if (message.contains("must be between")) {
                return GraphqlErrorBuilder.newError()
                        .errorType(ErrorType.BAD_REQUEST)
                        .message(message)
                        .path(env.getExecutionStepInfo().getPath())
                        .build();
            }
            
            // Default handling for other RuntimeException
            return GraphqlErrorBuilder.newError()
                    .errorType(ErrorType.BAD_REQUEST)
                    .message(message)
                    .path(env.getExecutionStepInfo().getPath())
                    .build();
        }
        
        // For other types of exceptions, return a generic error
        return GraphqlErrorBuilder.newError()
                .errorType(ErrorType.INTERNAL_ERROR)
                .message("An unexpected error occurred")
                .path(env.getExecutionStepInfo().getPath())
                .build();
    }
}
