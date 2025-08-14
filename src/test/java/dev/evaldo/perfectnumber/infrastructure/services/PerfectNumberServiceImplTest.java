package dev.evaldo.perfectnumber.infrastructure.services;

import dev.evaldo.perfectnumber.infrastructure.persistence.AuditLogEntity;
import dev.evaldo.perfectnumber.infrastructure.persistence.AuditLogRepository;
import dev.evaldo.perfectnumber.web.dto.PerfectNumbersResponse;
import dev.evaldo.perfectnumber.web.dto.RangeRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PerfectNumberServiceImplTest {

    private AuditLogRepository auditLogRepository;
    private PerfectNumberServiceImpl service;
    private HttpServletRequest httpServletRequest;

    @BeforeEach
    void setUp() {
        auditLogRepository = mock(AuditLogRepository.class);
        service = new PerfectNumberServiceImpl(auditLogRepository);
        httpServletRequest = mock(HttpServletRequest.class);
        when(httpServletRequest.getRemoteAddr()).thenReturn("127.0.0.1");
        when(httpServletRequest.getRequestURI()).thenReturn("/test");
    }

    @Test
    void testProcessRangeWithValidRange() {
        RangeRequest request = new RangeRequest(new Integer[]{1, 100});
        Optional<PerfectNumbersResponse> response = service.processRange(request, httpServletRequest);

        assertTrue(response.isPresent());
        PerfectNumbersResponse res = response.get();
        assertNotNull(res.getNumbers());
        assertEquals(res.getNumbers().size(), res.getCount());

        ArgumentCaptor<AuditLogEntity> captor = ArgumentCaptor.forClass(AuditLogEntity.class);
        verify(auditLogRepository).save(captor.capture());
        assertEquals("127.0.0.1", captor.getValue().getSourceIp());
        assertEquals("/test", captor.getValue().getRequestPath());
    }

    @Test
    void testProcessRangeWithInvalidRange() {
        RangeRequest request = new RangeRequest(new Integer[]{1});
        Optional<PerfectNumbersResponse> response = service.processRange(request, httpServletRequest);
        assertTrue(response.isEmpty());
    }

    @Test
    void testIsPerfect() {
        assertTrue(service.isPerfect(6));
        assertTrue(service.isPerfect(28));
        assertFalse(service.isPerfect(10));
        assertFalse(service.isPerfect(1));
    }

    @Test
    void testPrimesOf() {
        List<Integer> input = List.of(6, 28, 7, 13, 12);
        List<Integer> primes = service.primesOf(input);
        assertTrue(primes.contains(7));
        assertTrue(primes.contains(13));
        assertFalse(primes.contains(6));
        assertFalse(primes.contains(28));
    }

    @Test
    void testFindPerfectInRange() {
        assertEquals(List.of(), service.primesOf(List.of(6, 28, 496)));
    }

    @Test
    void testGetClientIpWithXForwardedFor() {
        when(httpServletRequest.getHeader("X-Forwarded-For")).thenReturn("192.168.0.1, 10.0.0.1");
        RangeRequest request = new RangeRequest(new Integer[]{1, 10});
        service.processRange(request, httpServletRequest);
        ArgumentCaptor<AuditLogEntity> captor = ArgumentCaptor.forClass(AuditLogEntity.class);
        verify(auditLogRepository).save(captor.capture());
        assertEquals("192.168.0.1", captor.getValue().getSourceIp());
    }
}