package com.mouhcine.ebankbackend.repositories;

import com.mouhcine.ebankbackend.entities.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountOperationRepository extends JpaRepository<AccountOperation,Long> {
}
