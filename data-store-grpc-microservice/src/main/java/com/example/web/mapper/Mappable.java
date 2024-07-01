package com.example.web.mapper;

public interface Mappable<E, D> {

    D toDto(E e);

}
