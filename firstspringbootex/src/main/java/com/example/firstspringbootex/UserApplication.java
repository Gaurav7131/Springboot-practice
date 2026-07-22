package com.example.firstspringbootex;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class UserApplication {
    public static void main(String[] args) throws IOException {

        UserJsonProcessor processor = new UserJsonProcessor();

        Path inputfile = Paths.get("src/main/resources/User.json");
        Path ValidOutputFile = Paths.get("data/valid.json");
        Path errorOutputFile = Paths.get("data/error.json");

        try {
            System.out.println("Starting user processing:");

            // read all
            List<Users> allusers = processor.readerUsers(inputfile);
            System.out.println("Read file" + allusers.size() + "from users");

            // Split valid invalid
            ValidationSplit split = processor.splitValidInvalid(allusers);

            // write o/p to respective files
            processor.writeUsers(split.getvalid(), ValidOutputFile);
            processor.writeUsers(split.getinvalid(), errorOutputFile);

            // print the summary
            System.out.println("Pipeline Summary:");
            System.out.println("Valid file" + split.getvalid().size());
            System.out.println("Invalid File" + split.getinvalid().size());
            System.out.println("Check the json data in '/data' folder");
        } catch (IOException e) {
            System.out.println("File ops failed:" + e.getMessage());
        }

    }

}
