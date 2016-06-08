package it.soft.web.validator;

import it.soft.dao.DatiTerreniHome;
import it.soft.domain.DatiTerreni;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.mysql.jdbc.StringUtils;

@Component
public class TerreniValidator implements Validator {

	@Autowired
	DatiTerreniHome datiTerreniHome;

	@Override
	public boolean supports(Class<?> arg0) {
		return DatiTerreni.class.equals(arg0);
	}

	@Override
	public void validate(Object arg0, Errors arg1) {
		DatiTerreni pojo = (DatiTerreni) arg0;

		String sezione = pojo.getSezione();
		String foglio = pojo.getFoglio();
		String particella = pojo.getParticella();
		String sub = pojo.getSubalterno();
		List<DatiTerreni> terreniList = datiTerreniHome.findBy(sezione, foglio,
				particella, sub);
		for (DatiTerreni datiTerreni : terreniList) {
			if (!StringUtils.isEmptyOrWhitespaceOnly(sezione)
					&& !StringUtils.isEmptyOrWhitespaceOnly(foglio)
					&& !StringUtils.isEmptyOrWhitespaceOnly(particella)
					&& !StringUtils.isEmptyOrWhitespaceOnly(sub)) {
				if (!String.valueOf(datiTerreni.getIddatiTerreni()).equals(
						String.valueOf(pojo.getIddatiTerreni())))
					if (sezione.equals(datiTerreni.getSezione())
							&& foglio.equals(datiTerreni.getFoglio())
							&& particella.equals(datiTerreni.getParticella())
							&& sub.equals(datiTerreni.getSubalterno()))
						arg1.rejectValue("foglio", "datiterreni.duplicati");
			}

		}
	}
}
