package com.ob.dailyReport.dao;

import java.sql.SQLException;
import java.util.List;

import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;

public class TeamDaoTest extends BaseDaoTest {

	
	@Test
	public void testAddTeam() throws SQLException, Exception {

		TeamDao.addTeam("Kronos Team6");

		List<String> teamList = TeamDao.getTeamList();

		ITable actualTable = getConnection().createTable("team");

		// Load expected data from an XML dataset
		IDataSet expectedDataSet = new FlatXmlDataSetBuilder()
				.build(Thread.currentThread().getContextClassLoader().getResourceAsStream("dataset.xml"));
		ITable expectedTable = expectedDataSet.getTable("team");

		// Assert actual database table match expected table
		Assertion.assertEquals(expectedTable, actualTable);
	}

}
