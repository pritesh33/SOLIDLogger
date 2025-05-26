package com.solidlogger.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A Singleton class for managing logging throughout the Selenium automation framework using Log4j2.
 * Adheres to SOLID principles:
 * - Single Responsibility Principle (SRP): Solely responsible for providing logging capabilities.
 * - Open/Closed Principle (OCP): Configurable via log4j2.xml, allowing extension without modifying this class.
 * - Dependency Inversion Principle (DIP): High-level modules depend on this abstraction (LoggerUtil),
 * not directly on Log4j2's concrete Logger implementation.
 */
public class LoggerUtil {
    // Make instance volatile to ensure thread-safety during initialization
    private static volatile LoggerUtil instance;
    private final Logger logger;

    /**
     * Private constructor to prevent direct instantiation (Singleton pattern).
     * Throws exception if created through reflection.
     */
    private LoggerUtil() {
        // Prevent reflection-based instantiation
        if (instance != null) {
            throw new IllegalStateException("Already instantiated");
        }
        this.logger = LogManager.getLogger("SOLIDLogger");
    }

    /**
     * Provides the single instance of LoggerUtil using double-checked locking pattern.
     * This implementation is thread-safe and more efficient than synchronizing the entire method.
     *
     * @return The singleton instance of LoggerUtil.
     */
    public static LoggerUtil getInstance() {
        // First check (no synchronization)
        if (instance == null) {
            // Synchronize only when instance is null
            synchronized (LoggerUtil.class) {
                // Second check (with synchronization)
                if (instance == null) {
                    instance = new LoggerUtil();
                }
            }
        }
        return instance;
    }

    /**
     * Returns the configured Log4j2 Logger instance.
     *
     * @return The Log4j2 Logger.
     */
    public Logger getLogger() {
        return logger;
    }

    // Convenience methods for common log levels

    public void debug(String message) {
        logger.debug(message);
    }

    public void info(String message) {
        logger.info(message);
    }

    public void warn(String message) {
        logger.warn(message);
    }

    public void error(String message) {
        logger.error(message);
    }

    public void fatal(String message) {
        logger.fatal(message);
    }

    /**
     * Logs an error message with an associated Throwable (exception).
     *
     * @param message   The log message.
     * @param throwable The Throwable (exception) to log.
     */
    public void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }

    /**
     * Logs a fatal message with an associated Throwable (exception).
     *
     * @param message   The log message.
     * @param throwable The Throwable (exception) to log.
     */
    public void fatal(String message, Throwable throwable) {
        logger.fatal(message, throwable);
    }

    /**
     * Prevent serialization-based singleton violation
     */
    private Object readResolve() {
        return instance;
    }
}