package Controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import Model.Login;
import Model.Response;
import Services.LoginService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class HelloController { // จัดการข้อมูลเพื่อเตรียมส่งค่ากลับ *************************
	@Autowired
	public LoginService loginn;

	// ** final static Logger logger = Logger.getLogger(HelloController.class);

	@RequestMapping("/")
	public String index() {
		return "Hello Tee";
	}

	@CrossOrigin("http://localhost:4200") // *** Connect Login ***
	@PostMapping("/login")
	public Response loginCheck(@RequestBody Login login) {
		// ** logger.info("Entering the Execute method");
		Response res = new Response();
		res = loginn.checkLogin(login.getUsername(), login.getPassword());
		// ** logger.info("Save successful " + login.toString());
		return res;
	}

	@CrossOrigin("http://localhost:4200") // *** Connect Login Insert *** REGISTER!!!
	@PostMapping("/insertlogin")
	public Response longinInsert(@RequestBody Login login) {
		Response res = new Response();
		res = loginn.insertUser(login);
		return res;
	}

	@CrossOrigin("http://localhost:4200") // *** Connect Login Update***
	@PostMapping("/updatelogin")
	public Response longinUpdate(@RequestBody Login login) {
		Response res = new Response();
		res = loginn.updateUser(login);
		return res;
	}

	@CrossOrigin("http://localhost:4200") // *** Connect Login Delete***
	@PostMapping("/deletelogin")
	public Response longinDelete(@RequestBody Login login) {
		Response res = new Response();
		res = loginn.deleteUser(login);
		return res;
	}

	@CrossOrigin("http://localhost:4200") // *** Get all User***
	@GetMapping("/getalluser")
	public Response getalluser() {
		Response res = new Response();
		res = loginn.getAllUser();
		return res;
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/uploadimage")
	public ResponseEntity<Object> uploadImage(@RequestParam("myFile") MultipartFile image,
			@RequestParam(value = "id") String id) throws IOException {
		// log.info("upload file");
		String path = "";
		try {
			System.out.println("LLLL");
			File convertFile = new File("D:\\AngularImage\\" + image.getOriginalFilename());
			path = "D:/AngularImage/" + image.getOriginalFilename();
			System.out.println(id);
			// convertFile.createNewFile();
			FileOutputStream fout = new FileOutputStream(convertFile);
			fout.write(image.getBytes());
			fout.close();
			loginn.UploadImage(path,id);
			System.out.println("Success");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("File is upload seccessfully", HttpStatus.OK);
	}
	
	@CrossOrigin(origins="http://localhost:4200")
	@RequestMapping(value = "/loadImage", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public void loadImage(@RequestParam (value="id") String id, HttpServletResponse Response)throws IOException {
		String path = loginn.loadImage(id);
		Response res = new Response();
		if (path!=null) {
			res.setCode("200");
			File convertFile = new File(path);
			FileInputStream file = new FileInputStream(convertFile);
			IOUtils.copy(file, Response.getOutputStream());
			file.close();
		} else if (path == null){
			try {
			res.setCode("404");

			File convertFile = new File("D:/AngularImage/default.jpg");
			FileInputStream file = new FileInputStream(convertFile);
			IOUtils.copy(file, Response.getOutputStream());
			file.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}
