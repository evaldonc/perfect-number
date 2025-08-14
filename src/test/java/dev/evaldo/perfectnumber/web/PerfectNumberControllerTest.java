package dev.evaldo.perfectnumber.web;

import dev.evaldo.perfectnumber.domain.PerfectNumberService;
import dev.evaldo.perfectnumber.web.dto.PerfectNumbersResponse;
import dev.evaldo.perfectnumber.web.dto.RangeRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class PerfectNumberControllerTest {

    private PerfectNumberService perfectNumberService;
    private PerfectNumberController controller;
    private HttpServletRequest httpServletRequest;

    @BeforeEach
    void setUp() {
        perfectNumberService = mock(PerfectNumberService.class);
        controller = new PerfectNumberController(perfectNumberService);
        httpServletRequest = mock(HttpServletRequest.class);
    }

    @Test
    void testFind_ReturnsOk() {
        RangeRequest request = new RangeRequest(new Integer[]{1, 30});
        PerfectNumbersResponse response = new PerfectNumbersResponse(List.of(6, 28), 2);
        when(perfectNumberService.processRange(eq(request), any())).thenReturn(Optional.of(response));

        ResponseEntity<PerfectNumbersResponse> result = controller.find(request, httpServletRequest);

        assertEquals(200, result.getStatusCode().value());
        assertEquals(response, result.getBody());
    }

    @Test
    void testFind_ReturnsBadRequest() {
        RangeRequest request = new RangeRequest(new Integer[]{1});
        when(perfectNumberService.processRange(eq(request), any())).thenReturn(Optional.empty());

        ResponseEntity<PerfectNumbersResponse> result = controller.find(request, httpServletRequest);

        assertEquals(400, result.getStatusCode().value());
        assertNull(result.getBody());
    }
}