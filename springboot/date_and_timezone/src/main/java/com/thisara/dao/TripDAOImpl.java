package com.thisara.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.QueryTimeoutException;

import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.springframework.stereotype.Repository;

import com.thisara.domain.Car;
import com.thisara.domain.Driver;
import com.thisara.domain.Trip;
import com.thisara.exception.DAOException;
import com.thisara.exception.ErrorCode;
import com.thisara.utils.SQLDateParser;

@Repository
public class TripDAOImpl implements TripDAO {

	private static final Logger logger = Logger.getLogger(TripDAOImpl.class.getName());

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@SuppressWarnings("unchecked")
	public List<Trip> search(String date, String carRegistrationNumber) throws DAOException {

		List<Trip> tripList = new ArrayList<Trip>();

		try {

			Query query = entityManager.createQuery("SELECT t, c, d, e FROM Trip t " + "INNER JOIN t.car c "
					+ "INNER JOIN t.driver d " + "INNER JOIN t.car.engine e "
					+ "WHERE t.date > :searchDate AND c.registrationNumber = :carRegistrationNumber");

			query.setParameter("searchDate", SQLDateParser.parseDate(date));
			query.setParameter("carRegistrationNumber", carRegistrationNumber);

			List<Object[]> list = query.getResultList();

			for (Object[] arr : list) {
				Trip trip = (Trip) (arr)[0];
				trip.setCar((Car) (arr)[1]);
				trip.setDriver((Driver) (arr)[2]);
				logger.info(trip.getDriver().getFirstName());
				tripList.add(trip);
			}

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

		return tripList;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Trip> get(String carRegistrationNumber) throws DAOException {

		List<Trip> tripList = new ArrayList<Trip>();

		try {

			Query query = entityManager.createQuery("select t, d from Trip t " + "INNER JOIN t.driver d "
					+ "where t.car.registrationNumber = :registrationNumber");

			query.setParameter("registrationNumber", carRegistrationNumber);

			List<Object[]> list = query.getResultList();

			for (Object[] arr : list) {
				Trip trip = (Trip) (arr)[0];
				trip.setDriver((Driver) (arr)[1]);
				logger.info(trip.getDriver().getFirstName());
				tripList.add(trip);
			}

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

		return tripList;
	}

	@Override
	public void save(Trip trip) throws DAOException {

		try {

			entityManager.persist(trip);

		} catch (ConstraintViolationException e) {
			logger.severe(e.getMessage());
			throw new DAOException(ErrorCode.SQL101.name());
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new DAOException(ErrorCode.GEN100.name());
		}
	}
}
