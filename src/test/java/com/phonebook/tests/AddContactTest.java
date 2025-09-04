package com.phonebook.tests;

import com.phonebook.core.TestBase;
import com.phonebook.dto.ContactDto;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class AddContactTest extends TestBase {

    ContactDto contactDto = ContactDto
            .builder()
            .name("Milla")
            .lastName("Wattson")
            .email("milla123@gm.de")
            .phone("123456789012")
            .address("Berlin")
            .description("goalkeeper")
            .build();

    @Test
    public void addContactSuccessTest() {
//        String message =
        given()
                .contentType(ContentType.JSON)
                .body(contactDto)
                .header(AUTH, TOKEN)
                .when() // когда
                .post("contacts") // используем метод
                .then()// тогда
                .assertThat().statusCode(200)
                .assertThat().body("message", containsString("Contact was added!"));
//                .extract().path("message");
//        System.out.println(message);
    }

    @Test
    public void addContactWithoutNameTest() {
//        ErrorDto errorDto =
                given()
                        .header(AUTH, TOKEN)
                        .contentType(ContentType.JSON)
                        .body(ContactDto.builder()
                                // имя мы убрали;
                                .lastName("Wattson")
                                .email("milla123@gm.de")
                                .phone("123456789012")
                                .address("Berlin")
                                .description("goalkeeper")
                                .build())
                        .when()
                        .post("contacts")
                        .then()
                        .assertThat().statusCode(400)
                        .assertThat().body("message.name",containsString("must not be blank"));
    //                        .extract().response().as(ErrorDto.class);
//        System.out.println(errorDto.getError() + "*****" + errorDto.getMessage());
    }
    @Test
    public void addContactWithInvalidPhoneTest(){
//ErrorDto errorDto =
        given()
                .header(AUTH,TOKEN)
                .contentType(ContentType.JSON)
                .body(ContactDto.builder()
                        .name("Milla")
                        .lastName("Wattson")
                        .email("milla123@gm.de")
                        .phone("1234")
                        .address("Berlin")
                        .description("goalkeeper")
                        .build())
                .when()
                .post("contacts")
                .then()
                .assertThat().statusCode(400)
                .assertThat().body("message.phone",equalTo("Phone number must contain only digits! And length min 10, max 15!"));
//                .extract().response().as(ErrorDto.class);
//        System.out.println(errorDto.getMessage());
    }
}
//{phone=Phone number must contain only digits! And length min 10, max 15!}