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
	
	public static Double convertLireEuro(Double lire){
		return lire / 1927.36;
	}
}
