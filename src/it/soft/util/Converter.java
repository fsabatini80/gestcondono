package it.soft.util;

import it.soft.exception.CustomException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Converter {

	public static Date stringToDate(String data) {

		if (data == null)
			return null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
		try {
			return dateFormat.parse(data);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String dateToString(Date data) {
		if (data == null)
			return null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
		return dateFormat.format(data);
	}

	public static boolean byteToBoolean(byte b) {
		if (b == 0)
			return false;
		return true;
	}

	public static Date convertData(String dataDomanda) {
		dataDomanda = dataDomanda.replaceAll("-", "/");
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
		try {
			return dateFormat.parse(dataDomanda);
		} catch (ParseException e) {
			throw new CustomException("99", e.getMessage());
		}
	}

	public static Double convertLireEuro(Double lire) {
		return round(lire / 1927.36, 2);
	}

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}
}
