package com.sqs.sequence.utils;

public class Pair<V, T> {

	private V first;
	private T second;

	public Pair(V first,T second){
		this.first=first;
		this.second = second;
	}

	public Pair() {
		this(null, null);
	}

	public V getFirst() {
		return first;
	}

	public void setFirst(V first) {
		this.first = first;
	}

	public T getSecond() {
		return second;
	}

	public void setSecond(T second) {
		this.second = second;
	}

}
