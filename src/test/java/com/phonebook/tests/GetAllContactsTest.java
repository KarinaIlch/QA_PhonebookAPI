package com.phonebook.tests;

import com.phonebook.core.TestBase;
import com.phonebook.dto.AllContactsDto;
import com.phonebook.dto.ContactDto;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GetAllContactsTest extends TestBase {

    @Test
    public void getAllContactsSuccessTest() {
        AllContactsDto dto =
        given()
                .header(AUTH,TOKEN)
                .when()
                .get("contacts")
                .then()
                .assertThat().statusCode(200)
                .extract().response().as(AllContactsDto.class);
        // витягуємо всю відповідь і мапимо її у клас AllContactsDto

        for (ContactDto contact: dto.getContacts()){ // проходимо по кожному контакту з отриманого списку
            System.out.println(contact.getId() + " ***** " + contact.getName());
            System.out.println(" ============= ");
            }
        }

        @Test
    public void getAllContactsWithInvalidToken() {     // перевіряємо поведінку з неправильним токеном

            given()
                .header(AUTH,"tyreq") // замість справжнього токена передаємо випадковий рядок
                .when()
                .get("contacts") // пробуємо отримати список контактів
                .then()
                .assertThat().statusCode(401) // очікуємо код 401 (Unauthorized)
                .assertThat().body("error",equalTo("Unauthorized"));  // перевіряємо, що у відповіді поле error = "Unauthorized"

        }
    }

