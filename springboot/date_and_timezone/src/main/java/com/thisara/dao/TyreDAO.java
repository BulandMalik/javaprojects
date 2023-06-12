package com.thisara.dao;

import com.thisara.domain.Tyre;
import com.thisara.exception.DAOException;

public interface TyreDAO {

	public void save(Tyre tyre) throws DAOException;
}
