package com.example.health_connect;

public class Report {
	
	private String picName;
	private String picType;
	private int picDrawable;
	private String picURL;
	
	public Report(String picName,String picType, int picDrawable, String picURL)
	{ 
		 this.picName=picName;
	     this.picType=picType;
	     this.picDrawable=picDrawable;
	     this.picURL=picURL;
		
	}
	
	public String getPicURL()
	{
		return this.picURL;
	}
	public String getPicName()
	{
		return this.picName;
	}
    public String getPicType()
    {
    	return this.picType;
    }
    public int getPicSource()
    {
    	return this.picDrawable;
    }

}
