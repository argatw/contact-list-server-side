package com.example.retestday34contactserver.models;

import java.io.StringReader;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Registration {
    private String id;
    private String name;
    private String email;
    private String tel;

    public String getTel() {
        return tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public static Registration create(String json) {
        JsonReader r = Json.createReader(new StringReader(json));
        JsonObject data = r.readObject();

        final Registration reg = new Registration();
        reg.setName(data.getString("name"));
        reg.setEmail(data.getString("email"));
        reg.setTel(data.getString("tel"));
        if (data.containsKey("id")) 
            reg.setId(data.getString("id"));
        return reg;
    }

    public static Registration convert(SqlRowSet rs) {
        Registration r = new Registration();
        r.setName(rs.getString("name"));
        r.setEmail(rs.getString("email"));
        r.setTel(rs.getString("tel"));
        r.setId(rs.getString("id"));
        return r;
    }

    //part 2 to get data entered
    public JsonObject toJson() {
        return Json.createObjectBuilder()
            .add("id", id)
            .add("name", name)
            .add("email", email)
            .add("tel", tel)
            .build();
    }
}
