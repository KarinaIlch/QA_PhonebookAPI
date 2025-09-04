package com.phonebook.tests;

import com.phonebook.core.TestBase;
import com.phonebook.dto.ContactDto;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class AddContactTest extends TestBase {

    ContactDto contactDto = ContactDto // викликаємо builder для зручного створення ContactDto
            .builder()
            .name("Milla")
            .lastName("Wattson")
            .email("milla123@gm.de")
            .phone("123456789012")
            .address("Berlin")
            .description("goalkeeper")
            .build(); // будуємо готовий обʼєкт

    @Test
    // позитивний тест: додаємо валідний контакт
    public void addContactSuccessTest() {

//        String message =
        given() // початок побудови HTTP-запиту
                .contentType(ContentType.JSON) // вказуємо тип даних JSON
                .body(contactDto)
                .header(AUTH, TOKEN)
                .when()    // коли (виконується запит)
                .post("contacts")  // POST-запит на endpoint /contacts
                .then() // тоді (перевіряємо результат)
                .assertThat().statusCode(200) // очікуємо статус 200
                .assertThat().body("message", containsString("Contact was added!"));
//                                // перевіряємо, що у відповіді поле message містить текст "Contact was added!"
//                .extract().path("message");
//        System.out.println(message);
    }

    @Test
    public void addContactWithoutNameTest() {
//        ErrorDto errorDto =
                given()
                        .header(AUTH, TOKEN)
                        .contentType(ContentType.JSON)
                        .body(ContactDto.builder() // передаємо тіло запиту — наш contactDto
                                // спеціально пропускаємо .name()
                                .lastName("Wattson")
                                .email("milla123@gm.de")
                                .phone("123456789012")
                                .address("Berlin")
                                .description("goalkeeper")
                                .build())
                        .when()
                        .post("contacts")
                        .then()
                        .assertThat().statusCode(400) // очікуємо помилку 400, бо тест негативний
                        // перевіряємо, що у відповіді поле message містить текст "Contact was added!"
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
