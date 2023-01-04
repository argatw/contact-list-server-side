package com.example.retestday34contactserver.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.retestday34contactserver.models.Registration;
import com.example.retestday34contactserver.repositories.RegisterRepository;

@Service
public class RegisterService {

    @Autowired
    private RegisterRepository rRepo;

    public boolean insertUser(Registration reg) {
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString().substring(0,8);
        reg.setId(id);
        boolean added = rRepo.saveNewUser(reg);
        if (added) {
            return true;
        } else {
            return false;
        }
    }

    // public List<Registerees> getRegisterees(String email) {
    //     return rRepo.getUsersByEmail(email);
    // }

    public List<Registration> getRegisterees() {
        return rRepo.getAllUsers();
    }

    public void deleteRegistereeByEmail(String email) {
        rRepo.deleteUserItemByEmail(email);
    }
}
