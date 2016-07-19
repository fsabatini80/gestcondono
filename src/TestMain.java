import it.soft.dao.InteressiLegaliHome;
import it.soft.domain.InteressiLegali;
import it.soft.service.MailService;
import it.soft.util.Converter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestMain {

	static ApplicationContext context = new ClassPathXmlApplicationContext(
			"disp-servlet.xml");

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// System.out.println(checkDoubleFormat("2511.22").toString());
		// System.out.println(convertDateToDouble("22-02-1983"));

		calcolaIM();

		System.exit(1);
	}

	private static void calcolaIM() {

		Double dataInizioIM = new Double(19960401);
		Double dataOdierna = new Double(20160719);

		Double importoDeltaOblaCalcAut = new Double(300);
		Double sommaimportoDeltaOblaCalcAut = new Double(300);

		String dataInizioIMString = String.valueOf(dataInizioIM);
		String dataOdiernaString = String.valueOf(dataOdierna);

		dataInizioIMString = dataInizioIMString.replace(".", "");
		dataOdiernaString = dataOdiernaString.replace(".", "");

		String meseGiornoPrimoAnno = dataInizioIMString.substring(4);
		System.out.println("meseGiornoPrimoAnno: " + meseGiornoPrimoAnno);
		String dataFinePrimoAnno = dataInizioIMString.replace(
				meseGiornoPrimoAnno, "1231");
		System.out.println("dataFinePrimoAnno: " + dataFinePrimoAnno);

		Integer annoInizioIM = Integer.parseInt(dataInizioIMString.substring(0,
				4));
		Integer annoOdierna = Integer.parseInt(dataOdiernaString
				.substring(0, 4));
		System.out.println("annoInizioIM: " + annoInizioIM);
		System.out.println("annoOdierna: " + annoOdierna);

		String meseGiornoUltimoAnno = dataOdiernaString.substring(4);
		System.out.println("meseGiornoUltimoAnno: " + meseGiornoUltimoAnno);
		String dataInizioUltimoAnno = dataOdiernaString.replace(
				meseGiornoUltimoAnno, "0101");
		System.out.println("dataInizioUltimoAnno: " + dataInizioUltimoAnno);

//		long ggPrimoAnno = calcolaGiorni(dataInizioIMString.substring(0, 9),
//				dataFinePrimoAnno);
//
//		long ggUltimoAnno = calcolaGiorni(dataInizioUltimoAnno,
//				dataOdiernaString.substring(0, 9));

		long ggInteroAnno = 0;

		Double interessiAnno = new Double(0.0);

		InteressiLegaliHome ilHome = (InteressiLegaliHome) context
				.getBean("interessiLegaliHome");

		// List<InteressiLegali> ilDTini = ilHome.findByDtInizio(new Double(
		// dataInizioUltimoAnno));
		// for (InteressiLegali interessiLegali : ilDTini) {
		// System.out.println(interessiLegali.getGiorni());
		// ggInteroAnno = interessiLegali.getGiorni();
		// interessiAnno = interessiLegali.getPercentuale();
		// }
		//
		// sommaimportoDeltaOblaCalcAut = sommaimportoDeltaOblaCalcAut
		// + applicaPercentuale(importoDeltaOblaCalcAut, ggInteroAnno,
		// ggPrimoAnno, interessiAnno);
		//
		// List<InteressiLegali> ilDTfin = ilHome.findByDtFine(new Double(
		// dataFinePrimoAnno));
		// for (InteressiLegali interessiLegali : ilDTfin) {
		// System.out.println(interessiLegali.getGiorni());
		// ggInteroAnno = interessiLegali.getGiorni();
		// interessiAnno = interessiLegali.getPercentuale();
		// }
		//
		// sommaimportoDeltaOblaCalcAut = sommaimportoDeltaOblaCalcAut
		// + applicaPercentuale(importoDeltaOblaCalcAut, ggInteroAnno,
		// ggUltimoAnno, interessiAnno);
		//
		// Integer annoStart = (annoInizioIM + 1);
		// Integer annoEnd = (annoOdierna - 1);

		Integer annoStart = annoInizioIM;
		Integer annoEnd = annoOdierna;
		for (int i = annoStart; annoStart < annoEnd; annoStart++) {

			String dataInizioAnno = annoStart + "1231";
			List<InteressiLegali> list = ilHome.findByDtFine(new Double(
					dataInizioAnno));

			for (InteressiLegali interessiLegali : list) {
				System.out.println(interessiLegali.getGiorni());
				ggInteroAnno = interessiLegali.getGiorni();
				interessiAnno = interessiLegali.getPercentuale();
			}
			sommaimportoDeltaOblaCalcAut = sommaimportoDeltaOblaCalcAut
					+ applicaPercentuale(importoDeltaOblaCalcAut, ggInteroAnno,
							ggInteroAnno, interessiAnno);
		}
		System.out.println("sommaimportoDeltaOblaCalcAut: "
				+ sommaimportoDeltaOblaCalcAut);
	}

	private static Double applicaPercentuale(Double importoDeltaOblaCalcAut,
			long ggInteroAnno, long ggPrimoAnno, Double interessiAnno) {

		Double importoAnnoIntero = importoDeltaOblaCalcAut
				* (interessiAnno / 100);
		Double percAnnoFraz = (((new Double(ggPrimoAnno) * new Double(100)) / new Double(
				ggInteroAnno)) / new Double(100));
		if (percAnnoFraz.intValue() != 1) {
			System.out.println("percentuale applicata: "
					+ Converter.round((importoAnnoIntero * percAnnoFraz), 2));
			return Converter.round((importoAnnoIntero * percAnnoFraz), 2);
		}
		System.out.println("percentuale applicata: " + importoAnnoIntero);
		return Converter.round(importoAnnoIntero, 2);
	}

	private static long calcolaGiorni(String dataInizio, String dataFine) {

		Date diDbl = Converter.convertData(dataInizio, "yyyyMMdd");
		Date dfDbl = Converter.convertData(dataFine, "yyyyMMdd");

		System.out.println("diDbl: " + diDbl);
		System.out.println("dfDbl: " + dfDbl);
		long answer = ((((dfDbl.getTime() - diDbl.getTime()) / 1000) / 60) / 60) / 24;
		System.out.println(answer);
		return answer;
	}

	public static void sendEmail() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"disp-servlet.xml");

		MailService mm = (MailService) context.getBean("mailService");
		mm.sendMail("from@no-spam.com", "f.sabatini80@gmail.com", "Testing123",
				"Testing only \n\n Hello Spring Email Sender");
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
