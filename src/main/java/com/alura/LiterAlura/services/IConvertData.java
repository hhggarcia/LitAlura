package com.alura.LiterAlura.services;

public interface IConvertData {
    <T> T getDatos(String json, Class<T> clase);
}
