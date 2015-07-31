package com.sixd.coe.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

public class Main {

	public static void main(String[] args) throws FileNotFoundException,
			IOException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {

		if (args.length != 2) {
			System.err.println("Please specify the in and out files!");
			System.exit(1);
		}

		File in = new File(args[0]);
		File out = new File(args[1]);

		if (!in.exists()) {
			System.err.println("File " + args[0] + " does not exist!");
			System.exit(1);
		}

		List<File> files = new ArrayList<File>();
		if (in.isDirectory()) {
			for (File file : in.listFiles()) {
				files.add(file);
			}
		} else {
			files.add(in);
		}

		if (!out.exists()) {
			out.createNewFile();
		}

		List<ABResultModel> models = new ArrayList<ABResultModel>();
		for (File file : files) {
			ABResultModel model = new ABResultModel(file.getName());
			List<String> lines = IOUtils.readLines(new FileInputStream(file));
			for (String line : lines) {
				importLine(model, line);
			}
			System.out.println("Loaded: " + model.toString() + " from "
					+ file.getAbsolutePath());
			models.add(model);
		}

		StringWriter writer = new StringWriter();
		try {
			final CSVPrinter printer = new CSVPrinter(writer, CSVFormat.EXCEL);
			writeHeaders(printer, ABResultModel.class);
			for (ABResultModel model : models) {
				System.out.println("Writing model: "
						+ model.getConcurrencyLevel());
				writeModel(printer, model);
			}
		} finally {
			//
		}
		IOUtils.write(writer.toString(), new FileOutputStream(out));
		System.out.println("Wrote results to: " + out.getAbsolutePath());
	}

	private static void writeModel(CSVPrinter printer, ABResultModel model)
			throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, IOException {
		List<String> values = new ArrayList<String>();
		for (Method m : model.getClass().getMethods()) {
			if (m.getAnnotation(OutputCfg.class) != null) {
				m.setAccessible(true);
				values.add(String.class.cast(m.invoke(model, new Object[0])));
			}
		}
		printer.printRecord(values);
	}

	private static void writeHeaders(CSVPrinter printer,
			Class<ABResultModel> class1) throws IOException {
		List<String> headers = new ArrayList<String>();
		for (Method m : class1.getMethods()) {
			if (m.getAnnotation(OutputCfg.class) != null) {
				headers.add(m.getAnnotation(OutputCfg.class).title());
			}
		}
		printer.printRecord(headers);
	}

	private static void importLine(ABResultModel model, String line) {
		for (Method m : model.getClass().getDeclaredMethods()) {
			if (m.getAnnotation(ParseCfg.class) != null) {
				m.setAccessible(true);
				ParseCfg config = m.getAnnotation(ParseCfg.class);
				if (line.matches(config.test())) {
					try {
						int start = line.indexOf(config.startAfter())
								+ (config.startAfter().length() - 1);
						if (!StringUtils.isEmpty(config.terminateAt())) {
							int end = line.indexOf(config.terminateAt(),
									start + 1);
							m.invoke(model, line.substring(start + 1, end)
									.trim());
						} else {
							m.invoke(model, line.substring(start + 1).trim());
						}
					} catch (Exception e) {
						System.err.println("Exception parsing line " + line
								+ " for method " + m.getName());
					}
				}
			}
		}
	}
}
