package com.example.aprg_projekt.utils;

import com.example.aprg_projekt.models.Profile;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ProfileRowMapper implements RowMapper<Profile> {
    @Override
    public Profile mapRow(ResultSet rs, int rowNum) throws SQLException {
        Profile profile = new Profile();

        profile.setId(UUID.fromString(rs.getString("id")));
        profile.setAboutMe(rs.getString("aboutMe"));
        profile.setDegreeCourse(rs.getString("degreeCourse"));
        profile.setSemester(rs.getInt("semester"));

        return profile;
    }
}
