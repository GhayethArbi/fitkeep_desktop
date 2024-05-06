package models;



import java.sql.Date;

public class User1 {
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



    private User1(UserBuilder builder)
    {
        this.id = builder.id;
        this.email = builder.email;
        this.roles = builder.roles;
        this.password = builder.password;
        this.name = builder.name;
        this.lastName = builder.lastName;
        this.gender = builder.gender;
        this.birthDay = builder.birthDay;
        this.phoneNumber = builder.phoneNumber;
        this.loyalityPoints = builder.loyalityPoints;
        this.profileImage = builder.profileImage;
        this.address = builder.address;
        this.authCode = builder.authCode;
        this.isBanned = builder.isBanned;
        this.registration_date = builder.registration_date;
        this.resetToken=builder.resetToken;
    }

    public User1(  String name, String lastName,String email) {

        this.email = email;
        this.name = name;
        this.lastName = lastName;

    }
    public int getId() {
        return id;
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
    public int getPhoneNumber() {
        return phoneNumber;
    }
    public int getLoyalityPoints() {
        return loyalityPoints;
    }
    public String getProfileImage() {
        return profileImage;
    }
    public String getAddress() {
        return address;
    }
    public String getAuthCode() {
        return authCode;
    }
    public boolean isBanned() {
        return isBanned;
    }
    public Date getRegistration_date() {
        return registration_date;
    }
    public String getResetToken() {
        return resetToken;
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
















    public static class UserBuilder {
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

        public UserBuilder setId(int id) {
            this.id = id;
            return this;
        }
        public UserBuilder setEmail(String email) {
            this.email = email;
            return this;
        }
        public UserBuilder setBirthDay(Date birthDay) {
            this.birthDay = birthDay;
            return this;
        }
        public UserBuilder setPassword(String password) {
            this.password = password;
            return this;
        }
        public UserBuilder setRoles(Roles roles) {
            this.roles = roles;
            return this;
        }
        public UserBuilder setName(String name) {
            this.name = name;
            return this;
        }
        public UserBuilder setGender(String gender) {
            this.gender = gender;
            return this;
        }
        public UserBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }
        public UserBuilder setPhoneNumber(int phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }
        public UserBuilder setLoyalityPoints(int loyalityPoints) {
            this.loyalityPoints = loyalityPoints;
            return this;
        }
        public UserBuilder setProfileImage(String profileImage) {
            this.profileImage = profileImage;
            return this;
        }
        public UserBuilder setAddress(String address) {
            this.address = address;
            return this;
        }
        public UserBuilder setAuthCode(String authCode) {
            this.authCode = authCode;
            return this;
        }
        public UserBuilder setBanned(boolean banned) {
            isBanned = banned;
            return this;
        }
        public UserBuilder setRegistration_date(Date registration_date) {
            this.registration_date = registration_date;
            return this;
        }
        public UserBuilder setResetToken(String resetToken) {
            this.resetToken = resetToken;
            return this;
        }
        public User1 build(){
            return new User1(this);
        }
    }
}
