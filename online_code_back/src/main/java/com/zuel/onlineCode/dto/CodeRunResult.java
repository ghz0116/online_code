package com.zuel.onlineCode.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CodeRunResult {
    public int exitCode;
    private String caseName;
    private String runResult;
    private String errorInfo;
    private long durationInMillis;
    private boolean right;
}
