package com.example.trendingso.util;

public class Resource<T>{
    public enum State{
        SUCCESS,
        FAILURE,
        LOADING
    }
    public T data;
    public Throwable t;
    public State state;
    public Resource(T data,Throwable t,State state){
        this.data = data;
        this.t = t;
        this.state = state;
    }
    public static <T> Resource<T> success(T data){
        return new Resource<>(data,null,State.SUCCESS);
    }
    public static <T> Resource<T> loading(T data){
        return new Resource<>(data,null,State.LOADING);
    }
    public static <T> Resource<T> failure(T data,Throwable th){
        return new Resource<>(data,th,State.FAILURE);
    }
}
