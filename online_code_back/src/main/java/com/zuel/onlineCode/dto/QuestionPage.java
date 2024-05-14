package com.zuel.onlineCode.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class QuestionPage {
    private QuestionQuery questionQuery;
    private Pagination pagination;
}
