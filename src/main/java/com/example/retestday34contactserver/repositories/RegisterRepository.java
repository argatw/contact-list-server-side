package com.example.retestday34contactserver.repositories;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;


import com.example.retestday34contactserver.models.Registration;

@Repository
public class RegisterRepository {
    @Autowired
    private JdbcTemplate temp;

    // query
    // public Registerees getUserByEmail(String email) {
    //     final SqlRowSet q = temp.queryForRowSet(
    //         "select * from registerees where email like ?", email
    //     );
    //     if(!q.next())
    //         return null;

    //     return Registerees.convert(q);
    // }

    public boolean deleteUserItemByEmail(String email) {
        int updated = temp.update("delete from registerees where email = ?", email);
        return 1 == updated;
    }

    // public List<Registerees> getUsersByEmail (String email) {
    //     List<Registerees> regItems = new LinkedList<>();
    //     SqlRowSet rs = temp.queryForRowSet("select * from registerees where email = ?", email);
    //     while (rs.next()) {
    //         Registerees regItem = Registerees.convert(rs);
    //         regItems.add(regItem);
    //     }
        
    //     return regItems;
    // }

    public List<Registration> getAllUsers () {
        List<Registration> regItems = new LinkedList<>();
        SqlRowSet rs = temp.queryForRowSet("select * from registerees");
        while (rs.next()) {
            Registration regItem = Registration.convert(rs);
            regItems.add(regItem);
        }
        
        return regItems;
    }

    // insert "insert into user (userId,email,password) values (?,?,sha1(?))"
    public boolean saveNewUser(Registration reg) {
        final int added = temp.update(
            "insert into registerees (name,email,tel,id) values (?,?,?,?)"
            ,reg.getName(),reg.getEmail(),reg.getTel(),reg.getId()
        );
        return added > 0;
    }
    
}
