package io.fourfinanceit.loan.model;

import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Long> {

	Client findById(long id);
}
