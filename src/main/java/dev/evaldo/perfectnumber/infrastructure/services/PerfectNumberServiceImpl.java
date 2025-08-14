package dev.evaldo.perfectnumber.infrastructure.services;

import dev.evaldo.perfectnumber.domain.PerfectNumberService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PerfectNumberServiceImpl implements PerfectNumberService {

    @Override
    public boolean isPerfect(int n) {
        if (n < 2) return false;
        int sum = 1;
        int sqrt = (int)Math.sqrt(n);
        for (int i = 2; i <= sqrt; i++) {
            if (n % i == 0) {
                sum += i;
                int d = n / i;
                if (d != i) sum += d;
            }
        }
        return sum == n;
    }

    @Override
    public List<Integer> findPerfectInRange(int start, int end) {
        int a = Math.min(start, end);
        int b = Math.max(start, end);
        List<Integer> result = new ArrayList<>();
        for (int n = a; n <= b; n++) {
            if (isPerfect(n)) result.add(n);
        }
        return result;
    }

    @Override
    public List<Integer> primesOf(List<Integer> numbers) {
        List<Integer> primes = new ArrayList<>();
        for (Integer n : numbers) {
            if (n != null && isPrime(n)) primes.add(n);
        }
        return primes;
    }

    private boolean isPrime(int n) {
        if (n < 2) return false;
        if (n % 2 == 0) return n == 2;
        int sqrt = (int)Math.sqrt(n);
        for (int i = 3; i <= sqrt; i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }
}
