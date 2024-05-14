package com.zuel.onlineCode.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class QuestionQuery {
    private String searchContent;
    private Integer troubleLevel;//1:简单 2：一般 3：困难
    private List<String> relativeTags;
}
