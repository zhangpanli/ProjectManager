package com.self.projectmanager.model;

import java.sql.Types;

import com.self.projectmanager.annotation.Column;
import com.self.projectmanager.annotation.Table;

@Table(name = "t_member")
public class Member implements Identifiable {

    private Integer memberId;
    private String memberName;
    private String memberNamePinyin;
    private String comment;
    private String status;

    private Integer projectId;

    @Column(name = "member_id", type = Types.INTEGER)
    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    @Column(name = "member_name")
    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    @Column(name = "member_name_pinyin")
    public String getMemberNamePinyin() {
        return memberNamePinyin;
    }

    public void setMemberNamePinyin(String memberNamePinyin) {
        this.memberNamePinyin = memberNamePinyin;
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

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @Override
    public Object getId() {
        return memberId;
    }
}
