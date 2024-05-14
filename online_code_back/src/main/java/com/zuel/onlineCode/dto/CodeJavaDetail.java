package com.zuel.onlineCode.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CodeJavaDetail {
    private String functionName;
    private List<HashMap<String,String>> parameters;
    private String returnType;
}
