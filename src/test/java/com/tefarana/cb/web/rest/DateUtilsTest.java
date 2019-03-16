package com.tefarana.cb.web.rest;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

import org.junit.Test;

import com.tefarana.cb.DateUtils;

import junit.framework.Assert;

public class DateUtilsTest {
	
	@Test
	public void converterTest() {
		Date now = new Date();
		LocalDate input = DateUtils.asLocalDate(now);
		Date firstPeriod = Date.from(input.atStartOfDay(ZoneOffset.UTC).toInstant());
		Assert.assertEquals(now, firstPeriod);
	}

}
