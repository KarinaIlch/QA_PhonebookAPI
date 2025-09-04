package com.phonebook.core;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;

public class TestBase {

    public static String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoibWlsYTEyM0BnbS5jb20iLCJpc3MiOiJSZWd1bGFpdCIsImV4cCI6MTc1NzU3MjM4NCwiaWF0IjoxNzU2OTcyMzg0fQ.1BGmLj_k6REKTXs9rHRPBADdbMF_K_Znh9ZF6JilfHY";
    public static String AUTH = "Authorization";
    @BeforeMethod
    public void init(){
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";
    }
}
