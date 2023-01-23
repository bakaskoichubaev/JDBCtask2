package org.example;

import model.Employee;
import model.Job;
import service.EmployeeService;
import service.EmployeeServiceImpl;
import service.JobService;
import service.JobServiceImpl;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{static JobService jobService = new JobServiceImpl();
    static EmployeeService employeeService = new EmployeeServiceImpl();
    public static void main( String[] args )
    {



        try {
            while (true) {
                System.out.println("""
                        1 Create table job
                        2 Add job   
                        3 Find job by ID
                        4 Sort by experience
                        5 Get job by employee id
                        6 Delete column description
                        7 Create table employee
                        8 Add employee
                        9 Delete table employee
                        10 Clean table employee
                        11 Update employee
                        12 Get all employee
                        13 Find by email
                        14 Find by ID                 
                        15 Get employee by position
                            """);
                int i = new Scanner(System.in).nextInt();
                switch (i) {
                    case 1 -> jobService.createJobTable();
                    case 2 -> {
                        System.out.println("Enter position: ");
                        String pos = new Scanner(System.in).nextLine();
                        System.out.println("Enter profession");
                        String pros = new Scanner(System.in).nextLine();
                        System.out.println("Enter description: ");
                        String des = new Scanner(System.in).nextLine();
                        System.out.println("Enter experience: ");
                        int exp = new Scanner(System.in).nextInt();
                        jobService.addJob(new Job(pos, pros, des, exp));
                    }
                    case 3 -> {
                        System.out.println("Enter ID: ");
                        Long jobId = new Scanner(System.in).nextLong();
                        System.out.println(jobService.getJobById(jobId));
                    }
                    case 4 -> {
                        System.out.println("Ascending or descending (asc/desc)");
                        String asOrDesc = new Scanner(System.in).nextLine();
                        System.out.println(jobService.sortByExperience(asOrDesc));
                    }
                    case 5 -> {
                        System.out.println("Enter employee id: ");
                        Long id = new Scanner(System.in).nextLong();
                        System.out.println(jobService.getJobByEmployeeId(id));
                    }
                    case 6 -> jobService.deleteDescriptionColumn();
                    case 7 -> employeeService.createEmployee();
                    case 8 -> {
                        employeeService.addEmployee(scan());
                    }
                    case 9 -> employeeService.dropTable();
                    case 10 -> employeeService.cleanTable();
                    case 11 -> {
                        System.out.println("The id you want to change: ");
                        Long empId = new Scanner(System.in).nextLong();
                        employeeService.updateEmployee(empId, scan());
                    }
                    case 12 -> System.out.println(employeeService.getAllEmployees());
                    case 13 -> {
                        System.out.println("Enter email: ");
                        String email = new Scanner(System.in).nextLine();
                        System.out.println(employeeService.findByEmail(email));
                    }
                    case 14 -> {
                        System.out.println("Enter employee ID: ");
                        Long id = new Scanner(System.in).nextLong();
                        System.out.println(employeeService.getEmployeeById(id));
                    }
                    case 15 -> {
                        System.out.println("Enter position employee: ");
                        String position = new Scanner(System.in).nextLine();
                        System.out.println(employeeService.getEmployeeByPosition(position));
                    }
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }


    }

        public static Employee scan() {
        try {
            System.out.println("Enter first name: ");
            String firstName = new Scanner(System.in).nextLine();
            System.out.println("Enter last name: ");
            String last = new Scanner(System.in).nextLine();
            System.out.println("Enter age: ");
            int age = new Scanner(System.in).nextInt();
            System.out.println("Enter email");
            String email = new Scanner(System.in).nextLine();
            System.out.println("Enter job id: ");
            int jobId = new Scanner(System.in).nextInt();
            Employee employee = new Employee(firstName, last, age, email, jobId);
            return employee;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
