package com.example.com.myapp.pojo;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
public class User {
    @PrimaryKey
    private Integer id;

    @SerializedName("avatr_url")
    @Expose
    private String imageUrl;
    @SerializedName("f_name")
    @Expose
    private String firstName;
    @SerializedName("l_name")
    @Expose
    private String lastName;
    @SerializedName("birthday")
    @Expose
    private String birthday;


    @SerializedName("specialty")
    @Expose
    @TypeConverters({SpecialityConverter.class})
    private List<Speciality> speciality;

    public static class Speciality {
        private String name;
        private Integer specialty_id;

        public String toString() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setSpecialty_id(Integer specialty_id) {
            this.specialty_id = specialty_id;
        }

        public String getName() {
            return name;
        }

        public Integer getSpecialty_id() {
            return specialty_id;
        }
    }

    public static class SpecialityConverter {

        @TypeConverter
        public String fromSpeciality(List<Speciality> speciality) {
            return speciality.toString();
        }

        @TypeConverter
        public List<Speciality> toSpeciality(String data) {
            List<Speciality> specialities = new ArrayList<>();
            Speciality speciality = new Speciality();
            speciality.setName(data);
            specialities.add(speciality);
            return specialities;
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static String toUpperCaseForFirstLetter(String text) {
        StringBuilder builder = new StringBuilder(text);
//выставляем первый символ заглавным, если это буква
        if (Character.isAlphabetic(text.codePointAt(0)))
            builder.setCharAt(0, Character.toUpperCase(text.charAt(0)));
//крутимся в цикле, и меняем буквы, перед которыми пробел на заглавные
        for (int i = 1; i < text.length(); i++)
            if (Character.isAlphabetic(text.charAt(i)) && Character.isSpaceChar(text.charAt(i - 1)))
                builder.setCharAt(i, Character.toUpperCase(text.charAt(i)));
        return builder.toString();
    }


    public User(String imageUrl, String firstName, String lastName, String birthday, List<Speciality> speciality) {
        this.imageUrl = imageUrl;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            this.firstName = toUpperCaseForFirstLetter(firstName.toLowerCase());
            this.lastName = toUpperCaseForFirstLetter(lastName.toLowerCase());
        } else {
            this.firstName = firstName;
            this.lastName = lastName;
        }
        this.birthday = getNormalDate(birthday);
        this.speciality = speciality;
    }

    private String getNormalDate(String birthday) {
        String result = "";
        if (birthday != null && !birthday.isEmpty()) {
            if (birthday.matches("\\d{2}-\\d{2}-\\d{4}")) {
                result = birthday.replace("-", ".");
            } else {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = sdf.parse(birthday);
                    sdf.applyPattern("dd.MM.yyyy");
                    result = sdf.format(date);
                } catch (ParseException e) {
                    result = birthday;
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setfirstName(String fName) {
        this.firstName = fName;
    }

    public void setlastName(String lName) {
        this.lastName = lName;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setSpeciality(List<Speciality> speciality) {
        this.speciality = speciality;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBirthday() {
        return birthday;
    }


    public List<Speciality> getSpeciality() {
        return speciality;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (imageUrl != null ? !imageUrl.equals(user.imageUrl) : user.imageUrl != null)
            return false;
        if (!firstName.equals(user.firstName)) return false;
        if (!lastName.equals(user.lastName)) return false;
        if (birthday != null ? !birthday.equals(user.birthday) : user.birthday != null)
            return false;
        return speciality != null ? speciality.equals(user.speciality) : user.speciality == null;
    }

    @Override
    public int hashCode() {
        int result = imageUrl != null ? imageUrl.hashCode() : 0;
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (speciality != null ? speciality.hashCode() : 0);
        return result;
    }
}
