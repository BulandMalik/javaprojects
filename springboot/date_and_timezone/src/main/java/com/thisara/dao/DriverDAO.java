package com.thisara.dao;

import com.thisara.domain.Driver;
import com.thisara.exception.DAOException;

public interface DriverDAO {

	public Driver getReference(Long id) throws DAOException;
}
