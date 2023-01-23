package dao;
import config.Util;
import model.Employee;
import model.Job;

import java.sql.*;
import java.util.*;

public class EmployeeDaoImpl implements EmployeeDao {
    Connection connection = Util.getConnection();

    public void createEmployee() {
        String sql = """
                create table employees(
                id serial primary key,
                first_name varchar not null ,
                last_name varchar,
                age int check ( age<100 or age>16),
                email varchar unique ,
                jobId int references jobs(id));
                 """;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Successfully created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addEmployee(Employee employee) {
        String sql = """
                insert into employees(first_name,last_name,age,email,jobId)values (?,?,?,?,?);
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setInt(3, employee.getAge());
            preparedStatement.setString(4, employee.getEmail());
            preparedStatement.setInt(5, employee.getJobId());
            preparedStatement.executeUpdate();
            System.out.println("Successfully added");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void dropTable() {
        String sql = """
                drop table employees;
                """;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Table successfully deleted");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void cleanTable() {
        String sql = """
                truncate table employees
                """;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Successfully cleaned");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateEmployee(Long id, Employee employee) {
        String sql = """
                update employees set first_name=?,
                last_name = ?,
                age=?,
                email=?,
                jobId=? where id=?;
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setInt(3, employee.getAge());
            preparedStatement.setString(4, employee.getEmail());
            preparedStatement.setInt(5, employee.getJobId());
            preparedStatement.setLong(6, id);
            preparedStatement.executeUpdate();
            System.out.println("Successfully updated");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = """
                select * from employees ;
                """;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                employees.add(new Employee(resultSet.getLong(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getInt(4),
                        resultSet.getString(5), resultSet.getInt(6)));
            }
            return employees;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Employee findByEmail(String email) {
        Employee employee = new Employee();
        String sql = """
                select * from employees where email=?;
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                employee = new Employee(resultSet.getLong(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getInt(4),
                        resultSet.getString(5), resultSet.getInt(6));
            }
            return employee;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Map<Employee, Job> getEmployeeById(Long employeeId) {
        Map<Employee, Job> employeeJobMap = new LinkedHashMap<>();
        String sql = """
                select * from employees join jobs j on employees.jobId = j.id where employees.id=?;
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, employeeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                employeeJobMap.put(new Employee(resultSet.getLong(1), resultSet.getString(2),
                                resultSet.getString(3), resultSet.getInt(4),
                                resultSet.getString(5), resultSet.getInt(6)),
                        new Job(resultSet.getLong(7), resultSet.getString(8),
                                resultSet.getString(9), resultSet.getInt(10)));
            }
            return employeeJobMap;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Employee> getEmployeeByPosition(String position) {
        List<Employee> employees = new ArrayList<>();
        String sql = """
                select * from employees join jobs j on employees.jobid = j.id where j.position = ?;
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1,position);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                employees.add(new Employee(resultSet.getLong(1),resultSet.getString(2),
                        resultSet.getString(3),resultSet.getInt(4),
                        resultSet.getString(5),resultSet.getInt(6)));
            }
            return employees;
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}