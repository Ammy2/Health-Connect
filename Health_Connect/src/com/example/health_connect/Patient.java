package com.example.health_connect;

public class Patient {
	private String picName;
	private String picEmail;
	private String picAddress;
	private String picNation;
	private String picDob;
	private String picWt;
	private String picHt;
	private String picBg;
	private String picDA;
	private int picDrawable;
	private String picId;
	
	public Patient(String picName,String picEmail, int picDrawable,String picAddress,String picNation,
			String picDob,String picWt,String picHt,String picBg,String picDA, String picId)
	{ 
		 this.picName=picName;
	     this.picEmail=picEmail;
	     this.picAddress=picAddress;
	     this.picNation=picNation;
	     this.picDob=picDob;
	     this.picWt=picWt;
	     this.picHt=picHt;
	     this.picBg=picBg;
	     this.picDA=picDA;
	     this.picDrawable=picDrawable;
	     this.picId = picId;
		
	}
	
	public String getPicId()
	{
		return this.picId;
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
    public String getPicNation()
    {
    	return this.picNation;
    }
    public String getPicDob()
    {
    	return this.picDob;
    }
    public String getPicWt()
    {
    	return this.picWt;
    }
    public String getPicHt()
    {
    	return this.picHt;
    }
    public String getPicBg()
    {
    	return this.picBg;
    }
    public String getPicDA()
    {
    	return this.picDA;
    }
}
