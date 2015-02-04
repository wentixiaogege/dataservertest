package com.itu.rest;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import com.itu.util.Log4jUtil;

public class Log4jTest extends TestCase {

	// static final Logger logger = Logger.getLogger(Log4jTest.class);

	@Override
	protected void setUp() throws Exception {
	}

	@Override
	protected void tearDown() throws Exception {
	}

	public void testLog() {
		// Configure logger
		// BasicConfigurator.configure();
		// PropertyConfigurator.configure("log4j.properties");
		Logger logger = Log4jUtil.getLogger(Log4jTest.class);
		logger.info("logger is running correctly");
		// logger.debug("Hello World!");
		// logger.info("Info");
		// logger.warn("warning!");
		// logger.error("error");
	}
}
