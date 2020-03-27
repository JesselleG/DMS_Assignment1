package schoolDatabase;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author dqp6065 & vmm0807
 */
@Entity
@Table(name = "Students")
public class Student implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "S_Id")
    private String stuID; //student id
    @Column(name = "H_Phone")
    private int phNo; // phone number
    @Column(name = "G_Name")
    private String stuFirst; //student's first name
    @Column(name = "S_Name")
    private String stuLast; //student's last name
    @Column(name = "Gender")
    private String gender; 
    @Column(name = "B_Date")
    @Temporal(TemporalType.DATE)
    private Date bDay; //birth day
    
    public Student(){}
    public String getFirstName(){
        return stuFirst;
    }
    public void setFirstName(String stuFirst) {
        this.stuFirst = stuFirst;
    }
    public String getLastName(){
        return stuLast;
    }
    public void setLastName(String stuLast) {
        this.stuLast = stuLast;
    }
    public String getStudentId(){
        return stuID;
    }
    public void setStudentId(String stuID) {
        this.stuID = stuID;
    }
    public int getPhoneNumber(){
        return phNo;
    }
    public void setPhoneNumber(int phNo) {
        this.phNo = phNo;
    }
    public String getGender(){
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public Date getBirthday(){
        return bDay;
    }
    public void setBirtday(Date bDay) {
        this.bDay = bDay;
    }
    //set
}