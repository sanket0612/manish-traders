package com.manishtraders.repository;

import com.manishtraders.model.TallyLedger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LedgerRepository extends JpaRepository<TallyLedger,Long> {

}
