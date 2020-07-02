/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schoolDatabase;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author dqp6065 & vmm0807
 */

@Entity
@Table(name = "Enrollments")
public class Enrolments implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "S_Id")
    private String stuID;
    @Id
    @Column(name = "CNN")
    private int courseNo;
    @Column(name = "Semester")
    private int semester;
    @Column(name = "Year")
    private int year;
    @Column(name = "Grade")
    private String grade;
    
    public Enrolments(){}
    public String getID(){
        return stuID;
    }
    public int getCNN(){
        return courseNo;
    }
    public int getSemester(){
        return semester;
    }
    public int getYear(){
        return year;
    }
    public String getGrade(){
        return grade;
    }
}
