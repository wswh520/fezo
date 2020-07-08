package com.fz.common.security;

import org.springframework.util.Assert;

final class ThreadLocalUser {
	private static final ThreadLocal<CurrentUser> holder = new ThreadLocal<CurrentUser>();

    public static void clearUser() {
    	holder.set(null);
    }

	public static CurrentUser getCurrentUser() {
    	CurrentUser user = holder.get();
    	return user;
    }

    public static void setCurrentUser(CurrentUser user) {
        Assert.notNull(user, "Only non-null CurrentUser instances are permitted");
        holder.set(user);
    }
}
