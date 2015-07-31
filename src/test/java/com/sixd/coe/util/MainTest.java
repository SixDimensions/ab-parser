package com.sixd.coe.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

public class MainTest {

	@Test
	public void basicTest() throws FileNotFoundException, IOException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {

		Main.main(new String[] {
				MainTest.class.getClassLoader().getResource("test.txt")
						.getPath(), "target/report.csv" });
	}
}
