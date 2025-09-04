package com.phonebook.data;

import com.phonebook.dto.ContactDto;

public class ObjectsData {
    public static ContactDto contactDto = ContactDto.builder()
            .name("Milla")
            .lastName("Wattson")
            .email("milla123@gm.de")
            .phone("123456789012")
            .address("Berlin")
            .description("goalkeeper")
            .build();
}
