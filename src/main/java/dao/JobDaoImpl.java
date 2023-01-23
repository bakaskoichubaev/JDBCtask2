package dao;


import config.Util;
import model.Job;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JobDaoImpl implements JobDao {
    Connection connection = Util.getConnection();

    public void createJobTable() {
        String sql = """
                create table jobs(
                id serial primary key ,
                position varchar,
                profession varchar,
                description varchar,
                experience int);       
                       """;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Successfully created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addJob(Job job) {
        String sql = """
                insert into jobs(position,profession,description,experience)values (?,?,?,?);
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, job.getPosition());
            preparedStatement.setString(2, job.getProfession());
            preparedStatement.setString(3, job.getDescription());
            preparedStatement.setInt(4, job.getExperience());
            preparedStatement.executeUpdate();
            System.out.println("Successfully added");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Job getJobById(Long jobId) {
        String sql = """
                select * from jobs where id=?;
                """;
        Job job = new Job();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, jobId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                job = new Job(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getInt(5));
            }
            resultSet.close();
            return job;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Job> sortByExperience(String ascOrDesc) {
        List<Job> jobs = new ArrayList<>();
        String sql1 = "select * from jobs order by experience";
        String sql2 = "select * from jobs order by experience desc";
        String sql = "";
        if (ascOrDesc.toLowerCase().equals("asc")) sql = sql1;
        else if (ascOrDesc.toLowerCase().equals("desc")) sql = sql2;

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                jobs.add(new Job(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getInt(5)));
            }
            return jobs;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Job getJobByEmployeeId(Long employeeId) {
        Job job = new Job();
        String sql = """
        select * from jobs join employees e on e.jobId = jobs.id where e.id = ?;
        """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setLong(1,employeeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                job = new Job(resultSet.getLong(1),resultSet.getString(2),
                        resultSet.getString(3),resultSet.getString(4),
                        resultSet.getInt(5));
            }
            return job;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void deleteDescriptionColumn() {
        String sql = """
                      alter table  jobs drop column description;
                """;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Column successfully deleted");
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}