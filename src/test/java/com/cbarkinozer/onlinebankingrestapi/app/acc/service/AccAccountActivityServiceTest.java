package com.cbarkinozer.onlinebankingrestapi.app.acc.service;

import com.cbarkinozer.onlinebankingrestapi.app.acc.dto.AccAccountActivityDto;
import com.cbarkinozer.onlinebankingrestapi.app.acc.dto.AccMoneyActivityRequestDto;
import com.cbarkinozer.onlinebankingrestapi.app.acc.entity.AccAccountActivity;
import com.cbarkinozer.onlinebankingrestapi.app.acc.enums.AccAccountActivityType;
import com.cbarkinozer.onlinebankingrestapi.app.acc.enums.AccErrorMessage;
import com.cbarkinozer.onlinebankingrestapi.app.acc.service.entityservice.AccAccountActivityEntityService;
import com.cbarkinozer.onlinebankingrestapi.app.acc.service.entityservice.AccAccountEntityService;
import com.cbarkinozer.onlinebankingrestapi.app.cus.service.entityservice.CusCustomerEntityService;
import com.cbarkinozer.onlinebankingrestapi.app.gen.enums.GenErrorMessage;
import com.cbarkinozer.onlinebankingrestapi.app.gen.exceptions.GenBusinessException;
import com.cbarkinozer.onlinebankingrestapi.app.gen.exceptions.IllegalFieldException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccAccountActivityServiceTest {

    @Mock
    private AccAccountEntityService accAccountEntityService;

    @Mock
    private AccAccountActivityEntityService accAccountActivityEntityService;

    @Mock
    private AccAccountValidationService accAccountValidationService;

    @InjectMocks
    private AccAccountActivityService accAccountActivityService;

    @Test
    void shouldWithdraw() {

        AccMoneyActivityRequestDto accMoneyActivityRequestDto = mock(AccMoneyActivityRequestDto.class);
        AccAccountActivityDto accAccountActivityDto = mock(AccAccountActivityDto.class);

        when(accAccountActivityDto.getCurrentBalance()).thenReturn(BigDecimal.valueOf(200));
        when(accMoneyActivityRequestDto.getAmount()).thenReturn(BigDecimal.valueOf(100));

        AccAccountActivityDto result = accAccountActivityService.withdraw(accMoneyActivityRequestDto);

        assertEquals(BigDecimal.valueOf(100),result.getCurrentBalance());
    }

    @Test
    void shouldNotWithdraw_WhenMoneyActivityRequestDto_IsNull(){

        GenBusinessException genBusinessException = new GenBusinessException(GenErrorMessage.PARAMETER_CANNOT_BE_NULL);

        doThrow(GenBusinessException.class).when(accAccountValidationService).controlIsMoneyActivityRequestDtoNotNull(null);

        GenBusinessException result = assertThrows(GenBusinessException.class,
                () -> accAccountActivityService.withdraw(null));

        assertEquals(genBusinessException, result);
        assertEquals(genBusinessException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(genBusinessException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertNotNull(result);
    }

    @Test
    void shouldNotWithdraw_WhenAccountId_DoesNotExist(){

        IllegalFieldException illegalFieldException = new IllegalFieldException(AccErrorMessage.ACCOUNT_NOT_FOUND);

        doThrow(IllegalFieldException.class).when(accAccountValidationService).controlIsAccountIdExist(0L);

        IllegalFieldException result = assertThrows(IllegalFieldException.class,
                () -> accAccountActivityService.withdraw(null));

        assertEquals(illegalFieldException, result);
        assertEquals(illegalFieldException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(illegalFieldException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertNotNull(result);
    }

    @Test
    void shouldNotWithdraw_WhenAmount_IsPositive(){

        IllegalFieldException illegalFieldException = new IllegalFieldException(AccErrorMessage.ACCOUNT_NOT_FOUND);

        doThrow(IllegalFieldException.class).when(accAccountValidationService).controlIsAmountPositive(BigDecimal.valueOf(-1));

        IllegalFieldException result = assertThrows(IllegalFieldException.class,
                () -> accAccountActivityService.withdraw(null));

        assertEquals(illegalFieldException, result);
        assertEquals(illegalFieldException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(illegalFieldException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertNotNull(result);

    }

    @Test
    void shouldDeposit() {

        AccMoneyActivityRequestDto accMoneyActivityRequestDto = mock(AccMoneyActivityRequestDto.class);
        AccAccountActivityDto accAccountActivityDto = mock(AccAccountActivityDto.class);

        when(accAccountActivityDto.getCurrentBalance()).thenReturn(BigDecimal.valueOf(100));
        when(accMoneyActivityRequestDto.getAmount()).thenReturn(BigDecimal.valueOf(100));

        AccAccountActivityDto result = accAccountActivityService.deposit(accMoneyActivityRequestDto);

        assertEquals(BigDecimal.valueOf(200),result.getCurrentBalance());
    }

    @Test
    void shouldNotDeposit_WhenMoneyActivityRequestDto_IsNull(){

        GenBusinessException genBusinessException = new GenBusinessException(GenErrorMessage.PARAMETER_CANNOT_BE_NULL);

        doThrow(GenBusinessException.class).when(accAccountValidationService).controlIsMoneyActivityRequestDtoNotNull(null);

        GenBusinessException result = assertThrows(GenBusinessException.class,
                () -> accAccountActivityService.deposit(null));

        assertEquals(genBusinessException, result);
        assertEquals(genBusinessException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(genBusinessException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertNotNull(result);
    }

    @Test
    void shouldNotDeposit_WhenAccountId_DoesNotExist(){

        AccMoneyActivityRequestDto accMoneyActivityRequestDto = mock(AccMoneyActivityRequestDto.class);

        IllegalFieldException illegalFieldException = new IllegalFieldException(AccErrorMessage.ACCOUNT_NOT_FOUND);

        when(accMoneyActivityRequestDto.getAccountId()).thenReturn(0L);

        doThrow(IllegalFieldException.class).when(accAccountValidationService).controlIsAccountIdExist(0L);

        IllegalFieldException result = assertThrows(IllegalFieldException.class,
                () -> accAccountActivityService.deposit(accMoneyActivityRequestDto));

        assertEquals(illegalFieldException, result);
        assertEquals(illegalFieldException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(illegalFieldException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertNotNull(result);
    }

    @Test
    void shouldNotDeposit_WhenAmount_IsNotPositive(){


        AccMoneyActivityRequestDto accMoneyActivityRequestDto = mock(AccMoneyActivityRequestDto.class);

        IllegalFieldException illegalFieldException = new IllegalFieldException(AccErrorMessage.ACCOUNT_NOT_FOUND);

        when(accMoneyActivityRequestDto.getAmount()).thenReturn(BigDecimal.valueOf(-1));

        doThrow(IllegalFieldException.class).when(accAccountValidationService).controlIsAmountPositive(BigDecimal.valueOf(-1));

        IllegalFieldException result = assertThrows(IllegalFieldException.class,
                () -> accAccountActivityService.deposit(accMoneyActivityRequestDto));

        assertEquals(illegalFieldException, result);
        assertEquals(illegalFieldException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(illegalFieldException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertNotNull(result);

    }

    @Test
    void shouldGetAccountStatement() {

        LocalDate startDate = LocalDate.of(2022, 1, 1);
        LocalDate endDate = LocalDate.of(2022, 1, 31);

        List<AccAccountActivity> accAccountActivityList = new ArrayList<>();
        accAccountActivityList.add(createDummyAccAccountActivity(LocalDate.of(2022, 1, 5), AccAccountActivityType.DEPOSIT));
        accAccountActivityList.add(createDummyAccAccountActivity(LocalDate.of(2022, 1, 20), AccAccountActivityType.WITHDRAW));

        when(accAccountActivityEntityService.findAccountActivitiesByAccountIdAndDateInterval(1L, startDate, endDate))
                .thenReturn(accAccountActivityList);

        List<AccAccountActivityDto> result = accAccountActivityService.getAccountStatement(1L, startDate, endDate);

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getAccountId());
        assertEquals(LocalDate.of(2022, 1, 5), result.get(0).getTransactionDate());
        assertEquals(AccAccountActivityType.DEPOSIT, result.get(0).getAccountActivityType());
        assertEquals(BigDecimal.valueOf(100), result.get(0).getAmount());
        assertEquals(LocalDate.of(2022, 1, 20), result.get(1).getTransactionDate());
        assertEquals(AccAccountActivityType.WITHDRAW, result.get(1).getAccountActivityType());

        verify(accAccountValidationService).controlIsAccountIdExist(1L);
        verify(accAccountValidationService).controlIsDateIntervalValid(startDate, endDate);
    }

    @Test
    void shouldGetEmptyAccountStatement_WhenNoActivity_InDateInterval() {

        LocalDate startDate = LocalDate.of(2022, 1, 1);
        LocalDate endDate = LocalDate.of(2022, 1, 31);

        when(accAccountActivityEntityService.findAccountActivitiesByAccountIdAndDateInterval(1L, startDate, endDate))
                .thenReturn(new ArrayList<>());

        List<AccAccountActivityDto> result = accAccountActivityService.getAccountStatement(1L, startDate, endDate);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldNotGetAccountStatement_WhenAccountId_DoesNotExist() {

        LocalDate startDate = LocalDate.of(2022, 1, 1);
        LocalDate endDate = LocalDate.of(2022, 1, 31);

        IllegalFieldException illegalFieldException = new IllegalFieldException(AccErrorMessage.ACCOUNT_NOT_FOUND);

        doThrow(illegalFieldException).when(accAccountValidationService).controlIsAccountIdExist(0L);

        IllegalFieldException result = assertThrows(IllegalFieldException.class,
                () -> accAccountActivityService.getAccountStatement(0L, startDate, endDate));

        assertEquals(illegalFieldException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(illegalFieldException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        verifyNoInteractions(accAccountActivityEntityService);
    }

    @Test
    void shouldNotGetAccountStatement_WhenStartDate_IsAfterEndDate() {

        LocalDate startDate = LocalDate.of(2022, 2, 1);
        LocalDate endDate = LocalDate.of(2022, 1, 1);

        IllegalFieldException illegalFieldException = new IllegalFieldException(AccErrorMessage.INVALID_DATE_INTERVAL);

        doThrow(illegalFieldException).when(accAccountValidationService).controlIsDateIntervalValid(startDate, endDate);

        IllegalFieldException result = assertThrows(IllegalFieldException.class,
                () -> accAccountActivityService.getAccountStatement(1L, startDate, endDate));

        assertEquals(AccErrorMessage.INVALID_DATE_INTERVAL.getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(AccErrorMessage.INVALID_DATE_INTERVAL.getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        verifyNoInteractions(accAccountActivityEntityService);
    }

    @Test
    void shouldThrowInvalidDateInterval_WhenValidationFails() {

        AccAccountValidationService validationService =
                new AccAccountValidationService(accAccountEntityService, mock(CusCustomerEntityService.class));

        LocalDate date = LocalDate.of(2022, 1, 1);

        assertDoesNotThrow(() -> validationService.controlIsDateIntervalValid(date, date));
        assertDoesNotThrow(() -> validationService.controlIsDateIntervalValid(date, date.plusDays(1)));

        IllegalFieldException afterEnd = assertThrows(IllegalFieldException.class,
                () -> validationService.controlIsDateIntervalValid(date.plusDays(1), date));
        assertEquals(AccErrorMessage.INVALID_DATE_INTERVAL, afterEnd.getBaseErrorMessage());

        assertThrows(IllegalFieldException.class, () -> validationService.controlIsDateIntervalValid(null, date));
        assertThrows(IllegalFieldException.class, () -> validationService.controlIsDateIntervalValid(date, null));
    }

    private AccAccountActivity createDummyAccAccountActivity(LocalDate transactionDate, AccAccountActivityType activityType) {

        AccAccountActivity accAccountActivity = new AccAccountActivity();
        accAccountActivity.setAccountId(1L);
        accAccountActivity.setAmount(BigDecimal.valueOf(100));
        accAccountActivity.setTransactionDate(transactionDate);
        accAccountActivity.setCurrentBalance(BigDecimal.valueOf(500));
        accAccountActivity.setAccountActivityType(activityType);

        return accAccountActivity;
    }
}
