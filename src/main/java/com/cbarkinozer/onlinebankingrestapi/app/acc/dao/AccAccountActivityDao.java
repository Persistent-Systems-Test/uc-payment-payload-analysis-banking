package com.cbarkinozer.onlinebankingrestapi.app.acc.dao;

import com.cbarkinozer.onlinebankingrestapi.app.acc.entity.AccAccountActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AccAccountActivityDao extends JpaRepository<AccAccountActivity,Long> {

    List<AccAccountActivity> findAllByAccountIdAndTransactionDateBetweenOrderByTransactionDateAsc(
            Long accountId, LocalDate startDate, LocalDate endDate);
}
