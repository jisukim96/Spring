package com.springlab.Test;

import java.sql.Connection;

import com.springlab.common.JDBCUtil;

public class Conn_Test {

	public static void main(String[] args) {

		Connection conn = null;
		conn = JDBCUtil.getConnection();
	}

}
