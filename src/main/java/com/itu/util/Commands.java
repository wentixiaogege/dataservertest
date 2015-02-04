package com.itu.util;

public enum Commands {
	
	    Parameter_Register_Read("Parameter Register Read", 0), GREEN("绿色", 2), BLANK("白色", 3), YELLO("黄色", 4);
	    // 成员变量
	    private String name;
	    private int id;

	    // 构造方法
	    private Commands(String name, int index) {
	        this.name = name;
	        this.id = index;
	    }

	   

	    // get set 方法
	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public int getIndex() {
	        return id;
	    }

	    public void setIndex(int index) {
	        this.id = index;
	    }
}
