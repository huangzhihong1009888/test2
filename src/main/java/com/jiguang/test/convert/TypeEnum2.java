package com.jiguang.test.convert;

import lombok.Getter;

/**
 * @author user
 */

@Getter
public enum TypeEnum2 {
	PACK(1),GOOD(2);
	private Integer  value;
	TypeEnum2(Integer value){
		this.value=value;
	}

}
