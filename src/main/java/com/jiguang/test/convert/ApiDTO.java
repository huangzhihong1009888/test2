package com.jiguang.test.convert;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName ApiDO
 * @Description TODO
 * @Author user
 * @Date 2020/7/15 17:34
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiDTO {
    private Integer id;
    private String num;
    private Integer type;
}
