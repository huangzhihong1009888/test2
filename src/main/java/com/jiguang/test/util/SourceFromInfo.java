package com.jiguang.test.util;

import lombok.Data;

@Data
public class SourceFromInfo {
    private String alias;
    private boolean isNeedAddCondition;
    private String tableName;
    private boolean subQuery;
}
