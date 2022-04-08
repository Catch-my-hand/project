package com.catch_my_hand.backend.home_sale.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostPetReq {
    private int useridx;
    private int categoryidx;
    private String title;
    private List<String> imgUrlList;
    private int price;
    private String content;
}
