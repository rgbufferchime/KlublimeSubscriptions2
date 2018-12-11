package com.bufferchime.klublimesubscriptions;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

// [START blog_user_class]
@IgnoreExtraProperties
public class User {


    public String email;
    public String name;
    public String userid;


    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String name, String email, String userid) {
        this.email = email;
        this.name= name;
        this.userid=userid;

    }


    public String getname(){return  name;}
    public String getEmail(){return  email;}
    public String getUserid(){return  userid;}



    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> User = new HashMap<>();
        User.put("name", name);
        User.put("email", email);



        return User;
    }
}
// [END blog_user_class]