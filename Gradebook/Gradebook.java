import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Gradebook {
   
   // Main method
   public static void main(String[] args) {
      
      // Create a scanner for console input
      Scanner input = new Scanner(System.in);
      
      // Create empty list of students
      ArrayList<Student> students = null;
      
      // Ask for the filename
      while (students == null) {
         System.out.print("What is the name of the file: ");
         String filename = input.nextLine();
         
         try {
            students = loadStudents(filename);
         } catch (FileNotFoundException e) {
            System.out.println("File not found.");
         }
      }
      
      // Runs the menu
      runMenu(students, input);
      
      // Close the console scanner
      input.close();
   }
   
   // Loads students from a file
   public static ArrayList<Student> loadStudents(String filename) throws FileNotFoundException {
     
      // Create empty arraylist to store the students
      ArrayList<Student> students = new ArrayList<>();
      
      // Create scanner to read the lines of the file
      Scanner fileReader = new Scanner(new File(filename));
      
      // Loop through the file while there is a next line
      while (fileReader.hasNextLine()) {
         String line = fileReader.nextLine();
         
         // Parse the lines by commas
         String[] parts = line.split(",");
         
         String name = parts[0].trim();
         int grade = Integer.parseInt(parts[1].trim());
         
         // Add the student to the arraylist
         students.add(new Student(name, grade));
      }
      
      // Close the file scanner
      fileReader.close();
      
      // Return the list
      return students;
   }
   
   // Runs the menu
   public static void runMenu(ArrayList<Student> students, Scanner input) {
      int choice = 0;
      
      // Chooses action depending on choice
      while (choice != 6) {
         
         // Prints the menu
         printMenu();
         
         // Gets the users choice
         while (true) {
            System.out.print("Enter choice: ");
         
            if (input.hasNextInt()) {
               choice = input.nextInt();
               input.nextLine(); // Clear buffer
               break;
            } else {
               System.out.println("Invalid input. Enter a number.");
               input.next(); // Discard bad input
            }
         }
         
         // Different choices
         switch (choice) {
            case 1:
               showStudents(students);
               break;
            case 2:
               searchStudent(students, input);
               break;
            case 3:
               showStats(students);
               break;
            case 4:
               addStudent(students, input);
               break;
            case 5:
               removeStudent(students, input);
               break;
            case 6:
               saveAndQuit(students, input);
               break;
            default:
               System.out.println("Invalid input");
         }
      }
   }
   
   // Prints the menu
   public static void printMenu() {
      System.out.println();
      System.out.println("1. Show students");
      System.out.println("2. Find student");
      System.out.println("3. Show stats");
      System.out.println("4. Add student");
      System.out.println("5. Remove student");
      System.out.println("6. Save and quit");
     // System.out.print("Enter choice: ");
   }
     
   // Shows the students
   public static void showStudents(ArrayList<Student> students) {
      if (!students.isEmpty()) {
         for (Student s : students) {
            System.out.println(s);
         }
      } else {
         System.out.println("There are no students");
      }
   }
   
   // Search for a student
   public static void searchStudent(ArrayList<Student> students, Scanner input) {
      
      // Ask user for student name
      System.out.print("Enter name: ");
      String name = input.nextLine();
      
      // Stores the student
      Student result = findStudent(students, name);
      
      // Checks if the student exists
      if (result != null) {
         System.out.println("Student found: " + result);
      } else {
         System.out.println("Student not found.");
      }
   }
   
   // Finds the student
   public static Student findStudent(ArrayList<Student> students, String name) {
      
      // Finds the student in the list
      for (Student s : students) {
         if (s.getName().equalsIgnoreCase(name)) {
            return s;
         }
      }
      
      // Return null if the student isnt found
      return null;
   }
   
   // Show stats
   public static void showStats(ArrayList<Student> students) {
      if (!students.isEmpty()) {
         System.out.printf("There are %d students.%n", students.size());
         System.out.printf("The average grade is %.3f.%n", averageGrade(students));
         System.out.printf("The student with the largest grade is %s%n", highestGrade(students));
         System.out.printf("There are %d students with less than a 60.%n", lessThan60(students));
      } else {
         System.out.println("Stats are unavailable.");
      }
   }
   
   // Add student
   public static void addStudent(ArrayList<Student> students, Scanner input) {
      
      // Ask user for student name
      System.out.print("Enter name: ");
      String name = input.nextLine();
      
      // Checks to see if student exists
      Student exist = findStudent(students, name);
      while (exist != null) {
         System.out.println("Student already exists");
         
         // Keep asking until unique name
         System.out.print("Enter name: ");
         name = input.nextLine();
         
         exist = findStudent(students, name);
      }
      
      // Ask for the students grade
      System.out.print("Enter grade: ");
      
      // Declare grade variable
      int grade = -1;
      
      // Keeps asking until valid integer
      while (grade < 0) {
         // Verify the input is a integer
         while (!input.hasNextInt()) {
            System.out.println("Invalid grade. Try again.");
            input.next();
            
            System.out.print("Enter grade: ");
         }
         
         // Gets the grade
         grade = input.nextInt();
         
         // Make sure the input is > 0
         if (grade < 0) {
            System.out.println("Invalid grade. Try again.");
            
            System.out.print("Enter grade: ");
         }
      }
     
      // Consume leftover line buffer
      input.nextLine();
      
      students.add(new Student(name, grade));
      System.out.println(name + " added to the gradebook.");
   }
   
   // Remove student
   public static void removeStudent(ArrayList<Student> students, Scanner input) {
      System.out.print("Enter name: ");
      String name = input.nextLine();
      
      // Checker
      Student found = findStudent(students, name);
      
      // Checks to see if the student exists
      if (found == null) {
         System.out.println("Student not found");
      } else {
         students.remove(found);
         System.out.println("Student has been removed.");
      }
   }
   
   // Save and quit
   public static void saveAndQuit(ArrayList<Student> students, Scanner input) {
      
      // Ask for output file name
      System.out.print("Enter output filename: ");
      String filename = input.nextLine();
      
      
      try {
         PrintStream output = new PrintStream(new File(filename));
         
         for (Student s : students) {
            output.println(s.getName() + "," + s.getGrade());
         }
         
         // Close the output
         output.close();
         
         System.out.println("Students saved successfully.");
      } catch (FileNotFoundException e) {
         System.out.println("Error: could not save file.");
      }
      
      System.out.println("Goodbye.");
   }
   
   // Finds the average grade 
   public static double averageGrade(ArrayList<Student> students) {
      int sum = 0;
      
      // Go through the list and add up the grades
      for (Student s : students) {
         sum += s.getGrade();
      }
      
      return (double) sum / students.size();
   }
   
   // Finds the student with the highest grade
   public static Student highestGrade(ArrayList<Student> students) {
      if (students.isEmpty()) return null;
      
      Student highest = students.get(0);
      
      // Go through the list comparing each time
      for (Student s : students) {
         if (s.getGrade() > highest.getGrade()) {
            highest = s;
         }    
      }     
      return highest;
   }
   
   // Finds how many students have a grade less than 60
   public static int lessThan60(ArrayList<Student> students) {
      int count = 0;
      
      // Go through list
      for (Student s : students) {
         if (s.getGrade() < 60) {
            count++;
         }
      }
      
      return count;
   }
}