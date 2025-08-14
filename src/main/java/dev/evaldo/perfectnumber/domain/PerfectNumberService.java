package dev.evaldo.perfectnumber.domain;

import dev.evaldo.perfectnumber.web.dto.PerfectNumbersResponse;
import dev.evaldo.perfectnumber.web.dto.RangeRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface PerfectNumberService {

    boolean isPerfect(int n);

    Optional<PerfectNumbersResponse> processRange(RangeRequest request, HttpServletRequest http);

    List<Integer> primesOf(List<Integer> numbers);

}
