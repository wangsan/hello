package top.sailingsan.dl4j.reid.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CosineResp {
    private String code = "0";
    private String message = "success";
    private float data;

}
