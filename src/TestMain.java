import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// System.out.println(checkDoubleFormat("2511.22").toString());
		System.out.println(convertDateToDouble("22-02-1983"));
		System.exit(1);
	}

	public static Double convertDateToDouble(String data) {
		
		try {
			DateFormat originalFormat = new SimpleDateFormat("dd-MM-yyyy");
			DateFormat targetFormat = new SimpleDateFormat("yyyyMMdd");
			Date date = originalFormat.parse(data);
			String formattedDate = targetFormat.format(date);
			System.out.println(formattedDate);
			return new Double(formattedDate);
		} catch (Exception e) {
			// throw new CustomException("99", e.getMessage());
			return null;
		}
	}

	public static Boolean checkDoubleFormat(String doubleValueString) {
		String[] s = doubleValueString.split("\\.");
		if (s.length != 2)
			return false;

		try {
			new Integer(s[0]);
			new Integer(s[1]);
		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}

}
