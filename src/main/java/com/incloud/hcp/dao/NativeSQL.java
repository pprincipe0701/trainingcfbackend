package com.incloud.hcp.dao;


import com.incloud.hcp.model.JobExecutionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;


@Component
public class NativeSQL {

    Logger logger = LoggerFactory.getLogger(NativeSQL.class);
    private Connection connection = null;
    @Autowired Database db;

    public void createConnection() {

        long startTime = System.currentTimeMillis();


        logger.info("Java version: " + com.sap.db.jdbc.Driver.getJavaVersion());
        logger.info("Minimum supported Java version and SAP driver version number: "
                + com.sap.db.jdbc.Driver.getVersionInfo());

        try {
            connection = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());

            if (connection != null) {

                logger.info("Connection to HANA successful!");
            }

            long endTime = System.currentTimeMillis();
            long executeTime = endTime - startTime;

            logger.info("HANA Connection Time:"  + executeTime);

        } catch (SQLException e) {
            logger.error("Connection Failed:");
            logger.error(e.toString());
            return;
        }
    }

    public void insertJob(JobExecutionStatus job) {
        if (connection != null) {
            try {
                long startTime = System.currentTimeMillis();

                PreparedStatement pstmt = connection.prepareStatement(
                        "INSERT INTO \"ECM_JOB_EXECUTION_STATUS\" (\"JOB_ID\", \"JOB_NAME\", \"RESULT\", \"START_TIME\", \"STATUS\") VALUES (?, ?, ?, ?, ?)");
                pstmt.setNString(1, job.getJobId());
                pstmt.setNString(2, job.getJobName());
                pstmt.setNString(3, job.getResult());
                pstmt.setLong(4, job.getStartTime());
                pstmt.setNString(5, job.getStatus());

                pstmt.executeUpdate();

                long endTime = System.currentTimeMillis();
                long executeTime = endTime - startTime;

                logger.info("Insert Job:" + job.getJobId() + ":" + executeTime);

            } catch (SQLException e) {
                logger.error("Insert failed!");
                logger.error(e.toString());
            }
        }
    }

    public void updateJob(JobExecutionStatus job) {
        if (connection != null) {
            try {
                long startTime = System.currentTimeMillis();

                PreparedStatement pstmt = connection.prepareStatement(
                        "UPDATE \"ECM_JOB_EXECUTION_STATUS\" SET \"RESULT\" = ?, \"STATUS\" = ? WHERE (\"JOB_ID\" = ?)");
                pstmt.setNString(1, job.getResult());
                pstmt.setNString(2, job.getStatus());
                pstmt.setNString(3, job.getJobId());
                pstmt.executeUpdate();

                long endTime = System.currentTimeMillis();
                long executeTime = endTime - startTime;

                logger.info("Update Job:" + job.getJobId() + ":" + executeTime);

            } catch (SQLException e) {
                logger.error("Update failed!");
                logger.error(e.toString());
            }
        }
    }

    public void getJob(String jobId) {
        if (connection != null) {
            try {
                long startTime = System.currentTimeMillis();

                PreparedStatement pstmt = connection.prepareStatement(
                        "SELECT \"JOB_ID\", \"JOB_NAME\", \"RESULT\", \"START_TIME\", \"STATUS\" FROM \"ECM_JOB_EXECUTION_STATUS\" WHERE (\"JOB_ID\" = ?)");
                pstmt.setNString(1, jobId);

                ResultSet rs = pstmt.executeQuery();

                if (rs != null && rs.next()) {
                    String job_id = rs.getString("JOB_ID");
                    String job_name = rs.getString("JOB_Name");
                    long start_time = rs.getLong("START_TIME");
                    String status = rs.getString("STATUS");
                    String result = rs.getString("RESULT");

                    logger.info(job_id + "-" + job_name + "-" + start_time + "-" + status + "-" + result);
                }

                long endTime = System.currentTimeMillis();
                long executeTime = endTime - startTime;

                logger.info("Query Job:" + jobId + ":" + executeTime);

            } catch (SQLException e) {
                logger.error("Query failed!");
                logger.error(e.toString());
            }
        }
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

}
