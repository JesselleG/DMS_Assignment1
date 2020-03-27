package schoolDatabase;

import java.io.IOException;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

/**
 *
 * @author dqp6065 & vmm0807
 */
@WebServlet(name = "LoginServlet", urlPatterns = 
{
    "/LoginServlet"
})
public class LoginServlet extends HttpServlet{
    private Logger logger;
    
    public LoginServlet(){
        //logger for warnings
        logger = Logger.getLogger(this.getClass().getName());
    }

   protected void processRequest(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {
        String username = request.getParameter("username");
        String cU = "CurrentUser";
        
            CurrentUser user = new CurrentUser();

        if(username == null || username.length() == 0){
            user.setUsername("Guest");
        }
        else{
            user.setUsername(username);
        }        
        
        //ensures a new session is created everytime user logs out/enters login page
        HttpSession session = request.getSession(); 
        if(session.getAttribute(cU)!=null){
            //just to be safe, remove any "CurrentUser" attributes that may be in the HttpSession 
            session.removeAttribute(cU);
        }
        session.setAttribute(cU, user);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/StudentForm.jsp");
        dispatcher.forward(request, response);            
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
