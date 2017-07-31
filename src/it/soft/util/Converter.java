package it.soft.util;

import it.soft.exception.CustomException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Converter {

    public static Date stringToDate(String data) {

	if (data == null)
	    return null;
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
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
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	return dateFormat.format(data);
    }

    public static boolean byteToBoolean(byte b) {
	if (b == 0)
	    return false;
	return true;
    }

    public static Date convertData(String dataDomanda) {
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	try {
	    return dateFormat.parse(dataDomanda);
	} catch (ParseException e) {
	    throw new CustomException("99", e.getMessage());
	}
    }

    public static Date convertData(String dataDomanda, String format) {
	SimpleDateFormat dateFormat = new SimpleDateFormat(format);
	try {
	    return dateFormat.parse(dataDomanda);
	} catch (ParseException e) {
	    throw new CustomException("99", e.getMessage());
	}
    }

    /**
     * converte date stringa formattate dd-MM-yyyy
     * 
     * @param data
     * @return Double nel formato yyyyMMdd
     */

    public static Double dateToDouble(String data) {

	try {
	    DateFormat originalFormat = new SimpleDateFormat("dd-MM-yyyy");
	    DateFormat targetFormat = new SimpleDateFormat("yyyyMMdd");
	    Date date = originalFormat.parse(data);
	    String formattedDate = targetFormat.format(date);
	    return new Double(formattedDate);
	} catch (Exception e) {
	    throw new CustomException("99", e.getMessage());
	}
    }

    /**
     * converte date stringa formattate parametro
     * 
     * @param data
     * @return Double nel formato yyyyMMdd
     */

    public static Double dateToDouble(String data, String format) {

	try {
	    DateFormat originalFormat = new SimpleDateFormat(format);
	    DateFormat targetFormat = new SimpleDateFormat("yyyyMMdd");
	    Date date = originalFormat.parse(data);
	    String formattedDate = targetFormat.format(date);
	    return new Double(formattedDate);
	} catch (Exception e) {
	    throw new CustomException("99", e.getMessage());
	}
    }

    public static Double convertLireEuro(Double lire) {
	return round(lire / 1936.27, 2);
    }

    public static double round(double value, int places) {
	if (places < 0)
	    throw new IllegalArgumentException();

	long factor = (long) Math.pow(10, places);
	value = value * factor;
	long tmp = Math.round(value);
	return (double) tmp / factor;
    }

    public static Boolean checkDoubleFormat(String doubleValueString) {
	String[] s = doubleValueString.split("\\.");
	// if (s.length != 2)
	// return false;

	try {
	    new Integer(s[0]);
	    // new Integer(s[1]);
	    // if (s[1].length() > 2)
	    // return false;
	} catch (NumberFormatException e) {
	    return false;
	}

	return true;
    }

    public static Boolean checkDoubleFormat(Double doubleValueString) {

	try {
	    new Double(doubleValueString);
	} catch (NumberFormatException e) {
	    return false;
	}

	return true;
    }

    public static Boolean checkIntegerFormat(String intValueString) {

	try {
	    new Integer(intValueString);
	} catch (Exception e) {
	    return false;
	}

	return true;
    }

    public static String doubleToString(Double input) {

	if (input == null)
	    return "0.00";
	input = round(input, 2);
	String inputstr = input.toString();
	if (inputstr.indexOf(".") > 0) {
	    if (inputstr.substring(inputstr.indexOf(".") + 1).length() < 2) {
		inputstr = inputstr.concat("0");
	    }
	}

	return inputstr;
    }

    /**
     * Per i parametri di urbanizzazione tranne che per le destinazioni
     * residenziali commerciale turistiche idustriale artigianale che hanno
     * delle tabelle specifiche utilizzare i parametri della tabella
     * residenziale
     * 
     * @param destinazioneUso
     * @return
     */
    public static String parseDestinazioneUsoPerUrbanizzazione(
	    String destinazioneUso) {

	if (!Constants.DEST_USO_RESIDENZIALE.equals(destinazioneUso)
		&& !Constants.DEST_USO_COMMERCIALE.equals(destinazioneUso)
		&& !Constants.DEST_USO_TURISTICO.equals(destinazioneUso)
		&& !Constants.DEST_USO_INDUSTRIALE_ARTIGIANALE
			.equals(destinazioneUso)) {
	    return Constants.DEST_USO_RESIDENZIALE;

	}

	return destinazioneUso;
    }

    public static BigDecimal stringToBigDdecimal(String s) {
	return new BigDecimal(s);
    }

    public static int stringToint(String s) {
	return Integer.parseInt(s);
    }
    
    public static BigInteger stringToBigInteger(String s) {
	return new BigInteger(s);
    }
}
