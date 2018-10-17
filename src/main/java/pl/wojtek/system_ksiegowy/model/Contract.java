package pl.wojtek.system_ksiegowy.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Contract implements Serializable
{
    private static final long serialVersionUID = 64448748484684l;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contractNumber;
    private Date signDate;
    private Date endDate;
    private byte length;
    private double salary;
    @ManyToOne
    @JoinColumn(name = "contractor_id")
    private Contractor contractor;
    @ManyToMany(cascade =  CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "contract_services", joinColumns = {@JoinColumn(name = "contract_id", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "service_id", referencedColumnName = "id")})
    private Set<Service> services = new HashSet<>();

    public Contract() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public byte getLength() {
        return length;
    }

    public void setLength(byte length) {
        this.length = length;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Contractor getContractor() {
        return contractor;
    }

    public void setContractor(Contractor contractor) {
        this.contractor = contractor;
    }

    public Set<Service> getServices() {
        return services;
    }

    public void setServices(Set<Service> services) {
        this.services = services;
    }
}
