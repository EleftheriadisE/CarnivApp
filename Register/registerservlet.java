package CarnivAPP.Registration;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import CarnivAPP.Registration.RegisterBean;
import CarnivAPP.Registration.RegisterDao;
 
public class RegisterServlet extends HttpServlet {
 
     public RegisterServlet() {
     }
 
     protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	 //Αντιγραφή όλων των παραμέτρων σε τοπικές μεταβλητές.
         String FullName = request.getParameter("Πλήρες Όνομα");
         String Email = request.getParameter("E-mail");
         String UserName = request.getParameter("Όνομα Χρήστη");
         String Password = request.getParameter("Κωδικός Χρήστη");
         
         RegisterBean registerBean = new RegisterBean();
         registerBean.setFullName(FullName);
         registerBean.setEmail(Email);
         registerBean.setUserName(UserName);
         registerBean.setPassword(Password); 
         
         RegisterDao registerDao = new RegisterDao();
         
        //Εισαγωγή των στοιχείων του χρήστη στη βάση δεδομένων.
         String userRegistered = registerDao.registerUser(registerBean);
         
         if(userRegistered.equals("Επιτυχία"))
         {
            request.getRequestDispatcher("/Home.jsp").forward(request, response);
         }
         else
         {
            request.setAttribute("Σφάλμα", userRegistered);
            request.getRequestDispatcher("/Register.jsp").forward(request, response);
         }
     }
}