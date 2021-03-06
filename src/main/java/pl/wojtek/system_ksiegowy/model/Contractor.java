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
    private String fullName;
    private int NIP;
    private int REGON;
    private int KRS;
    private String street;
    private String houseNumber;
    private String apartmentNumber;
    private String city;
    private String post;
    private String voivodeship;
    private String county;
    private String streetCorresspond;
    private String houseNumberCorresspond;
    private String apartmentNumberCorresspond;
    private String cityCorresspond;
    private String postCorresspond;
    private String voivodeshipCorresspond;
    private String countyCorresspond;
    private long phoneNumber;
    private String email;
    private String contactPerson;
    private String fullAddress;
    @OneToMany(mappedBy = "contractor")
    private List<Contract> contracts;
    @OneToMany(mappedBy = "contractor")
    private List<Invoice> invoices;

    public Contractor() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getNIP() {
        return NIP;
    }

    public void setNIP(int NIP) {
        this.NIP = NIP;
    }

    public int getREGON() {
        return REGON;
    }

    public void setREGON(int REGON) {
        this.REGON = REGON;
    }

    public int getKRS() {
        return KRS;
    }

    public void setKRS(int KRS) {
        this.KRS = KRS;
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

    public String getVoivodeship() {
        return voivodeship;
    }

    public void setVoivodeship(String voivodeship) {
        this.voivodeship = voivodeship;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
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

    public String getVoivodeshipCorresspond() {
        return voivodeshipCorresspond;
    }

    public void setVoivodeshipCorresspond(String voivodeshipCorresspond) {
        this.voivodeshipCorresspond = voivodeshipCorresspond;
    }

    public String getCountyCorresspond() {
        return countyCorresspond;
    }

    public void setCountyCorresspond(String countyCorresspond) {
        this.countyCorresspond = countyCorresspond;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
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

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    @Override
    public String toString() {
        return  fullName +
                ", NIP: " + NIP +
                ", Adres: " + contractorAddress(this);

    }

    public String contractorAddress(Contractor contractor)
    {
        StringBuilder address = new StringBuilder();
        if (!contractor.getStreet().equals(""))
            address.append(contractor.getStreet() + " ");
        if (!contractor.getHouseNumber().equals(""))
        {
            address.append(contractor.getHouseNumber());
            if (!contractor.getApartmentNumber().equals(""))
                address.append("/" + contractor.getApartmentNumber());
        }
        return address.toString();
    }
}
