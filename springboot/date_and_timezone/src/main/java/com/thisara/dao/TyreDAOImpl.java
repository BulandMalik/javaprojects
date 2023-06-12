package com.thisara.dao;

import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;

import com.thisara.domain.Tyre;
import com.thisara.exception.DAOException;
import com.thisara.exception.ErrorCode;

@Repository
public class TyreDAOImpl implements TyreDAO {

	private static final Logger logger = Logger.getLogger(TyreDAOImpl.class.getName());

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void save(Tyre tyre) throws DAOException {
		try {

			entityManager.persist(tyre);

		} catch (ConstraintViolationException e) {
			logger.severe(e.getMessage());
			throw new DAOException(ErrorCode.SQL101.name());
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new DAOException(ErrorCode.GEN100.name());
		}
	}
}
