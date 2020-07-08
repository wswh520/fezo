package com.fz.enroll.login.service;

import com.fz.common.res.Response;

public interface SignInService {

	public Response signInService(String name,String username,String password);
}
