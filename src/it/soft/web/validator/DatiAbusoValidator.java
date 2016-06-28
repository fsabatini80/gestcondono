package it.soft.web.validator;

import it.soft.dao.DatiPraticaHome;
import it.soft.domain.Datipratica;
import it.soft.util.Converter;
import it.soft.web.pojo.DatiAbusoPojo;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.mysql.jdbc.StringUtils;

@Component
public class DatiAbusoValidator implements Validator {

	@Autowired
	DatiPraticaHome datiPraticaHome;

	public boolean supports(Class<?> arg0) {
		return DatiAbusoPojo.class.equals(arg0);
	}

	public void validate(Object arg0, Errors arg1) {
		ValidationUtils.rejectIfEmptyOrWhitespace(arg1, "destinazioneUso",
				"obbligatorio");
		ValidationUtils.rejectIfEmptyOrWhitespace(arg1, "epocaAbuso",
				"obbligatorio");
		ValidationUtils.rejectIfEmptyOrWhitespace(arg1, "tipologiaAbuso",
				"obbligatorio");
		ValidationUtils.rejectIfEmptyOrWhitespace(arg1,
				"dataUltimazioneLavori", "obbligatorio");

		DatiAbusoPojo pojo = (DatiAbusoPojo) arg0;
		if (!StringUtils.isEmptyOrWhitespaceOnly(pojo.getAutodeterminata())
				&& !Converter.checkDoubleFormat(pojo.getAutodeterminata())) {
			arg1.rejectValue("autodeterminata", "double.format");
		}
		if (!StringUtils.isEmptyOrWhitespaceOnly(pojo.getVolumeTotale())
				&& !Converter.checkDoubleFormat(pojo.getVolumeTotale())) {
			arg1.rejectValue("volumeTotale", "double.format");
		}

		Datipratica dp = datiPraticaHome.findById(BigDecimal.valueOf(Integer
				.parseInt(pojo.getIdPratica())));
		if ("2".equals(String.valueOf(dp.getLeggeCondono().getIdleggiCondono()))) {
			if (abusoNonSanabile(dp, pojo)) {
				arg1.rejectValue("volumeTotale", "abuso.non.sanabile");
				arg1.rejectValue("destinazioneUso", "abuso.non.sanabile");
				arg1.rejectValue("tipoOpera", "abuso.non.sanabile");
			}
		}
	}

	private boolean abusoNonSanabile(Datipratica dp, DatiAbusoPojo pojo) {

		String tipoOpera = pojo.getTipoOpera();
		String destinazioneUso = pojo.getDestinazioneUso();
		String volumeTotateMC = pojo.getVolumeTotale();
		Double volumeTotDouble = new Double(0.0);
		if (volumeTotateMC != null)
			volumeTotDouble = new Double(volumeTotateMC);
		Double volumeMax = new Double(750);
		Double volumePerc = volumeTotDouble * 0.3;

		return (("1".equals(tipoOpera) || "2".equals(tipoOpera))
				&& "1".equals(destinazioneUso) && (volumeTotDouble > volumeMax || volumeTotDouble > volumePerc));
	}

}