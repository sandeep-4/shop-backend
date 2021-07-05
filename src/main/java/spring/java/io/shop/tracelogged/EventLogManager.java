package spring.java.io.shop.tracelogged;


import org.apache.log4j.*;

public class EventLogManager {
	
	private static EventLogManager instance=null;
	
	private Logger log;
	
	public static EventLogManager getInstance() {
		if(instance==null) {
			instance=new EventLogManager();
		}
		return instance;
	}
	
	private EventLogManager() {
		try {
		log=Logger.getRootLogger();
		} catch (Exception ex) {
            java.util.logging.Logger.getLogger(EventLogManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
	}
	
	public void log(String logMessage) {
		if(logMessage !=null && !"".equals(logMessage)) {
			Level loglevel=log.getLevel();
			
			if(loglevel.equals(Level.TRACE)) {
				log.trace( logMessage);
			}
			if (loglevel.equals(Level.DEBUG)) {
                log.debug(logMessage);
            }
            if (loglevel.equals(Level.INFO)) {
                log.info(logMessage);
            }
            if (loglevel.equals(Level.WARN)) {
                log.warn(logMessage);
            }
            if (loglevel.equals(Level.ERROR)) {
                log.error(logMessage);
            }
            if (loglevel.equals(Level.FATAL)) {
                log.fatal(logMessage);
            }
		}
	}
	
	public void debug(Object message) {
		log.debug(message);
	}
	
	public void debug(Object message,Exception e) {
		log.debug(message,e);
	}
	
	public void info(Object message) {
		log.info(message);
	}
	
	public void info(Object message,Exception e) {
		log.info(message,e);
	}
	
	public void warn(Object message) {
		log.warn(message);
	}
	
	public void warn(Object message,Exception e) {
		log.warn(message,e);
	}
	
	public void error(Object message) {
		log.error(message);
	}
	
	public void error(Object message,Exception e) {
		log.error(message,e);
	}
	
	
	public void fatal(Object message) {
		log.fatal(message);
	}
	
	public void fatal(Object message,Exception e) {
		log.fatal(message,e);
	}
}

