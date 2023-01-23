package service;

import dao.JobDao;
import dao.JobDaoImpl;
import model.Job;

import java.util.List;

public class JobServiceImpl implements JobService{
    JobDao jobDao = new JobDaoImpl();

    public void createJobTable() {
        jobDao.createJobTable();
    }

    public void addJob(Job job) {
        jobDao.addJob(job);
    }

    public Job getJobById(Long jobId) {
        return jobDao.getJobById(jobId);
    }

    public List<Job> sortByExperience(String ascOrDesc) {
        return jobDao.sortByExperience(ascOrDesc);
    }

    public Job getJobByEmployeeId(Long employeeId) {
        return jobDao.getJobByEmployeeId(employeeId);
    }

    public void deleteDescriptionColumn() {
        jobDao.deleteDescriptionColumn();
    }
}
