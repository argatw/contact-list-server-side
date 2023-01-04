package com.example.retestday34contactserver.controllers;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.example.retestday34contactserver.models.Registration;
import com.example.retestday34contactserver.models.Response;
import com.example.retestday34contactserver.services.RegisterService;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path="/api",produces = MediaType.APPLICATION_JSON_VALUE)
public class RegistrationRESTController {
    private Logger logger = Logger.getLogger(RegistrationRESTController.class.getName());

    @Autowired
    private RegisterService regService;

    @PostMapping(path="/registration",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postRegistration(@RequestBody String payload){
        logger.info("Payload: %s".formatted(payload));
        Registration reg;
        Response resp;
        // Response resp = new Response();

        // read the payload and save the data to database
        // String id = UUID.randomUUID().toString().substring(0,8);
        // Registration reg = Registration.create(payload);
        // reg.setId(id);
        try {
            reg = Registration.create(payload);
            // reg.setId(id);
            regService.insertUser(reg);

        } catch (Exception e) {
            resp = new Response();
            resp.setCode(400);
            resp.setMessage(e.getMessage());
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(resp.toJson().toString());
        }

        resp = new Response();
        resp.setCode(201);
        // resp.setMessage(id);
        resp.setData(reg.toJson().toString()); //part 2 to get data entered
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(resp.toJson().toString());
        // return ResponseEntity.status(HttpStatus.CREATED).body("{}");
    }

    @GetMapping(path="/users",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getListOfUsers() {
        // Response resp = new Response();
        // List<Registration> rList = new LinkedList<>();
        List<Registration> rList = regService.getRegisterees();

        JsonArrayBuilder ab = Json.createArrayBuilder();
        for (Registration rege : rList) {
            ab.add(rege.toJson());
        }
        // resp.setCode(200);
        // resp.setMessage("Userlist retrieval successful");
        // resp.setData(ab.build().toString());
        // return ResponseEntity.status(HttpStatus.CREATED)
        //         .body(resp.toJson().toString());
        return ResponseEntity.ok(ab.build().toString());

    }

    @DeleteMapping(path = "/deleteuser")
    public ResponseEntity<String> deleteCartItemByUser(@RequestParam String email) {
        try {
            regService.deleteRegistereeByEmail(email);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
        }

        Response r = new Response();
        r.setMessage("Fav item delete success.");
        r.setCode(200);

        return ResponseEntity.status(HttpStatus.OK).body(r.toJson().toString());

    }


}
