package Services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import Model.Login;
import Model.Response;
import connectDB.ConnectDB;
import connectDB.LoginJDBC;

public class LoginService { // จัดการข้อมูลให้เรียบร้อย (วืเคราะห์, จัดการเงื่อนไข, logic business)
							// ****************************
	@Autowired
	public ConnectDB conn;
	@Autowired
	public LoginJDBC logJDBC;

	final static Logger logger = Logger.getLogger(LoginService.class);

	public Response checkLogin(String user, String pass) {
		logger.info("Entering the Execute method");
		Response res = null;
		Login rec = null;
		try {
			rec = logJDBC.getLoingUser(user, pass);
			res = new Response();
			System.out.println("Check login"); //*** check code ***
		} catch (Exception e) {
			//logger.error("Error while login. Message: " + e.getMessage());
		}
		if (rec.getUsername() != null && rec.getPassword() != null) {
			res.setCode("200");
			res.setResult(rec);
			logger.info("Save successful " + rec.toString());
			return res;

			// bad code res.setResult("{id: " + rec.getId() + ", username: " +
			// rec.getUsername() + ", password: " + rec.getPassword() + "}");

		} else {
			res.setCode("404");
			res.setResult("Not Found Username");
			logger.warn("Not Found User in database");
			logger.error("test error");
			return res;
		}
	}

	public Response insertUser(Login login) {
		int status = logJDBC.insertUser(login.getUsername(), login.getPassword());
		Response res = new Response();
		Login rec = null;
		if (status == 0) {
			res.setCode("404");
			res.setResult(status); // *** if status == 0 insertUser fail status == 0 ***
		} else {
			res.setCode("200"); // *** if status > 0 insertUser success status == fill that insert ***
			try {
				
				rec = logJDBC.getLoingUser(login.getUsername(), login.getPassword());
				System.out.println("Check login"); //*** check code ***                                     
				System.out.println(rec.getPath());
				res.setResult(rec);
			} catch (Exception e) {
				//logger.error("Error while login. Message: " + e.getMessage());
			}
		}
		return res;
	}

	public Response updateUser(Login login) {
		int status = logJDBC.updateUser(login.getId(), login.getUsername(), login.getPassword());
		Response res = new Response();
		if (status == 0) {
			res.setCode("404");
			res.setResult(status); // *** if status == 0 updateUser fail status == 0 ***
		} else {
			res.setCode("200"); // *** if status > 0 updateUser success status == fill that update ***
			res.setResult(status);
		}
		return res;
	}

	public Response deleteUser(Login login) {
		int status = logJDBC.deleteUser(login.getId(), login.getUsername(), login.getPassword());
		Response res = new Response();
		if (status == 0) {
			res.setCode("404");
			res.setResult(status); // *** if status == 0 deleteUser fail status == 0 ***
		} else {
			res.setCode("200"); // *** if status > 0 deleteUser success status == fill that delete ***
			res.setResult(status);
		}
		return res;
	}

	public Response getAllUser() {
		Response res = new Response();
		List<Login> listlogin = new ArrayList<Login>();
		listlogin = logJDBC.getAllUser();
		if (listlogin != null) {
			res.setCode("200");
			res.setResult(listlogin);
			return res;
		} else {
			res.setCode("404");
			res.setResult("Not Found All User");
			return res;
		}
	}
	public void UploadImage(String path, String id) {
		logJDBC.uploadImage(path, id);
	}
	
	public String loadImage(String id) {
		String path = logJDBC.getImagePath(id);
		return path;
	}
}
