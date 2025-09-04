package com.phonebook.tests;

import com.phonebook.core.TestBase;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.phonebook.data.ObjectsData.contactDto;
import static io.restassured.RestAssured.given;

public class DeleteContactByIdTest extends TestBase {

    String id; // створюємо змінну

    @BeforeMethod
    public void preRequest() {
        // Тут ми створюємо новий контакт і отримуємо його id, щоб було що видаляти.
        String message =
                given()
                .contentType(ContentType.JSON)
                .body(contactDto)
                .header(AUTH,TOKEN)
                .when()
                .post("contacts")
                .then()
                .assertThat().statusCode(200)
                .extract().path("message");

        String[] split = message.split(": "); // розділяємо рядок по ": "
        id = split[1]; // друга частина — це id створеного контакту
    }

    @Test
    public void deleteContactByIdSuccessTest(){ //  видалення контакту по id
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
