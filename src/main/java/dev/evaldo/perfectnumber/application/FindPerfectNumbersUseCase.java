package dev.evaldo.perfectnumber.application;

import dev.evaldo.perfectnumber.domain.PerfectNumberService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class FindPerfectNumbersUseCase {
    private final PerfectNumberService perfectNumberService;

    public List<Integer> execute(int start, int end) {
        return perfectNumberService.findPerfectInRange(start, end);
    }
}
