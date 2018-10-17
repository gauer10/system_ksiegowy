package pl.wojtek.system_ksiegowy.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Contractor implements Serializable
{
    private static final long serialVersionUID = 56464849644644l;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastname;
    private String identificationType;
    private String identification;
    private String street;
    private String houseNumber;
    private String apartmentNumber;
    private String city;
    private String post;
    private String streetCorresspond;
    private String houseNumberCorresspond;
    private String apartmentNumberCorresspond;
    private String cityCorresspond;
    private String postCorresspond;
    private int phoneNumber;
    private String email;
    @OneToMany(mappedBy = "contractor")
    private List<Contract> contracts;

    public Contractor() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(String identificationType) {
        this.identificationType = identificationType;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getStreetCorresspond() {
        return streetCorresspond;
    }

    public void setStreetCorresspond(String streetCorresspond) {
        this.streetCorresspond = streetCorresspond;
    }

    public String getHouseNumberCorresspond() {
        return houseNumberCorresspond;
    }

    public void setHouseNumberCorresspond(String houseNumberCorresspond) {
        this.houseNumberCorresspond = houseNumberCorresspond;
    }

    public String getApartmentNumberCorresspond() {
        return apartmentNumberCorresspond;
    }

    public void setApartmentNumberCorresspond(String apartmentNumberCorresspond) {
        this.apartmentNumberCorresspond = apartmentNumberCorresspond;
    }

    public String getCityCorresspond() {
        return cityCorresspond;
    }

    public void setCityCorresspond(String cityCorresspond) {
        this.cityCorresspond = cityCorresspond;
    }

    public String getPostCorresspond() {
        return postCorresspond;
    }

    public void setPostCorresspond(String postCorresspond) {
        this.postCorresspond = postCorresspond;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }
}
