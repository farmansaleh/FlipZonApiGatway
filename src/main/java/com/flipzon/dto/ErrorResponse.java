package com.flipzon.dto;

/**
 * @author Farman Saleh
 * @since 07/02/2024
 *
 */

public class ErrorResponse {
	
	private int status;
	private String error;
	//details like on which field violation exception occured
	private Object details;

	public ErrorResponse() {
		super();
	}

	public ErrorResponse(int status, String error) {
		this.status = status;
		this.error = error;
	}
	
	public ErrorResponse(int status, String error, Object details) {
		super();
		this.status = status;
		this.error = error;
		this.details = details;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Object getDetails() {
		return details;
	}

	public void setDetails(Object details) {
		this.details = details;
	}

}
