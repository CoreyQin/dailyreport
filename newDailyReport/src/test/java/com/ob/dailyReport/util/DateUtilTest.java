package com.ob.dailyReport.util;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DateUtilTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFormatDate2String() {
		Calendar cal = Calendar.getInstance();
		cal.set(2014, 11, 4);
		String formatStr = DateUtil.FormatDate2String(cal.getTime());
		assertEquals("2014-12-04", formatStr);
	}

	@Test
	public void testParseString2Date() throws ParseException {
		String dateString = "2014-12-04";
		Date date = DateUtil.parseString2Date(dateString);

		Calendar cal = Calendar.getInstance();
		cal.set(2014, 11, 4, 0, 0, 0);
		Date expected = cal.getTime();
		assertEquals(expected.toString(), date.toString());
	}
	
	

}
