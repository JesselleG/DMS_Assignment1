package schoolDatabase;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 *
 * @author dqp6065 & vmm0807
 */


@WebServlet(name = "StudentServlet", urlPatterns = {
    "/StudentServlet"
}, initParams = 
{
    @WebInitParam(name = "dbTable", value = "Students"),
    @WebInitParam(name = "dbIDAtt", value = "S_ID"),
    @WebInitParam(name = "dbFirstAtt", value = "G_Name"),    
    @WebInitParam(name = "dbLastAtt", value = "S_Name"),    
})

public class StudentServlet extends HttpServlet{
   private final char QUOTE = '"';
   private Logger logger;
   private String sqlCommand;

   @Resource(mappedName = "jdbc/SQLUniversityDB")
   private DataSource dataSource;

   public StudentServlet()
   {
      logger = Logger.getLogger(getClass().getName());
   }

   @Override
   public void init()
   {
      // obtain servlet configuration values from annotation or web.xml
      ServletConfig config = getServletConfig();
      String dbTable = config.getInitParameter("dbTable");
      String dbIDAtt = config.getInitParameter("dbIDAtt");
      String dbFirstAtt = config.getInitParameter("dbFirstAtt");
      String dbLastAtt = config.getInitParameter("dbLastAtt");
      sqlCommand = "SELECT " + dbIDAtt + ", " + dbFirstAtt
         + ", " + dbLastAtt + " FROM " + dbTable;
   }
   protected void processRequest(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException
   {
       HttpSession session = request.getSession();
       CurrentUser user = (CurrentUser) session.getAttribute("CurrentUser");
       // query database
      Connection conn = null;
      PreparedStatement prepStmt = null;
      ResultSet resultSet = null;
      if (sqlCommand != null && dataSource != null)
      {
         try
         {
            conn = dataSource.getConnection();
            prepStmt = conn.prepareStatement(sqlCommand);
            resultSet = prepStmt.executeQuery();
            logger.info("Succes!");
         }
         catch (SQLException e)
         {
            logger.severe("Unable to execute query"+ e);
         }
      }
      // set response headers before returning any message content
      response.setContentType("text/html;charset=UTF-8");
      try (PrintWriter out = response.getWriter())
      {
         out.println("<!DOCTYPE html>");
         out.println("<html>");
         out.println("<head>");
         out.println("<title>Student Servlet Response</title>");
         out.println("<link href=\"styles.css\" rel=\"stylesheet\">");
         out.println("</head>");
         
         out.println("<body>");
         out.println("<h1>List of Students</h1>");
         
         out.println("<h3>Active User: <i>"+user.getUsername()+"</i>\n");
         out.println("</h3>");
         
         if (resultSet != null)
         {
            out.println("<TABLE cellspacing=5 border=2>");
            out.println("<tr><td><b>Student ID</b></td>" + "<td><b>First Name</b></td>"  + "<td><b>Last Name</b></td></tr>");
            try
            {
               while (resultSet.next())
               {
                  String id = resultSet.getString("S_Id");
                  String firstName = resultSet.getString("G_Name");
                  String lastName = resultSet.getString("S_Name");
                  
                  out.println("<TR><TD>" + id + "</TD><TD>"
                     + firstName + "</TD><TD>" + lastName + "</TD></TR>");
               }
            }
            catch (SQLException e)
            {
               logger.severe("Exception in result set for students " + e);
            }
            out.println("</TABLE>");
         }
         try
         {
            if (prepStmt != null)
               prepStmt.close();
            if (conn != null)
               conn.close(); // release conn back to pool
         }
         catch (SQLException e)
         {  // ignore
         }
         out.println("<P><A href=" + QUOTE
            + response.encodeURL("StudentForm.jsp") + QUOTE + ">"
            + "Return to Home page</A></P>");
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
