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
@WebServlet(name = "EnrolmentsEntityServlet", urlPatterns = 
{
    "/EnrolmentsEntityServlet"
}, initParams = 
{
    @WebInitParam(name = "dbTable", value = "Enrollments"),
    @WebInitParam(name = "dbIDAtt", value = "S_Id"),
})

public class EnrolmentsServlet extends HttpServlet
{
    private final char QUOTE = '"';
    private Logger logger;
    private String sqlCommand;

    @Resource(mappedName = "jdbc/SQLUniversityDB")
    private DataSource dataSource;
            
    public EnrolmentsServlet()
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
       sqlCommand = "SELECT * FROM "+dbTable+" WHERE "+dbIDAtt+" LIKE ?";
    }
    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response) 
            throws ServletException, IOException
    {      
        HttpSession session = request.getSession();
        CurrentUser user = (CurrentUser) session.getAttribute("CurrentUser");
       
        String studentID = request.getParameter("studentID");
        if (studentID == null || studentID.length() == 0)
           studentID = "%";
        
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
               prepStmt.setString(1, studentID);
               resultSet = prepStmt.executeQuery();
               logger.info("Success!");
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
               out.println("<tr><td><b>Student ID</b></td>" + "<td><b>CCN</b></td>"  + "<td><b>Semester</b></td>"
                       + "<td><b>Year</b></td>" + "<td><b>Grade</b></td></tr>");
               try
               {
                  while (resultSet.next())
                  {
                     String id = resultSet.getString("S_Id");
                     int ccn = resultSet.getInt("CCN");
                     int semester = resultSet.getInt("Semester");
                     int year = resultSet.getInt("Year");
                     String grade = resultSet.getString("Grade");

                     out.println("<TR><TD>" + id + "</TD><TD>" + ccn + "</TD><TD>" + semester + "</TD><TD>" + year
                                 + "</TD><TD>" + grade + "</TD></TR>");
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
