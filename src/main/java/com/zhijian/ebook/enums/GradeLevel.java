package com.zhijian.ebook.enums;

/**
 * 历史消息类型 ,用以从历史消息表中获取相应类型的历史消息
 * @author Beyond
 *
 */
public enum GradeLevel {
	
	ONE_LEVEL("one"), TWO_LEVEL("two"), THREE_LEVEL("three"), FOUR_LEVEL("four");
	
	private String level;
	
	private GradeLevel(String level){
		this.level = level;
	}

	public String getLevel() {
		return level;
	}

//	private void setLevel(String level) {
//		this.level = level;
//	}
	
	public static void main(String[] args) {
		;
	}
	
}
