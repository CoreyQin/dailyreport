package com.ob.dailyReport.dao;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.dbunit.DBTestCase;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.h2.H2DataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.AfterClass;

import com.ob.dailyReport.db.DataBaseHandler;

public class BaseDaoTest extends DBTestCase {

	public BaseDaoTest() {
	}

	public BaseDaoTest(String name) {
		super(name);
	}
	
	@Override
	protected void setUp() throws Exception {
		initDataBase();
		super.setUp();
		
	}

	private void initDataBase() throws Exception {
		Connection conn = getDatabaseTester().getConnection().getConnection();
		conn.setAutoCommit(true);
		ScriptRunner runner = new ScriptRunner(conn);
		runner.runScript(Resources.getResourceAsReader("newdailyreport.sql"));
		DataBaseHandler.setconnection(conn);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		// delete database

	}

	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(Thread.currentThread().getContextClassLoader().getResourceAsStream("initdataset.xml"));
	}

	/*
	 */
	protected IDatabaseTester getDatabaseTester() throws ClassNotFoundException {
		return new JdbcDatabaseTester("org.h2.Driver", "jdbc:h2:~/dbunitdemo;MODE=MySQL", "sa", "");

	}

	protected void setUpDatabaseConfig(DatabaseConfig config) {
//		config.setProperty(DatabaseConfig.PROPERTY_BATCH_SIZE, new Integer(97));
		config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new H2DataTypeFactory());
	}

	protected DatabaseOperation getSetUpOperation() throws Exception {
		return DatabaseOperation.REFRESH;
	}

	protected DatabaseOperation getTearDownOperation() throws Exception {
		return DatabaseOperation.NONE;
	}

	private void exportData() throws Exception {
		IDatabaseTester testter = new JdbcDatabaseTester("com.mysql.jdbc.Driver",
				"jdbc:mysql://localhost:3306/newdailyreport", "dailyreport", "dailyreport");
		// create DataSet from database.
		IDataSet ds = testter.getConnection().createDataSet();
		// create temp file
		File tempFile = File.createTempFile("temp", "xml");
		// write the content of database to temp file
		FlatXmlDataSet.write(ds, new FileWriter(tempFile), "UTF-8");
	}

	public static void main(String[] args) throws Exception {
		BaseDaoTest test = new BaseDaoTest();
		test.exportData();
	}

}
