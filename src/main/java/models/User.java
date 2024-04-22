package models;

import java.sql.Date;

public class User {

    private int id;
    private String email;
    private Roles roles;
    private String password;
    private String name, lastName;
    private String gender;
    private Date birthDay;
    private int phoneNumber;
    private int loyalityPoints;
    private String profileImage;
    private String address;
    private String authCode;
    private boolean isBanned;
    private Date registration_date;
    private String resetToken;

    public User() {
    }

    public User(int id, String email, Roles roles, String password, String name, String lastName, String gender, Date birthDay, int phoneNumber, int loyalityPoints, String profileImage, String address, String authCode, boolean isBanned, Date registration_date, String resetToken) {
        this.id = id;
        this.email = email;
        this.roles = roles;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.gender = gender;
        this.birthDay = birthDay;
        this.phoneNumber = phoneNumber;
        this.loyalityPoints = loyalityPoints;
        this.profileImage = profileImage;
        this.address = address;
        this.authCode = authCode;
        this.isBanned = isBanned;
        this.registration_date = registration_date;
        this.resetToken = resetToken;
    }

    public User(String name, String lastName, String email) {

    this.email = email;
    this.name = name;
    this.lastName = lastName;

}
    public int getId() {
        return id;
    }


    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public void setLoyalityPoints(int loyalityPoints) {
        this.loyalityPoints = loyalityPoints;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getEmail() {
        return email;
    }



    public Roles getRoles() {
        return roles;
    }



    public String getPassword() {
        return password;
    }



    public String getName() {
        return name;
    }



    public String getLastName() {
        return lastName;
    }



    public String getGender() {
        return gender;
    }



    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setRoles(String roleString) {
        roleString = roleString.replaceAll("[\\[\\]\"]", ""); // Remove square brackets and double quotes
        Roles roles = Roles.valueOf(roleString);// Convert the string value to Roles enum
        this.roles = roles;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public int getPhoneNumber() {
        return phoneNumber;
    }


    public int getLoyalityPoints() {
        return loyalityPoints;
    }


    public String getProfileImage() {
        return profileImage;
    }

    public void setId(int id) {
        this.id = id;

    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    public Date getRegistration_date() {
        return registration_date;
    }

    public void setRegistration_date(Date registration_date) {
        this.registration_date = registration_date;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", birthDay=" + birthDay +
                ", phoneNumber=" + phoneNumber +
                ", loyalityPoints=" + loyalityPoints +
                ", profileImage='" + profileImage + '\'' +
                ", address='" + address + '\'' +
                ", authCode='" + authCode + '\'' +
                ", isBanned=" + isBanned +
                ", registration_date=" + registration_date +
                '}';
    }

}
