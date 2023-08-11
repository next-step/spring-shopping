package shopping.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExceptionLogger {

    private final Logger logger;

    public ExceptionLogger(String name) {
        logger = LoggerFactory.getLogger(name);
    }

    public void logException(Exception exception) {
        if (logger.isErrorEnabled()) {
            logger.error(createLogInfo(exception));
        }
    }

    private String createLogInfo(Exception exception) {
        StringBuilder sb = new StringBuilder();
        sb.append(exception.getClass()).append(": ").append(exception.getMessage());

        StackTraceElement[] stackTrace = exception.getStackTrace();
        if (stackTrace.length > 0) {
            StackTraceElement causedAt = stackTrace[0];
            sb.append(createCausedAtInfo(causedAt));
        }
        return sb.toString();
    }

    private String createCausedAtInfo(StackTraceElement causedAt) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n at ").append(causedAt.getClassName()).append(".").append(causedAt.getMethodName());
        String simpleClassName = getSimpleName(causedAt.getClassName());
        sb.append("(").append(simpleClassName).append(":").append(causedAt.getLineNumber()).append(")");
        return sb.toString();
    }

    private String getSimpleName(String fullClassName) {
        String[] parsedNames = fullClassName.split("\\.");
        if (parsedNames.length == 0) {
            return fullClassName;
        }
        return parsedNames[parsedNames.length-1];
    }
}
