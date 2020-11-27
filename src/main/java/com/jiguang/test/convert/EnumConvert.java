package com.jiguang.test.convert;

/**
 * @ClassName EnumConvert
 * @Description TODO
 * @Author user
 * @Date 2020/7/22 10:03
 */
class EnumConver {
	public TypeEnum toEnumUserType(int value){
		for(TypeEnum type : TypeEnum.values()){
			if(type.getValue() == value) {
				return type;
			}
		}
		return null;
	}

	public Integer toEnurType(TypeEnum type){

		return type.getValue();
	}
	public Integer toEnur2Type(TypeEnum2 type){

		return type.getValue();
	}

}
