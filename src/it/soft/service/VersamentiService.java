package it.soft.service;

import it.soft.dao.DatiVersamentiHome;
import it.soft.domain.DatiVersamento;
import it.soft.web.pojo.DatiVersamentiPojo;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VersamentiService {

	@Autowired
	DatiVersamentiHome datiVersamentiHome;

	public void persist(DatiVersamentiPojo pojo) {
		DatiVersamento versamenti = new DatiVersamento();

		versamenti.setCausale(pojo.getCausale());
		versamenti.setCcPostale(pojo.getCcPostale());
		versamenti.setCodiceVersamento(pojo.getCodiceVersamento());
		versamenti.setCognome(pojo.getCognome());
		versamenti.setDataInserimento(pojo.getDataInserimento());
		versamenti.setDataProtocollo(pojo.getDataProtocollo());
		versamenti.setDataVersamento(pojo.getDataVersamento());
		versamenti.setIban(pojo.getIban());
		// versamenti.setIddati_versamento(iddati_versamento)
		versamenti.setIddatipratica(BigInteger.valueOf(Integer.parseInt(pojo
				.getIddatipratica())));
		versamenti.setImporto(pojo.getImporto());
		versamenti.setImportoEuro(pojo.getImportoEuro());
		versamenti.setImportoLire(pojo.getImportoLire());
		versamenti.setNome(pojo.getNome());
		versamenti.setNumeroBollettino(pojo.getNumeroBollettino());
		versamenti.setNumeroProtocollo(pojo.getNumeroProtocollo());
		versamenti.setRagioneSociale(pojo.getRagioneSociale());
		versamenti.setUfficioPostale(pojo.getUfficioPostale());

		datiVersamentiHome.persist(versamenti);
	}

}
