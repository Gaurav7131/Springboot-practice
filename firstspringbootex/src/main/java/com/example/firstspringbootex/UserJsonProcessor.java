package com.example.firstspringbootex;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

public class UserJsonProcessor {
    private final ObjectMapper mapper = new ObjectMapper();
    private final UserValidation validator = new UserValidation();

    // Read JSON array into List<User>
    public List<Users> readerUsers(Path input) throws IOException {
        if (!Files.exists(input)) {
            throw new IOException("The file is missing at path: " + input.toString());
        }
        return mapper.readValue(input.toFile(), new TypeReference<List<Users>>() {
        });
    }

    // partition users into valid and invalid
    public ValidationSplit splitValidInvalid(List<Users> users) {
        List<Users> validList = new ArrayList<>();
        List<Users> invalidList = new ArrayList<>();

        for (Users user : users) {
            if (validator.isValid(user)) {
                validList.add(user);
            } else {
                invalidList.add(user);
            }
        }
        return new ValidationSplit(validList, invalidList);
    }
}
