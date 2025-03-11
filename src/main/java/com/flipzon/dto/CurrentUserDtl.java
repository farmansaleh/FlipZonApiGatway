package com.flipzon.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.flipzon.utility.FlipZonUtility;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotNull;

@MappedSuperclass
public class CurrentUserDtl {
	
	@NotNull
	@Column(name = "created_by", insertable = true, updatable = false)
	private long createdBy;
	
	@NotNull
	@JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss")
	@Column(name = "created_date" ,insertable = true, updatable = false)
	private Timestamp createdDate;
	
	@NotNull
	private int status;
	
	@PrePersist
	public void onSubmit() {
		this.createdBy = FlipZonUtility.getCurrentUserId();
		this.createdDate = new Timestamp(System.currentTimeMillis());
		this.status = Constant.ACTIVE;
	}

	public long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
