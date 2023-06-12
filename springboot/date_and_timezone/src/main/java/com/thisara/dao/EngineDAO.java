package com.thisara.dao;

import com.thisara.domain.Engine;
import com.thisara.exception.DAOException;

public interface EngineDAO {

	public void save(Engine engine) throws DAOException;
}
