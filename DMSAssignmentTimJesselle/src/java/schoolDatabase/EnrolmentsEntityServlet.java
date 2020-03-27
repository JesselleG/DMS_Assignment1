/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schoolDatabase;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author dqp6065 & vmm0807
 */
@WebServlet(name = "EnrolmentsEntityServlet", urlPatterns = 
{
    "/EnrolmentsEntityServlet"
})

public class EnrolmentsEntityServlet extends HttpServlet
{
    private final char QUOTE = '"';
    private Logger logger;
    @PersistenceContext
    private EntityManager entityManager;
            
    public EnrolmentsEntityServlet()
    {
        logger = Logger.getLogger(getClass().getName());
    }
    
    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response) 
            throws ServletException, IOException
    {      
        int studentID = (int) request.getAttribute("StudentID");
        
        List<Student> studentList = null;  
        if(entityManager != null){
            String jpqlCommand = "SELECT "+studentID+" FROM Enrolments"; //select everything from student table
            Query query = entityManager.createQuery(jpqlCommand);
            studentList = query.getResultList();
            logger.info("Success!");
        }
        response.setContentType("text/html;charset=UTF-8");
     
        try (PrintWriter out = response.getWriter()){
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Student Enrolment Servlet Response</title>");
            out.println("</head>");
            out.println("<body>");

            if (studentList != null) {
            out.println("<table cellspacing=1 border=5>");
            out.println("<tr><td><b>Student ID</b></td>" + "<td><b>First Name</b></td>"  + "<td><b>Last Name</b></td>"
             + "<td><b>Gender</b></td>" + "<td><b>D.O.B</b></td>" + "<td><b>Phone Number</b></td></tr>");
            for (Student student : studentList)
            {
               int id = student.getStudentId();
               String fName = student.getFirstName();
               String lName = student.getLastName();
               String gender = student.getGender();
               Date dob = student.getBirthday();
               int phNo = student.getPhoneNumber();
               out.println("<tr><td>" + id + "</td><td>" + fName  + "</td><td>" + lName  + "</td><td>" + gender
                        + "</td><td>" + dob  + "</td><td>" + phNo + "</td></tr>");
            }
            out.println("</table>");
         }
            out.println("<p><a href=" + QUOTE + response.encodeURL("StudentForm.jsp") + QUOTE + ">" + "Return to Student Information Page</a></p>");
         out.println("</body>");
         out.println("</html>");      
      }
    }
   // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
   /**
    * Handles the HTTP <code>GET</code> method.
    *
    * @param request servlet request
    * @param response servlet response
    * @throws ServletException if a servlet-specific error occurs
    * @throws IOException if an I/O error occurs
    */
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException
   {
      processRequest(request, response);
   }

   /**
    * Handles the HTTP <code>POST</code> method.
    *
    * @param request servlet request
    * @param response servlet response
    * @throws ServletException if a servlet-specific error occurs
    * @throws IOException if an I/O error occurs
    */
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException
   {
      processRequest(request, response);
   }

   /**
    * Returns a short description of the servlet.
    *
    * @return a String containing servlet description
    */
   @Override
   public String getServletInfo()
   {
      return "Short description";
   }// </editor-fold>  
}
