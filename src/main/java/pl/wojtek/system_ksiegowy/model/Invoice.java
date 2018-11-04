package pl.wojtek.system_ksiegowy.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Entity
public class Invoice implements Serializable
{
    private static final long serialVersionUID = 6464841658464l;

    private static DecimalFormat format = new DecimalFormat("#.##");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String invoiceNumber;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date  issueDate;
    private double amount;
    private double balance;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date dueDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date paymentDate;
    private double paid;
    private double netPrice;
    private double grossPrice;
    private Date sendDate;
    @ManyToOne
    @JoinColumn(name = "contractor_id")
    private Contractor contractor;

    public Invoice() {}

    public Invoice(String invoiceNumber, Date issueDate, double amount, double balance, Date dueDate, Date paymentDate, double paid, double netPrice, double grossPrice, Date sendDate, Contractor contractor) {
        this.invoiceNumber = invoiceNumber;
        this.issueDate = issueDate;
        this.amount = amount;
        this.balance = balance;
        this.dueDate = dueDate;
        this.paymentDate = paymentDate;
        this.paid = paid;
        this.netPrice = netPrice;
        this.grossPrice = grossPrice;
        this.sendDate = sendDate;
        this.contractor = contractor;
    }

    public Long getId() {
        return id;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public double getAmount() {
        return amount;
    }

    public String getBalance()
    {
        return format.format(balance);
    }

    public Date getDueDate() {
        return dueDate;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public String getNetPrice()
    {
        return format.format(netPrice);
    }

    public String getGrossPrice()
    {
        return format.format(grossPrice);
    }

    public Date getSendDate() {
        return sendDate;
    }

    public Contractor getInvoice() {
        return contractor;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void setNetPrice(double netPrice) {
        this.netPrice = netPrice;
    }

    public void setGrossPrice(double grossPrice) {
        this.grossPrice = grossPrice;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public void setContractor(Contractor contractor) {
        this.contractor = contractor;
    }

    public double getPaid() {
        return paid;
    }

    public void setPaid(double paid) {
        this.paid = paid;
    }

    public Contractor getContractor() {
        return contractor;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", invoiceNumber=" + invoiceNumber +
                ", issueDate=" + issueDate +
                ", amount=" + amount +
                ", balance=" + balance +
                ", dueDate=" + dueDate +
                ", paymentDate=" + paymentDate +
                ", netPrice=" + netPrice +
                ", grossPrice=" + grossPrice +
                ", sendDate=" + sendDate +
                ", invoice=" + contractor +
                '}';
    }

    public String getYear()
    {
        Date date = this.getIssueDate();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        return year;
    }

    public String  getMonth()
    {
        Date date = this.getIssueDate();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        return month;
    }

    public String getDay()
    {
        Date date = this.getIssueDate();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        return day;
    }
}

