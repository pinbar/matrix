package com.percipient.matrix.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.percipient.matrix.dao.DatabaseRepository;

public interface DatabaseService {

	public List<?> getTableData(String entityName);
	
}

@Service
class DatabaseServiceImpl implements DatabaseService {

	@Autowired
	private DatabaseRepository databaseRepository;

	@Override
	@Transactional
	public List<?> getTableData(String entityName) {
		return databaseRepository.getTableData(entityName);
	}

}
