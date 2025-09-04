package com.phonebook.tests;

import com.phonebook.core.TestBase;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.phonebook.data.ObjectsData.contactDto;
import static io.restassured.RestAssured.given;

public class DeleteContactByIdTest extends TestBase {

    String id;

    @BeforeMethod
    public void preRequest() {

        String message = given()
                .contentType(ContentType.JSON)
                .body(contactDto)
                .header(AUTH,TOKEN)
                .when()
                .post("contacts")
                .then()
                .assertThat().statusCode(200)
                .extract().path("message");

        String[] split = message.split(": ");
        id = split[1];

    }

    @Test
    public void deleteContactByIdSuccessTest(){
        String message =
                given()
                .header(AUTH,TOKEN)
                .when()
                .delete("contacts/" + id)
                .then()
                .assertThat().statusCode(200)
                .extract().path("message");
        System.out.println(message);

    }
}
