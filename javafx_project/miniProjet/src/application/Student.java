package application;
public class Student {
	   private Long id;
	   private String cne;
	   private String birthDate;
	   private String firstName;
	   private String lastName;
	   private String studentField;
	   private String currentLevel;
	   private String email;
	   
	   public Student() {}

	   public Student(Long id, String cne, String birthDate, String firstName, String lastName, String studentField, String currentLevel, String email) {
	       this.id = id;
	       this.cne = cne;
	       this.birthDate=birthDate;
	       this.firstName = firstName;
	       this.lastName = lastName;
	       this.studentField = studentField;
	       this.currentLevel=currentLevel;
	       this.email = email;
	   }
	   

	   public Long getId() {
	       return id;
	   }

	   public void setId(Long generatedId) {
	       this.id = generatedId;
	   }
	   
	   public String getCne() {
	       return cne;
	   }

	   public void setCne(String cne) {
	       this.cne = cne;
	   }
	   
	   public String getBirthDate() {
	       return birthDate;
	   }

	   public void setBirthDate(String birthDate) {
	       this.birthDate = birthDate;
	   }

	   public String getFirstName() {
	       return firstName;
	   }

	   public void setFirstName(String firstName) {
	       this.firstName = firstName;
	   }

	   public String getLastName() {
	       return lastName;
	   }

	   public void setLastName(String lastName) {
	       this.lastName = lastName;
	   }
	   
	   public String getStudentField() {
	       return studentField;
	   }

	   public void setStudentField(String studentField) {
	       this.studentField = studentField;
	   }
	   
	   public String getCurrentLevel() {
	       return currentLevel;
	   }

	   public void setCurrentLevel(String currentLevel) {
	       this.currentLevel = currentLevel;
	   }
	   
	   public String getEmail() {
	       return email;
	   }

	   public void setEmail(String email) {
	       this.email = email;
	   }

}
