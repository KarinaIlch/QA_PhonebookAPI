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
                .then().assertThat().statusCode(200)
                .extract().response().as(AllContactsDto.class);

        for (ContactDto contact: dto.getContacts()){
            System.out.println(contact.getId() + " ***** " + contact.getName());
            System.out.println(" ============= ");
            }
        }

        @Test
    public void getAllContactsWithInvalidToken() {
        given()
                .header(AUTH,"wower98u")
                .when()
                .get("contacts")
                .then()
                .assertThat().statusCode(401)
                .assertThat().body("error",equalTo("Unauthorized"));
        }
    }

