package com.jsanz.metanalizer.core.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jsanz.metanalizer.core.entity.Ambito;
import com.jsanz.metanalizer.core.entity.Arquetipo;
import com.jsanz.metanalizer.core.entity.ArquetipoMetajuego;
import com.jsanz.metanalizer.core.entity.Formato;
import com.jsanz.metanalizer.core.entity.Fuente;
import com.jsanz.metanalizer.core.entity.Metajuego;
import com.jsanz.metanalizer.core.entity.Proporcion;
import com.jsanz.metanalizer.core.repository.AmbitoRepository;
import com.jsanz.metanalizer.core.repository.ArquetipoMetajuegoRepository;
import com.jsanz.metanalizer.core.repository.ArquetipoRepository;
import com.jsanz.metanalizer.core.repository.FormatoRepository;
import com.jsanz.metanalizer.core.repository.FuenteRepository;
import com.jsanz.metanalizer.core.repository.MetajuegoRepository;
import com.jsanz.metanalizer.core.repository.ProporcionRepository;

@Service
public class MetajuegoService {
	
	Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private FuenteRepository fuenteRepository;

	@Autowired
	private MetajuegoRepository metajuegoRepository;
	
	@Autowired
	private FormatoRepository formatoRepository;

	@Autowired
	private AmbitoRepository ambitoReporsitory;

	@Autowired
	private ArquetipoRepository arquetipoRepository;

	@Autowired
	private ProporcionRepository proporcionRepository;

	@Autowired
	private ArquetipoMetajuegoRepository arquetipoMetajuegoRepository;
	
	public Formato getFormato(int id) {
		return formatoRepository.getOne(id);
	}
	
	public Metajuego getOne(Integer id) {
		return metajuegoRepository.getOne(id);
	}
	
	public void save(Metajuego metajuego) {
		metajuegoRepository.save(metajuego);
	}
	
	public void saveOLD(Metajuego metajuego, List<Arquetipo> listaArquetipo, List<Proporcion> listaProporcion) throws IllegalStateException, SecurityException, HeuristicMixedException, HeuristicRollbackException, RollbackException, SystemException {
		if(metajuego.getId()==null) {
			metajuego.setId(metajuegoRepository.getNextId());
		}
		
		int arquetipoId=arquetipoRepository.getNextId();
		for(Arquetipo arquetipo:listaArquetipo) {
			if(arquetipo.getId()==null) {
				arquetipo.setId(arquetipoId);
			}
			ArquetipoMetajuego arquetipoMetajuego=new ArquetipoMetajuego();
			arquetipoMetajuego.setId(arquetipoMetajuegoRepository.getNextId());
			arquetipoMetajuego.setIdArquetipo(arquetipo.getId());
			arquetipoMetajuego.setIdMetajuego(metajuego.getId());
			arquetipoMetajuegoRepository.save(arquetipoMetajuego);
			int proporcionId=proporcionRepository.getNextId();
			for(Proporcion proporcion:listaProporcion) {
				if(proporcion.getIdArquetipo().equals(arquetipo.getId())) {
					proporcion.setIdMetajuego(metajuego.getId());
					if(proporcion.getId()==null) {
						proporcion.setId(proporcionId);
					}
					proporcionRepository.save(proporcion);
					proporcionId++;
				}
			}
			arquetipoRepository.save(arquetipo);
			arquetipoId++;
		}
		metajuegoRepository.save(metajuego);
		logger.debug("save metajuego {}  del formato {} con fuente {} y ambito {}",metajuego,metajuego.getFormato(),metajuego.getFuente(),metajuego.getAmbito());																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																											metajuegoRepository.save(metajuego);
	}

	public List<Metajuego> findAll() {
		return metajuegoRepository.findAll();
	}

	public MetajuegoRepository getMetajuegoRepository() {
		return metajuegoRepository;
	}

	public void setMetajuegoRepository(MetajuegoRepository metajuegoRepository) {
		this.metajuegoRepository = metajuegoRepository;
	}

	public Ambito getAmbitoMundial() {
		Ambito ambito=this.ambitoReporsitory.getAmbitoByNombre("MUNDIAL");
		return ambito;
	}

	public Fuente getFuenteWebScraping() {
		return this.fuenteRepository.getOne(1);
	}

	/**
	 * 1 webscraping
	 * 2 test
	 * @param i
	 * @return
	 */
	public Fuente getFuente(int i) {
		return this.fuenteRepository.getOne(1);
	}

	public Integer getNextIdMetajuego() {
		return metajuegoRepository.getNextId();
	}

	public Integer getNextIdArquetipo() {
		return arquetipoRepository.getNextId();
	}

	public Integer getNextIdProporcion() {
		return proporcionRepository.getNextId();
	}

	public Metajuego getConsolidadoByAmbitoBetweenFechas(Ambito ambito, XMLGregorianCalendar fechaInicio,
			XMLGregorianCalendar fechaFin) throws Exception {
		if(ambito!=null && fechaInicio!=null && fechaFin!=null) {
				return metajuegoRepository.getConsolidadoByAmbitoBetweenFechas(ambito.getId(),fechaInicio.toGregorianCalendar().getTime(),
						fechaFin.toGregorianCalendar().getTime());
		}else if(ambito!=null && fechaInicio==null && fechaFin!=null){
			return metajuegoRepository.getConsolidadoByAmbitoBeforeFecha(ambito.getId(),fechaFin.toGregorianCalendar().getTime());
		}else if(ambito!=null && fechaInicio!=null && fechaFin==null) {
			return metajuegoRepository.getConsolidadoByAmbitoAfterFecha(ambito.getId(),fechaInicio.toGregorianCalendar().getTime());
		}else if(ambito!=null) {
			return metajuegoRepository.getConsolidadoByAmbito(ambito.getId());
		}else {
			throw new Exception("intenta buscar un metajuego sin ambito");
		}
		
	}

	public List<Arquetipo> getArquetipoByMetajuego(Integer id) {
		return arquetipoRepository.getByMetajuegoId(id);
	}

	public int getNextIdArquetipoMetajuego() {
		return arquetipoRepository.getNextArquetipoMetajuegoId();
	}

	public Ambito getAmbitoByNombre(String string) {
		return ambitoReporsitory.getAmbitoByNombre(string);
	}

	public Integer getNextMetajuegoId() {
		return metajuegoRepository.getNextId();
	}

	public Integer getNextIdAmbito() {
		return ambitoReporsitory.getNextId();
	}

	public Proporcion getProporcionByIdMetajuegoIdArquetipo(Integer id, Integer id2) {
		return proporcionRepository.getProporcionByIdMetajuegoIdArquetipo(id, id2);
	}



}
