package com.example.apiprac;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@EqualsAndHashCode
@Getter
public class NewsArticlePrediction  implements Serializable {

    private String prediction1;
    private String prediction1_percent;
    private String prediction2;
    private String prediction2_percent;
    private String prediction3;
    private String prediction3_percent;

    public NewsArticlePrediction(String prediction1, String prediction1_percent, String prediction2, String prediction2_percent, String prediction3, String prediction3_percent) {
        this.prediction1 = prediction1;
        this.prediction1_percent = prediction1_percent;
        this.prediction2 = prediction2;
        this.prediction2_percent = prediction2_percent;
        this.prediction3 = prediction3;
        this.prediction3_percent = prediction3_percent;
    }

// boilerplate getters, constructors, equals, and hashcode omitted
}