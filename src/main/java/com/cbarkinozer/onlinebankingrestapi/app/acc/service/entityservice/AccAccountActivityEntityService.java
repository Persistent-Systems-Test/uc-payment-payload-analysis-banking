package com.cbarkinozer.onlinebankingrestapi.app.acc.service.entityservice;

import com.cbarkinozer.onlinebankingrestapi.app.acc.dao.AccAccountActivityDao;
import com.cbarkinozer.onlinebankingrestapi.app.acc.entity.AccAccountActivity;
import com.cbarkinozer.onlinebankingrestapi.app.gen.service.BaseEntityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class AccAccountActivityEntityService extends BaseEntityService<AccAccountActivity, AccAccountActivityDao> {
    public AccAccountActivityEntityService(AccAccountActivityDao accAccountActivityDao){
        super(accAccountActivityDao);
    }

    public List<AccAccountActivity> findAccountActivitiesByAccountIdAndDateInterval(Long accountId, LocalDate startDate, LocalDate endDate) {

        List<AccAccountActivity> accAccountActivityList = getDao()
                .findAllByAccountIdAndTransactionDateBetweenOrderByTransactionDateAsc(accountId, startDate, endDate);

        return accAccountActivityList;
    }
}
