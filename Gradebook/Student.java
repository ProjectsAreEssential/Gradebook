public class Student {
   
   // Instance fields
   private String name;
   private int grade;
   
   // Constructor
   public Student(String name, int grade) {
      this.name = name;
      this.grade = grade;
   }
   
   // Gets the name
   public String getName() {
      return name;
   }
   
   // Gets the grade
   public int getGrade() {
      return grade;
   }
   
   // Sets the name
   public void setName(String newName) {
      this.name = newName;
   }
   
   // Sets the grade
   public void setGrade(int newGrade) {
      this.grade = newGrade;
   }
   
   // String representation
   @Override
   public String toString() {
      return name + " (Grade: " + grade + ")";
   }
}