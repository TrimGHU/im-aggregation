package com.ubtech.im.logger;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;
import org.bson.Document;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * 
 * Copyright © 2017 Ubtech. All rights reserved.
 * 
 * @Title: AbstractBsonAppender.java 
 * @Prject: im-logger
 * @Package: com.ubtech.im.logger 
 * @Description: TODO
 * @author: HuGui   
 * @date: 2017年6月6日 下午2:57:08 
 * @version: V1.0
 */

public abstract class AbstractBsonAppender extends AppenderSkeleton {
    private LoggingEvent2Document bsonifier = new LoggingEvent2Document();
    
    public boolean requiresLayout() {
        return(false);
    }

    @Override
    protected final void append(final LoggingEvent loggingEvent) {
	        Document bson = bsonifier.convert(loggingEvent);
	        append(bson);
    }

    protected abstract void append(Document bson);
    
    class LoggingEvent2Document {

        // Main log event elements
        private static final String KEY_TIMESTAMP = "timestamp";
        private static final String KEY_LEVEL = "level";
        private static final String KEY_THREAD = "thread";
        private static final String KEY_MESSAGE = "message";
        private static final String KEY_LOGGER_NAME = "loggerName";
        // Source code location
        private static final String KEY_FILE_NAME = "fileName";
        private static final String KEY_METHOD = "method";
        private static final String KEY_LINE_NUMBER = "lineNumber";
        private static final String KEY_CLASS = "class";
        // Class info
        private static final String KEY_FQCN = "fullyQualifiedClassName";
        private static final String KEY_PACKAGE = "package";
        private static final String KEY_CLASS_NAME = "className";
        // Exceptions
        private static final String KEY_THROWABLES = "throwables";
        private static final String KEY_EXCEPTION_MESSAGE = "message";
        private static final String KEY_STACK_TRACE = "stackTrace";
        // Host and Process Info
        private static final String KEY_HOST = "host";
        private static final String KEY_PROCESS = "process";
        private static final String KEY_HOSTNAME = "name";
        private static final String KEY_IP = "ip";
        // MDC Properties
        private static final String KEY_MDC_PROPERTIES = "properties";

        private final DBObject hostInfo = new BasicDBObject();

        public LoggingEvent2Document() {
            setupNetworkInfo();
        }

        private void setupNetworkInfo() {
            hostInfo.put(KEY_PROCESS, ManagementFactory.getRuntimeMXBean().getName());
            try {
                hostInfo.put(KEY_HOSTNAME, InetAddress.getLocalHost().getHostName());
                hostInfo.put(KEY_IP, InetAddress.getLocalHost().getHostAddress());
            } catch (UnknownHostException e) {
                LogLog.warn(e.getMessage());
            }
        }

        /**
         * BSONifies a single Log4J LoggingEvent object.
         * 
         * @param loggingEvent
         *            The LoggingEvent object to BSONify <i>(may be null)</i>.
         * @return The BSONified equivalent of the LoggingEvent object <i>(may be null)</i>.
         */
        @SuppressWarnings("unchecked")
    	public Document convert(final LoggingEvent loggingEvent) {
        	Document result = null;

            if (loggingEvent != null) {
                result = new Document();

                result.put(KEY_TIMESTAMP, new Date(loggingEvent.getTimeStamp()));
                nullSafePut(result, KEY_LEVEL, loggingEvent.getLevel().toString());
                nullSafePut(result, KEY_THREAD, loggingEvent.getThreadName());
                nullSafePut(result, KEY_MESSAGE, loggingEvent.getRenderedMessage());
                nullSafePut(result, KEY_LOGGER_NAME, bsonifyClassName(loggingEvent.getLoggerName()));

                addMDCInformation(result, loggingEvent.getProperties());
                addLocationInformation(result, loggingEvent.getLocationInformation());
                addThrowableInformation(result, loggingEvent.getThrowableInformation());
                addHostnameInformation(result);
            }

            return (result);
        }

        /**
         * Adds MDC Properties to the DBObject.
         * 
         * @param bson
         *            The root DBObject
         * @param props
         *            MDC Properties to be logged
         */
        protected void addMDCInformation(Document bson, final Map<Object, Object> props) {
            if (props != null && props.size() > 0) {

            	Document mdcProperties = new Document();
                String key;
                // Copy MDC properties into document
                for (Map.Entry<Object, Object> entry : props.entrySet()) {
                    key = (entry.getKey().toString().contains("."))
                            ? entry.getKey().toString().replaceAll("\\.", "_")
                            : entry.getKey().toString();
                    nullSafePut(mdcProperties, key, entry.getValue().toString());
                }
                bson.put(KEY_MDC_PROPERTIES, mdcProperties);
            }
        }

        /**
         * Adds the LocationInfo object to an existing BSON object.
         * 
         * @param bson
         *            The BSON object to add the location info to <i>(must not be null)</i>.
         * @param locationInfo
         *            The LocationInfo object to add to the BSON object <i>(may be null)</i>.
         */
        protected void addLocationInformation(Document bson, final LocationInfo locationInfo) {
            if (locationInfo != null) {
                nullSafePut(bson, KEY_FILE_NAME, locationInfo.getFileName());
                nullSafePut(bson, KEY_METHOD, locationInfo.getMethodName());
                nullSafePut(bson, KEY_LINE_NUMBER, locationInfo.getLineNumber());
                nullSafePut(bson, KEY_CLASS, bsonifyClassName(locationInfo.getClassName()));
            }
        }

        /**
         * Adds the ThrowableInformation object to an existing BSON object.
         * 
         * @param bson
         *            The BSON object to add the throwable info to <i>(must not be null)</i>.
         * @param throwableInfo
         *            The ThrowableInformation object to add to the BSON object <i>(may be null)</i>.
         */
        @SuppressWarnings(value = "unchecked")
        protected void addThrowableInformation(Document bson, final ThrowableInformation throwableInfo) {
            if (throwableInfo != null) {
                Throwable currentThrowable = throwableInfo.getThrowable();
                @SuppressWarnings("rawtypes")
    			List throwables = new BasicDBList();

                while (currentThrowable != null) {
                	Document throwableBson = bsonifyThrowable(currentThrowable);

                    if (throwableBson != null) {
                        throwables.add(throwableBson);
                    }

                    currentThrowable = currentThrowable.getCause();
                }

                if (throwables.size() > 0) {
                    bson.put(KEY_THROWABLES, throwables);
                }
            }
        }

        /**
         * Adds the current process's host name, VM name and IP address
         * 
         * @param bson
         *            A BSON object containing host name, VM name and IP address
         */
        protected void addHostnameInformation(Document bson) {
            nullSafePut(bson, KEY_HOST, hostInfo);
        }

        /**
         * BSONifies the given Throwable.
         * 
         * @param throwable
         *            The throwable object to BSONify <i>(may be null)</i>.
         * @return The BSONified equivalent of the Throwable object <i>(may be null)</i>.
         */
        protected Document bsonifyThrowable(final Throwable throwable) {
        	Document result = null;

            if (throwable != null) {
                result = new Document();

                nullSafePut(result, KEY_EXCEPTION_MESSAGE, throwable.getMessage());
                nullSafePut(result, KEY_STACK_TRACE, bsonifyStackTrace(throwable.getStackTrace()));
            }

            return (result);
        }

        /**
         * BSONifies the given stack trace.
         * 
         * @param stackTrace
         *            The stack trace object to BSONify <i>(may be null)</i>.
         * @return The BSONified equivalent of the stack trace object <i>(may be null)</i>.
         */
        protected DBObject bsonifyStackTrace(final StackTraceElement[] stackTrace) {
            BasicDBList result = null;

            if (stackTrace != null && stackTrace.length > 0) {
                result = new BasicDBList();

                for (StackTraceElement element : stackTrace) {
                	Document bson = bsonifyStackTraceElement(element);

                    if (bson != null) {
                        result.add(bson);
                    }
                }
            }

            return (result);
        }

        /**
         * BSONifies the given stack trace element.
         * 
         * @param element
         *            The stack trace element object to BSONify <i>(may be null)</i>.
         * @return The BSONified equivalent of the stack trace element object <i>(may be null)</i>.
         */
        protected Document bsonifyStackTraceElement(final StackTraceElement element) {
        	Document result = null;

            if (element != null) {
                result = new Document();

                nullSafePut(result, KEY_FILE_NAME, element.getFileName());
                nullSafePut(result, KEY_METHOD, element.getMethodName());
                nullSafePut(result, KEY_LINE_NUMBER, element.getLineNumber());
                nullSafePut(result, KEY_CLASS, bsonifyClassName(element.getClassName()));
            }

            return (result);
        }

        /**
         * BSONifies the given class name.
         * 
         * @param className
         *            The class name to BSONify <i>(may be null)</i>.
         * @return The BSONified equivalent of the class name <i>(may be null)</i>.
         */
        @SuppressWarnings(value = "unchecked")
        protected DBObject bsonifyClassName(final String className) {
            DBObject result = null;

            if (className != null && className.trim().length() > 0) {
                result = new BasicDBObject();

                result.put(KEY_FQCN, className);

                @SuppressWarnings("rawtypes")
    			List packageComponents = new BasicDBList();
                String[] packageAndClassName = className.split("\\.");

                packageComponents.addAll(Arrays.asList(packageAndClassName));
                // Requires Java 6
                // packageComponents.addAll(Arrays.asList(Arrays.copyOf(packageAndClassName,
                // packageAndClassName.length - 1)));

                if (packageComponents.size() > 0) {
                    result.put(KEY_PACKAGE, packageComponents);
                }

                result.put(KEY_CLASS_NAME, packageAndClassName[packageAndClassName.length - 1]);
            }

            return (result);
        }

        /**
         * Adds the given value to the given key, except if it's null (in which case this method does
         * nothing).
         * 
         * @param bson
         *            The BSON object to add the key/value to <i>(must not be null)</i>.
         * @param key
         *            The key of the object <i>(must not be null)</i>.
         * @param value
         *            The value of the object <i>(may be null)</i>.
         */
        protected void nullSafePut(Document bson, final String key, final Object value) {
            if (value != null) {
                if (value instanceof String) {
                    String stringValue = (String) value;
                    if (stringValue.trim().length() > 0) {
                        bson.put(key, stringValue);
                    }
                } else if (value instanceof StringBuffer) {
                    String stringValue = ((StringBuffer) value).toString();
                    if (stringValue.trim().length() > 0) {
                        bson.put(key, stringValue);
                    }
                } else {
                    bson.put(key, value);
                }
            }
        }

    }

}
