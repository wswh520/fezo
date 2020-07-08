package com.fz.common.security;


public class ThreadLocalUtils {

	@SuppressWarnings("unchecked")
	public static <T extends CurrentUser> T getCurrentUser(){
    	CurrentUser user = ThreadLocalUser.getCurrentUser();
    	if(user!=null){
    		return (T)user;
    	}
        return null;
	}
	
	public static String getName(){
		CurrentUser user = ThreadLocalUser.getCurrentUser();
    	if(user!=null){
    		return user.getName();
    	}
		return null;
	}

    public static void setCurrentUser(CurrentUser user) {
    	ThreadLocalUser.setCurrentUser(user);
    }
    
    public static void clearUser(){
    	ThreadLocalUser.clearUser();
    }
}
