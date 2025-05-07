package bean;

import java.io.Serializable;

public class RangeCount implements Serializable {
	private int lower; // 下限値
	private int upper; // 上限値
	private int count; // 人数

	// 以下ゲッター
	public int getLower(){
		return this.lower;
	}

	public int getUpper(){
		return this.upper;
	}

	public int getCount(){
		return this.count;
	}

	// 以下セッター
	public void setLower(int lower){
		this.lower = lower;
	}

	public void setUpper(int upper){
		this.upper = upper;
	}

	public void setCount(int count){
		this.count = count;
	}
}
