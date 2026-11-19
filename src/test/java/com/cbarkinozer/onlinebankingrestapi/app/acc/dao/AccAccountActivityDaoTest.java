package com.cbarkinozer.onlinebankingrestapi.app.acc.dao;

import com.cbarkinozer.onlinebankingrestapi.app.acc.entity.AccAccountActivity;
import com.cbarkinozer.onlinebankingrestapi.app.acc.enums.AccAccountActivityType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AccAccountActivityDaoTest {

    @Autowired
    private AccAccountActivityDao accAccountActivityDao;

    @BeforeEach
    void setUp() {
        accAccountActivityDao.save(createAccAccountActivity(1L, LocalDate.of(2022, 1, 31)));
        accAccountActivityDao.save(createAccAccountActivity(1L, LocalDate.of(2021, 12, 31)));
        accAccountActivityDao.save(createAccAccountActivity(1L, LocalDate.of(2022, 1, 1)));
        accAccountActivityDao.save(createAccAccountActivity(1L, LocalDate.of(2022, 1, 15)));
        accAccountActivityDao.save(createAccAccountActivity(1L, LocalDate.of(2022, 2, 1)));
        accAccountActivityDao.save(createAccAccountActivity(2L, LocalDate.of(2022, 1, 10)));
    }

    @Test
    void shouldFindActivitiesOfAccount_InInclusiveDateInterval_OrderedByTransactionDate() {

        List<AccAccountActivity> result = accAccountActivityDao
                .findAllByAccountIdAndTransactionDateBetweenOrderByTransactionDateAsc(
                        1L, LocalDate.of(2022, 1, 1), LocalDate.of(2022, 1, 31));

        List<LocalDate> transactionDates = result.stream()
                .map(AccAccountActivity::getTransactionDate)
                .collect(Collectors.toList());

        assertEquals(List.of(LocalDate.of(2022, 1, 1), LocalDate.of(2022, 1, 15), LocalDate.of(2022, 1, 31)), transactionDates);
        assertTrue(result.stream().allMatch(activity -> activity.getAccountId().equals(1L)));
    }

    @Test
    void shouldFindNoActivities_WhenNoneInDateInterval() {

        List<AccAccountActivity> result = accAccountActivityDao
                .findAllByAccountIdAndTransactionDateBetweenOrderByTransactionDateAsc(
                        2L, LocalDate.of(2022, 2, 1), LocalDate.of(2022, 2, 28));

        assertTrue(result.isEmpty());
    }

    private AccAccountActivity createAccAccountActivity(Long accountId, LocalDate transactionDate) {

        AccAccountActivity accAccountActivity = new AccAccountActivity();
        accAccountActivity.setAccountId(accountId);
        accAccountActivity.setAmount(BigDecimal.valueOf(100));
        accAccountActivity.setTransactionDate(transactionDate);
        accAccountActivity.setCurrentBalance(BigDecimal.valueOf(500));
        accAccountActivity.setAccountActivityType(AccAccountActivityType.DEPOSIT);

        return accAccountActivity;
    }
}
