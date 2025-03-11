package com.flipzon.configuration;

import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.flipzon.dto.ErrorResponse;
import com.flipzon.dto.Violation;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;

/**
 * @author Farman Saleh
 * @since 10/01/2024
 *
 */

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
	
	/*  
		100-level (Informational) – server acknowledges a request
		200-level (Success) – server completed the request as expected
		300-level (Redirection) – client needs to perform further actions to complete the request
		400 Bad Request – client sent an invalid request, such as lacking required request body or parameter
		401 Unauthorized – client failed to authenticate with the server
		403 Forbidden – client authenticated but does not have permission to access the requested resource
		404 Not Found – the requested resource does not exist
		412 Precondition Failed – one or more conditions in the request header fields evaluated to false
		500 Internal Server Error – a generic error occurred on the server
		503 Service Unavailable – the requested service is not available 
	*/
	
	//For Jwt Token Exception
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(AccessDeniedException.class)
	public ErrorResponse accessDeniedException(AccessDeniedException exception){
		return new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "You have no rights to this functionality");
	}
	
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(BadCredentialsException.class)
	public ErrorResponse handleRuntimeEx(BadCredentialsException exception){
		return new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "Incorrect username or password");
	}
	
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(SignatureException.class)
	public ErrorResponse handleSignatureEx(SignatureException exception){
		return new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "JWT Token is invalid");
	}
	
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(ExpiredJwtException.class)
	public ErrorResponse handleExpiredJwtEx(ExpiredJwtException exception){
		return new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "JWT Token is Expired");
	}
	
	//For Field Validation Exception
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ErrorResponse handleMethodArgumentNotValidEx(MethodArgumentNotValidException exception){
		
		if(exception.getFieldErrors().size() > 0) {
			List<Violation> violations = new ArrayList<Violation>();
			exception.getFieldErrors().forEach(field->{
				violations.add(new Violation(field.getField(), field.getDefaultMessage()));
			});
			return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Validation failed", violations);
		}else {
			return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
		}
	}
	
	// For Mongo DB - Database Exception
	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler(DuplicateKeyException.class)
	public ErrorResponse handleDuplicateKeyException(DuplicateKeyException exception){
		String exMsg = exception.getMessage();
		String field = exMsg.substring(exMsg.indexOf("key: {")+7, exMsg.indexOf("}',")).split(":")[0];
		return new ErrorResponse(HttpStatus.CONFLICT.value(),field+" already exists");
	}
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(SQLSyntaxErrorException.class)
	public ErrorResponse handleSqlSyntaxErrorEx(SQLSyntaxErrorException exception){
		return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
	}
	
	//For All/Any Exception which is not handled by above
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({RuntimeException.class,Exception.class})
	public ErrorResponse handleAllException(Exception exception) {
		return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
	}
	
}
