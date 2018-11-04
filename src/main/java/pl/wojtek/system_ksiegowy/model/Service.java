package pl.wojtek.system_ksiegowy.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
public class Service implements Serializable, Comparable<Service>
{
    private static final long serialVersionUID = 84849844645645l;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String serviceName;
    @Column(nullable = false)
    private String serviceDescription;
    @Column(nullable = false)
    private Date startDate;

    public Service() {}

    public Service(String serviceName, String serviceDescription) {
        this.serviceName = serviceName;
        this.serviceDescription = serviceDescription;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return serviceName + ", " + serviceDescription;
    }

    @Override
    public int compareTo(Service o) {
        return id.compareTo(o.getId());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj instanceof Service)
        {
            Service service = (Service) obj;
            return id == service.id &&
                    serviceName.equals(service.serviceName) &&
                    serviceDescription.equals(service.serviceDescription) &&
                    startDate == service.startDate;
        }
        return false;
    }
}
