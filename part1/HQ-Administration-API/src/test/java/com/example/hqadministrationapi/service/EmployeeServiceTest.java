package com.example.hqadministrationapi.service;

import com.example.hqadministrationapi.domain.ContractType;
import com.example.hqadministrationapi.domain.Employee;
import com.example.hqadministrationapi.domain.Rank;
import com.example.hqadministrationapi.exception.NotEligibleException;
import com.example.hqadministrationapi.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    EmployeeRepository repo;

    @InjectMocks
    EmployeeService service;

    private Employee sample(Long id, Rank rank, LocalDate startDate, BigDecimal salary) {
        Employee e = new Employee();
        e.setId(id);
        e.setName("Eryk");
        e.setEmail("em558@gmail.com");
        e.setContractType(ContractType.FULL_TIME);
        e.setStartDate(startDate);
        e.setSalary(salary);
        e.setRank(rank);
        return e;
    }

    @Test
    void promoteThrowsWhenEmployeeDoesNotExist() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.promote(99L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");
    }


    @Test
    void promoteThrowsWhenEmployeeWorkedLessThanSixMonths() {
        Employee e = sample(1L, Rank.JUNIOR,
                LocalDate.now().minusMonths(3),
                new BigDecimal("50000"));
        when(repo.findById(1L)).thenReturn(Optional.of(e));

        assertThatThrownBy(() -> service.promote(1L))
                .isInstanceOf(NotEligibleException.class)
                .hasMessageContaining("less than 6 months");
    }


    @Test
    void promoteThrowsWhenAlreadySenior() {
        Employee e = sample(1L, Rank.SENIOR,
                LocalDate.now().minusYears(3),
                new BigDecimal("90000"));
        when(repo.findById(1L)).thenReturn(Optional.of(e));

        assertThatThrownBy(() -> service.promote(1L))
                .isInstanceOf(NotEligibleException.class)
                .hasMessageContaining("Senior");
    }

    @Test
    void promoteJuniorToIntermediateWithTenPercentRaise() {
        Employee e = sample(1L, Rank.JUNIOR,
                LocalDate.now().minusYears(1),
                new BigDecimal("50000"));
        when(repo.findById(1L)).thenReturn(Optional.of(e));

        Employee result = service.promote(1L);

        assertThat(result.getRank()).isEqualTo(Rank.INTERMEDIATE);
        assertThat(result.getSalary()).isEqualByComparingTo(new BigDecimal("55000.00"));
    }

    @Test
    void promoteIntermediateToSeniorWithTenPercentRaise() {
        Employee e = sample(1L, Rank.INTERMEDIATE,
                LocalDate.now().minusYears(2),
                new BigDecimal("70000"));
        when(repo.findById(1L)).thenReturn(Optional.of(e));

        Employee result = service.promote(1L);

        assertThat(result.getRank()).isEqualTo(Rank.SENIOR);
        assertThat(result.getSalary()).isEqualByComparingTo(new BigDecimal("77000.00"));
    }

    @Test
    void getThrowsWhenEmployeeDoesNotExist() {
        when(repo.findById(42L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.get(42L))
                .isInstanceOf(EntityNotFoundException.class);
    }
}