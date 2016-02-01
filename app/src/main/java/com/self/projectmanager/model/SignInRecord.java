package com.self.projectmanager.model;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.Date;

import com.self.projectmanager.annotation.Column;
import com.self.projectmanager.annotation.Table;

@Table(name = "t_sign_in_record")
public class SignInRecord {

	private Integer signInId;
	private Integer memberId;
	private Integer projectId;
	private Date signInDate;
	private BigDecimal amount;
	private String comment;
	private String status;

	private Date starttime;
	private Date endtime;

	@Column(name = "sign_in_id", type = Types.INTEGER)
	public Integer getSignInId() {
		return signInId;
	}

	public void setSignInId(Integer signInId) {
		this.signInId = signInId;
	}

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

	@Column(name = "sign_in_date", type = Types.DATE)
	public Date getSignInDate() {
		return signInDate;
	}

	public void setSignInDate(Date signInDate) {
		this.signInDate = signInDate;
	}

	@Column(name = "amount", type = Types.DECIMAL)
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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
