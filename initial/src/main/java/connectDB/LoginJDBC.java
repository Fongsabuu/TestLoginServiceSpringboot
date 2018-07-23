package connectDB;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import Model.Login;

public class LoginJDBC { // sql ดึงข้อมูลส่งกลับ ********************************
	@Autowired
	JdbcTemplate jdbcTemplate;

	public Login getLoingUser(String user, String pass) {
		String sql = "SELECT * FROM users Where username='" + user + "'" + "AND password='" + pass + "'";
		try {
			Login logins = (Login) jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper(Login.class));
			return logins;
		} catch (Exception e) {
			return new Login();
		}
	}

	public int insertUser(String user, String pass) {
		String sql = "insert into users (username,password) Select '" + user + "','" + pass + "' Where not exists(select * from users where username='" + user + "' )";
		if(user == "TT" && pass == "TT") {
			sql = "insert into users (id,username,password) Select 3  ,'" + user + "','" + pass + "' Where not exists(select * from users where username='" + user + "' )";
			System.out.println(sql);
		}
		System.out.println(sql);
		int status = jdbcTemplate.update(sql);
		return status;
	}

	public int updateUser(Login login) {
		String sql = "UPDATE users SET username='" + login.getUsername() + "', password='" + login.getPassword() + "', name='" + login.getName() +"', birthday='" + login.getBirthday() + "' WHERE id='" + login.getId() + "'";
		int status = jdbcTemplate.update(sql);
		if(login.getId() == 3) {
		System.out.println("fail");
		System.out.println(sql);
		System.out.println(status); }
		else {
			System.out.println("success");
			System.out.println(sql);
			System.out.println(status);
		}
		return status;
	}

	public int deleteUser(int id, String user, String pass) {
		String sql = "DELETE FROM users Where username='" + user + "'";
		int status = jdbcTemplate.update(sql);
		return status;
	}

	public List<Login> getAllUser() {
		List<Login> listlogin = new ArrayList<Login>();
		String sql = "SELECT * FROM users";
		listlogin = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Login>(Login.class));
		return listlogin;

	}

	public void uploadImage(String path, String id) {
		String sql = "UPDATE users SET path = '" + path + "' WHERE id = '" + id + "'";
		jdbcTemplate.update(sql);
		System.out.println("test Upload!!!");
	}

	public String getImagePath(String id) {
		String sql = "Select path FROM users Where id ='" + id + "'";
		String path = jdbcTemplate.queryForObject(sql, String.class);
		return path;
	}
	
	public int getCountUser() {
		String sql = "SELECT COUNT(*) FROM users";
		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		return count;
	}
	
	public List<Login> getLazyloadGetUser(String first, String rows) {
		List<Login> listlogin = new ArrayList<Login>();
		String sql = "SELECT * FROM users LIMIT " + first + "," + rows;
		listlogin = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Login>(Login.class));
		return listlogin;

	}
}
