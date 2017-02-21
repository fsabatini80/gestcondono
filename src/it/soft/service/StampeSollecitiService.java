package it.soft.service;

import java.util.List;

import it.soft.dao.StampeSollecitiHome;
import it.soft.domain.StampeSolleciti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StampeSollecitiService {

    @Autowired
    StampeSollecitiHome stampeSollecitiHome;

    public List<StampeSolleciti> search(StampeSolleciti stampeSolleciti) {
	List<StampeSolleciti> list = stampeSollecitiHome
		.search(stampeSolleciti);
	return list;
    }

    public void save(StampeSolleciti stampeSolleciti) {
	stampeSollecitiHome.persist(stampeSolleciti);
    }

    public List<StampeSolleciti> findAll() {
	return stampeSollecitiHome.findAll();
    }

    public StampeSolleciti findById(String idfile) {
	return stampeSollecitiHome.findById(idfile);
    }

}
