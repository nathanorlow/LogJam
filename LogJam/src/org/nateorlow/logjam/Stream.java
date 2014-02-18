package org.nateorlow.logjam;

import java.util.Random;

public class Stream {
private int ID;
private int sum;
private int capacityMin;
private int capacityMax;
private int capacity;
Random r = new Random();


public Stream(int newID, int newCapacityMin, int newCapacityMax){
	ID=newID;
	sum=0;
	capacityMin=newCapacityMin;
	capacityMax=newCapacityMax;
	this.resetCapacity();
}

public void addOn(int logValue){
	sum+=logValue;
}

public boolean isFull(){
	return (sum>=capacity);
}

public boolean isTooFull(){
	return (sum>capacity);
}

public void resetSelf(){
	this.resetCapacity();
	sum=0;
}

public void resetCapacity(){
	capacity=r.nextInt(capacityMax-capacityMin+1)+capacityMin;
}
	
public int getID(){
	return ID;
}

public int getSum(){
	return sum;
}
public int getCapacity(){
	return capacity;
}

}