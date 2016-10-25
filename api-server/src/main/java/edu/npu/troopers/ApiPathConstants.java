/*
 * Copyright (C) 2015 Stealth Security, Inc - All Rights Reserved
 * 
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * 
 * Abraham Jeevagunta, September 2015
*/
package edu.npu.troopers;

public interface ApiPathConstants {
	public static String BASE = "/api";
	public static String EMPTY = "";
	
	//Auth
	public static String AUTH_BASE = BASE + "/auth";
	public static String AUTH_LOGIN = "/login";
	public static String AUTH_LOGOUT = "/logout";
	public static String AUTH_PING = "/ping";
	public static String AUTH_SIGNUP = "/signup";
	public static String AUTH_ACTIVATE = "/activate";
	
    //User Management
    public static String USER_BASE = BASE +"/user";
    public static String USER_ID = "/{email:.+}";
    public static String USER_ACTIVATE = "/{email:.+}/{token}";
    public static String USER_ENBALE = "/{email:.+}/enable";
    public static String USER_CHPWD = "/changepassword";
    public static String USER_RESET_PASSWD = "/{email:.+}/resetpassword";
    
}
