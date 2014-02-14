package com.example.health_connect;

public class Picture {
	private String picName;
	private String picEmail;
	private String picAddress;
	private String picLic;
	private String picDegree;
	private String picSchool;
	private String picSpec;
	private String picStart;
	private int picDrawable;
	private String picId;
	
	public Picture(String picName,String picEmail, int picDrawable,String picAddress,String picLic,
			String picDegree,String picSchool,String picSpec,String picStart,String picId)
	{ 
		 this.picName=picName;
	     this.picEmail=picEmail;
	     this.picAddress=picAddress;
	     this.picLic=picLic;
	     this.picDegree=picDegree;
	     this.picSchool=picSchool;
	     this.picSpec=picSpec;
	     this.picStart=picStart;
	     this.picDrawable=picDrawable;
	     this.picId = picId;
		
	}
	
	public String getPicName()
	{
		return this.picName;
	}
    public String getPicType()
    {
    	return this.picEmail;
    }
    public int getPicSource()
    {
    	return this.picDrawable;
    }
    public String getPicAdd()
    {
    	return this.picAddress;
    }
    public String getPicLic()
    {
    	return this.picLic;
    }
    public String getPicDegree()
    {
    	return this.picDegree;
    }
    public String getPicSchool()
    {
    	return this.picSchool;
    }
    public String getPicSpec()
    {
    	return this.picSpec;
    }
    public String getPicStart()
    {
    	return this.picStart;
    }

	public String getPicId() {
		return picId;
	}

	
}
