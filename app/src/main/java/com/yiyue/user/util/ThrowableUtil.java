package com.yiyue.user.util;

public class ThrowableUtil {

	public static String getMessage(Throwable throwable) {
		return throwable == null ? "" : throwable.getMessage();
	}
}