package com.self.projectmanager.model;

import java.sql.Types;
import java.util.Date;

import com.self.projectmanager.annotation.Column;
import com.self.projectmanager.annotation.Table;

@Table(name = "t_project")
public class Project implements Identifiable {

    private Integer projectId;
    private String projectName;
    private Date projectStarttime;
    private Date projectEndtime;
    private String comment;
    private String status;

    private String memberId;

    @Column(name = "project_id", type = Types.INTEGER)
    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @Column(name = "project_name")
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Column(name = "project_starttime", type = Types.DATE)
    public Date getProjectStarttime() {
        return projectStarttime;
    }

    public void setProjectStarttime(Date projectStarttime) {
        this.projectStarttime = projectStarttime;
    }

    @Column(name = "project_endtime", type = Types.DATE)
    public Date getProjectEndtime() {
        return projectEndtime;
    }

    public void setProjectEndtime(Date projectEndtime) {
        this.projectEndtime = projectEndtime;
    }

    @Column(name = "comment")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    @Override
    public Object getId() {
        return projectId;
    }
}
