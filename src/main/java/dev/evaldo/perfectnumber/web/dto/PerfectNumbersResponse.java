package dev.evaldo.perfectnumber.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PerfectNumbersResponse {
    private List<Integer> numbers;
    private int count;
}
