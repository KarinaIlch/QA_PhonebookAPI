package com.phonebook.tests;

import com.phonebook.core.TestBase;
import com.phonebook.dto.ContactDto;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class ModifyContactTest extends TestBase {

    String id; // id - змінна для створеного контакта

    @BeforeMethod
    public void preRequest() { // створення та збереження нового контакту

        ContactDto contactDto = ContactDto // створюємо DTO (об’єкт із даними контакту),
                .builder() // дозволяє зручно по черзі вказати всі потрібні поля (name, lastName, email і т.д.), без необхідності писати довгий конструктор.
                .name("Leon")
                .lastName("Mitchel")
                .email("leon123@gm.de")
                .phone("321456789012")
                .address("Cologne")
                .description("goalkeeper")
                .build(); // завершує створення контакта та повертає готовий об’єкт типу ContactDto.

        String message = given()
                .contentType(ContentType.JSON)
                .body(contactDto)
                .header(AUTH, TOKEN)
                .when()
                .post("contacts") // post метод для створення контакту
                .then()
                .assertThat().statusCode(200)
                .extract().path("message");

        String[] split = message.split(": "); // Розбиваємо повідомлення, щоб дістати id контакту
        id = split[1];
    }

    @Test
    public void updateContactTest() {
        given() // будуємо запит
                .contentType(ContentType.JSON) // вказуємо, що тіло JSON
                .header(AUTH, TOKEN) // додаємо токен авторизації
                .body(ContactDto.builder() // будуємо новий контакт зі змінами
                        .id(id) // id контакту в precondition
                        .name("Mikhael")
                        .lastName("Dikson")
                        .email("miky_new@gm.de")
                        .phone("987654321012")
                        .address("Berlin")
                        .description("goalkeeper"))
                .when() // виконуємо сам запит (коли виконається метод put)
                .put("contacts")  // Відправляємо PUT-запит, щоб змінити контакт
                .then() // тоді (перевірка відповіді)
                .assertThat().statusCode(200)// очікуємо статус код 200
                .assertThat().body("message", equalTo("Contact was updated")); // асьортим і отримуємо повідомлення,
        // яке є еквівалентним тексту "Contact was updated".
    }
}

