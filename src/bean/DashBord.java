package bean;

import java.util.List;

public class DashBord implements java.io.Serializable{
	private int entYear;
	private String classNum;
	private Subject subject;
	private int testNo;
	private List<RangeCount> distribution;
	private double avg;
	private int max;
	private int min;

	// 以下ゲッター
	public int getEntYear(){
		return this.entYear;
	}

	public String getClassNum(){
		return this.classNum;
	}

	public Subject getSubject(){
		return this.subject;
	}

	public int getTestNo(){
		return this.testNo;
	}

	public List<RangeCount> getDistribution(){
		return distribution;
	}

	public double getAvg(){
		return this.avg;
	}

	public int getMax(){
		return this.max;
	}

	public int getMin(){
		return this.min;
	}

	// 以下セッター
	public void setEntYear(int entYear){
		this.entYear = entYear;
	}

	public void setClassNum(String classNum){
		this.classNum = classNum;
	}

	public void setSubject(Subject subject){
		this.subject = subject;
	}

	public void setTestNo(int no){
		this.testNo = no;
	}

	public void setDistribution(List<RangeCount> distribution){
		this.distribution = distribution;
	}

	public void setAvg(double avg){
		this.avg = avg;
	}

	public void setMax(int max){
		this.max = max;
	}

	public void setMin(int min){
		this.min = min;
	}
}
