package com.creditsuise.coding.test.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.creditsuise.coding.test.model.persistence.Alert;

@Repository
public interface AlertRepository extends CrudRepository<Alert, String> {
}
