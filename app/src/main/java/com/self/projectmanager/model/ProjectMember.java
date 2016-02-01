package com.self.projectmanager.model;

import java.sql.Types;
import java.util.Date;

import com.self.projectmanager.annotation.Column;
import com.self.projectmanager.annotation.Table;

@Table(name = "t_project_member")
public class ProjectMember {

	private Integer memberId;
	private Integer projectId;
	private Date inDate;
	private Date outDate;
	private String comment;
	private String status;

	@Column(name = "member_id", type = Types.INTEGER)
	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	@Column(name = "project_id", type = Types.INTEGER)
	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	@Column(name = "in_date", type = Types.DATE)
	public Date getInDate() {
		return inDate;
	}

	public void setInDate(Date inDate) {
		this.inDate = inDate;
	}

	@Column(name = "out_date", type = Types.DATE)
	public Date getOutDate() {
		return outDate;
	}

	public void setOutDate(Date outDate) {
		this.outDate = outDate;
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

}
