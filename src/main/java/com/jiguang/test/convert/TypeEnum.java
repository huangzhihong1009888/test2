package com.jiguang.test.convert;

import lombok.Data;
import lombok.Getter;

@Getter
public enum TypeEnum {
	PACK(1),GOOD(2);
	private Integer  value;
	TypeEnum(Integer value){
		this.value=value;
	}

}
