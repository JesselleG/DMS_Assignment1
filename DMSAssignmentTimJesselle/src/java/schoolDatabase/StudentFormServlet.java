/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schoolDatabase;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author dqp6065 & vmm0807
 */
@WebServlet(name = "StudentFormServlet", urlPatterns = 
{
    "/StudentFormServlet"
})
public class StudentFormServlet extends HttpServlet{ 
    public StudentFormServlet(){
        
    }
    
    protected void processRequest(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {
        String parameterID = request.getParameter("studentID");
        int studentID = Integer.parseInt(parameterID);
        
        //ensures a new session is created everytime user logs out/enters login page
        request.setAttribute("StudentID", studentID);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/EnrollmentsEntityServlet");
        dispatcher.forward(request, response);
   }    
}
