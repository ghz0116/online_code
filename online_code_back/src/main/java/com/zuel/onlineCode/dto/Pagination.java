package com.zuel.onlineCode.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Pagination {
    private Integer currentPage;
    private Integer pageSize;
    private Integer total;
    private List<T> data;
}
