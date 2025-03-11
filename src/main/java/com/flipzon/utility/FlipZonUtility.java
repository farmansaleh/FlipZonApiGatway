package com.flipzon.utility;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.flipzon.dto.Constant;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author Farman Saleh
 * @since 13/01/2024
 *
 */

@Component
public interface FlipZonUtility {

	//return status message
	public static String getStatusMessage(int status) {
		if(Constant.ACTIVE == status) {
			return "Activated";
		}else {
			return "De-Activated";
		}
	}
	
	public static long getCurrentUserId() {
		return getCurrentUserIdFromReq(getCurrentHttpRequest());
	}
	
	public static HttpServletRequest getCurrentHttpRequest(){
	    RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
	    if (requestAttributes instanceof ServletRequestAttributes) {
	        HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
	        return request;
	    }
	    return null;
	}
	
	public static long getCurrentUserIdFromReq(HttpServletRequest req) {
		Object userId = req.getAttribute("userId");
		return Long.valueOf(isNull(userId)?"0":userId.toString());
	}
	
	public static boolean isNull(Object value) {
		return value == null;
	}
}
