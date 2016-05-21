package it.soft.service;

import it.soft.dao.DatiPraticaHome;
import it.soft.dao.LeggiCondonoHome;
import it.soft.domain.Datipratica;
import it.soft.domain.LeggiCondono;
import it.soft.domain.Richiedente;
import it.soft.util.Converter;
import it.soft.web.pojo.DatiPraticaPojo;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatiPraticaService {

	@Autowired
	DatiPraticaHome datiPraticaHome;
	@Autowired
	LeggiCondonoHome leggiCondonoHome;

	public void saveDatiPratica(DatiPraticaPojo pojo) {
		Datipratica datipratica;
		if (pojo.getIddatipratica() != null
				&& !"".equals(pojo.getIddatipratica()))
			datipratica = datiPraticaHome.findById(BigDecimal.valueOf(Integer
					.parseInt(pojo.getIddatipratica())));
		else
			datipratica = new Datipratica();
		if (pojo.getDataDomanda() != null && !"".equals(pojo.getDataDomanda()))
			datipratica.setDataDomanda(pojo.getDataDomanda());
		if (pojo.getDataProtocollo() != null
				&& !"".equals(pojo.getDataProtocollo()))
			datipratica.setDataProtocollo(pojo.getDataProtocollo());
		if (pojo.getLeggeCondono() != null)
			datipratica.setLeggeCondono(leggiCondonoHome.findById(Integer
					.valueOf(pojo.getLeggeCondono())));
		datipratica.setNumeroPratica(pojo.getNumeroPratica());
		datipratica.setNumeroProtocollo(pojo.getNumeroProtocollo());
		datipratica.setIdUtente(BigDecimal.valueOf(Integer.parseInt(pojo
				.getIdutente())));

		Richiedente richiedente = new Richiedente();
		if (datipratica.getRichiedente() != null)
			richiedente = datipratica.getRichiedente();
		richiedente.setCap(pojo.getCap());
		richiedente.setCodiceFiscale(pojo.getCodiceFiscale());
		richiedente.setCognome(pojo.getCognome());
		richiedente.setComuneEsteroNas(pojo.getComuneEsteroNas());
		richiedente.setComuneEsteroRes(pojo.getComuneEsteroRes());
		richiedente.setComuneNascita(pojo.getComuneNascita());
		richiedente.setComuneResidenza(pojo.getComuneResidenza());
		if (pojo.getDataNascita() != null && !"".equals(pojo.getDataNascita()))
			richiedente.setDataNascita(pojo.getDataNascita());
		richiedente.setEmail(pojo.getEmail());
		richiedente.setIndirizzo(pojo.getIndirizzo());
		richiedente.setNome(pojo.getNome());
		richiedente.setPartitaIva(pojo.getPartitaIva());
		richiedente.setProvinciaNascita(pojo.getProvinciaNascita());
		richiedente.setProvinciaResidenza(pojo.getProvinciaResidenza());
		richiedente.setRagioneSociale(pojo.getRagioneSociale());
		richiedente.setStatoEsteroNas(pojo.getStatoEsteroNas());
		richiedente.setStatoEsteroRes(pojo.getStatoEsteroRes());
		richiedente.setTelefono(pojo.getTelefono());
		richiedente.setIsvalid((byte) 0);
		richiedente.setDatipratica(datipratica);
		datipratica.setRichiedente(richiedente);
		datiPraticaHome.persist(datipratica);
		if (pojo.getIddatipratica() == null
				|| "".equals(pojo.getIddatipratica().trim()))
			pojo.setIddatipratica(datipratica.getIddatipratica().toString());

	}

	public DatiPraticaPojo findById(String id) {
		Datipratica source = datiPraticaHome.findById(BigDecimal
				.valueOf(Integer.parseInt(id)));
		DatiPraticaPojo target = new DatiPraticaPojo();
		target.setCap(source.getRichiedente() != null ? source.getRichiedente()
				.getCap() : "");
		target.setCodiceFiscale(source.getRichiedente() != null ? source
				.getRichiedente().getCodiceFiscale() : "");
		target.setCognome(source.getRichiedente() != null ? source
				.getRichiedente().getCognome() : "");
		target.setComuneEsteroNas(source.getRichiedente() != null ? source
				.getRichiedente().getComuneEsteroNas() : "");
		target.setComuneEsteroRes(source.getRichiedente() != null ? source
				.getRichiedente().getComuneEsteroRes() : "");
		target.setComuneNascita(source.getRichiedente() != null ? source
				.getRichiedente().getComuneNascita() : "");
		target.setComuneResidenza(source.getRichiedente() != null ? source
				.getRichiedente().getComuneResidenza() : "");
		target.setDataDomanda(source.getDataDomanda());
		target.setDataNascita(source.getRichiedente() != null ? source
				.getRichiedente().getDataNascita() : null);
		target.setDataProtocollo(source.getDataProtocollo());
		target.setEmail(source.getRichiedente() != null ? source
				.getRichiedente().getEmail() : "");
		target.setIddatipratica(String.valueOf(source.getIddatipratica()));
		target.setIdrichiedente(source.getRichiedente() != null ? source
				.getRichiedente().getIdrichiedente() : "");
		target.setIdutente(String.valueOf(source.getIdUtente()));
		target.setIndirizzo(source.getRichiedente() != null ? source
				.getRichiedente().getIndirizzo() : "");
		target.setIsvalid(Converter.byteToBoolean(source.getIsvalid())
				&& Converter
						.byteToBoolean(source.getRichiedente().getIsvalid()));
		if (source.getLeggeCondono() != null)
			target.setLeggeCondono(String.valueOf(source.getLeggeCondono()
					.getIdleggiCondono()));
		target.setNome(source.getRichiedente() != null ? source
				.getRichiedente().getNome() : "");
		target.setNumeroPratica(source.getNumeroPratica());
		target.setNumeroProtocollo(source.getNumeroProtocollo());
		target.setPartitaIva(source.getRichiedente() != null ? source
				.getRichiedente().getPartitaIva() : "");
		target.setProvinciaNascita(source.getRichiedente() != null ? source
				.getRichiedente().getProvinciaNascita() : "");
		target.setProvinciaResidenza(source.getRichiedente() != null ? source
				.getRichiedente().getProvinciaResidenza() : "");
		target.setRagioneSociale(source.getRichiedente() != null ? source
				.getRichiedente().getRagioneSociale() : "");
		target.setStatoEsteroNas(source.getRichiedente() != null ? source
				.getRichiedente().getStatoEsteroNas() : "");
		target.setStatoEsteroRes(source.getRichiedente() != null ? source
				.getRichiedente().getStatoEsteroRes() : "");
		target.setTelefono(source.getRichiedente() != null ? source
				.getRichiedente().getTelefono() : "");
		return target;
	}

	public List<Datipratica> findAll() {
		return datiPraticaHome.findAll();
	}

	public List<Datipratica> findBy(String numeroPratica,
			LeggiCondono leggeCondono, String dataDomanda,
			String numeroProtocollo) {
		return datiPraticaHome.findBy(numeroPratica, numeroProtocollo,
				dataDomanda, leggeCondono);
	}
}
