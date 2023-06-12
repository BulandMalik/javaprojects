package com.thisara.dao;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.QueryTimeoutException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.springframework.stereotype.Repository;

import com.thisara.domain.Car;
import com.thisara.exception.DAOException;
import com.thisara.exception.ErrorCode;

@Repository
public class CarDAOImpl implements CarDAO {

	private static Logger logger = Logger.getLogger(CarDAOImpl.class.getName());

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Car> search(String registrationNumber) throws DAOException {

		List<Car> carsList = null;

		try {

			TypedQuery<Car> query = entityManager
					.createQuery("FROM Car c WHERE c.registrationNumber LIKE :registrationNumber ", Car.class);

			query.setParameter("registrationNumber", registrationNumber + "%");

			carsList = query.getResultList();

		} catch (QueryTimeoutException e) {
			logger.severe(e.getMessage());
			throw new DAOException(ErrorCode.SQL101.name());
		} catch (DataException e) {
			logger.severe(e.getMessage());
			throw new DAOException(ErrorCode.SQL102.name());
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new DAOException(ErrorCode.GEN100.name());
		}

		return carsList;
	}

	@Override
	public Car getCar(String registrationNumber) throws DAOException {

		Car car = null;

		try {

			TypedQuery<Car> query = entityManager.createQuery(
					"SELECT c FROM Car c, Tyre t WHERE c.id = t.car.id AND c.registrationNumber = :registrationNumber ",
					Car.class);

			query.setParameter("registrationNumber", registrationNumber);

			car = (Car) query.getSingleResult();

		} catch (EntityNotFoundException e) {
			logger.severe(e.getMessage());
			throw new DAOException(ErrorCode.SQL104.name());
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new DAOException(ErrorCode.GEN100.name());
		}

		return car;
	}

	@Override
	public Car getReference(Long id) throws DAOException {
		Car car = null;

		try {

			car = entityManager.getReference(Car.class, id);

		} catch (EntityNotFoundException e) {
			logger.severe(e.getMessage());
			throw new DAOException(ErrorCode.SQL104.name());
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new DAOException(ErrorCode.GEN100.name());
		}

		return car;
	}

	@Override
	@Transactional
	public void save(Car car) throws DAOException {

		try {

			entityManager.persist(car);

		} catch (ConstraintViolationException e) {
			logger.severe(e.getMessage());
			throw new DAOException(ErrorCode.SQL101.name());
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new DAOException(ErrorCode.GEN100.name());
		}
	}

	@Override
	public void update(Car car) throws DAOException {
		// TODO - to be developed
	}

	@Override
	public void remove(Car car) throws DAOException {

		try {

			entityManager.remove(car);

		} catch (EntityNotFoundException e) {
			logger.severe(e.getMessage());
			throw new DAOException(ErrorCode.SQL104.name());
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new DAOException(ErrorCode.GEN100.name());
		}
	}

}
