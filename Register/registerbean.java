package CarnivAPP.Registration;
 
public class RegisterBean {
 
 private String FullName;
 private String Email;
 private String UserName;
 private String Password;
 
 public String getUserName() {
 return UserName;
 }
 public void setUserName(String UserName) {
 this.UserName = UserName;
 }
 public String getPassword() {
 return Password;
 }
 public void setPassword(String Password) {
 this.Password = Password;
 }
 public void setFullName(String FullName) {
 this.FullName = FullName;
 }
 public String getFullName() {
 return FullName;
 }
 public void setEmail(String Email) {
 this.Email = Email;
 }
 public String getEmail() {
 return Email;
 }