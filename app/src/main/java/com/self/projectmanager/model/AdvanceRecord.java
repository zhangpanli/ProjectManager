package com.self.projectmanager.model;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.Date;

import com.self.projectmanager.annotation.Column;
import com.self.projectmanager.annotation.Table;

@Table(name = "t_advance_record")
public class AdvanceRecord {

	private Integer memberId;
	private Integer projectId;
	private Date advanceDate;
	private BigDecimal balance;
	private String comment;
	private String status;

	private Date starttime;
	private Date endtime;

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

	@Column(name = "advance_date", type = Types.DATE)
	public Date getAdvanceDate() {
		return advanceDate;
	}

	public void setAdvanceDate(Date advanceDate) {
		this.advanceDate = advanceDate;
	}

	@Column(name = "balance", type = Types.DECIMAL)
	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
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

	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

}
