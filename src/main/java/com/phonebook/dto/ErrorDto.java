package com.phonebook.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder // додає шаблон Builder для зручного створення об'єкта
public class ErrorDto {

    private int status; // код статусу
    private String error; // короткий опис помилки
    private Object message; // текстове повідомлення від сервера
}
