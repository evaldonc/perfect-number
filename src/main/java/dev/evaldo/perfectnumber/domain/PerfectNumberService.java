package dev.evaldo.perfectnumber.domain;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PerfectNumberService {
    boolean isPerfect(int n);
    List<Integer> findPerfectInRange(int start, int end);
    List<Integer> primesOf(List<Integer> numbers);
}
