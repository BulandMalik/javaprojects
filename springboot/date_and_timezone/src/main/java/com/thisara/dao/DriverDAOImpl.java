package com.thisara.dao;

import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.thisara.domain.Driver;
import com.thisara.exception.DAOException;
import com.thisara.exception.ErrorCode;

@Repository
public class DriverDAOImpl implements DriverDAO {

	private static Logger logger = Logger.getLogger(DriverDAOImpl.class.getName());

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Driver getReference(Long id) throws DAOException {

		Driver driver = null;

		try {

			driver = entityManager.getReference(Driver.class, id);

		} catch (EntityNotFoundException e) {
			logger.severe(e.getMessage());
			throw new DAOException(ErrorCode.SQL104.name());
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new DAOException(ErrorCode.GEN100.name());
		}

		return driver;
	}

}
