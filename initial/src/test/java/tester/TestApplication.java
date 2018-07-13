package tester;
import org.junit.runner.RunWith;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import Controller.Application;
import Model.Login;
import Model.Response;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class,webEnvironment = WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestApplication {
	
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	@Test
	public void _01loginSuccess() {
		// this.webClient.get().uri("http://localhost:"+port+"/detailuser").exchange().expectStatus().isOk()
		// .expectBody(String.class).isEqualTo("success");
		Login user = new Login();
		user.setUsername("tester1");
		user.setPassword("1111");
		Response response = this.restTemplate.postForObject("http://localhost:" + port + "/login", user,
				Response.class);
		// assertThat(Response.class,response.getResult().contain("success"));
		assertThat(response.getCode()).isEqualTo("200");
	}


	@Test
	public void _02loginFail() {
		Login user = new Login();
		user.setUsername("111");
		user.setPassword("111");
		Response response = this.restTemplate.postForObject("http://localhost:" + port + "/login", user,
				Response.class);
		assertThat(response.getCode()).isEqualTo("404");
	}

	@Test
	public void _03getallDetailUser() {
		Response response = this.restTemplate.getForObject("http://localhost:" + port + "/getalluser", Response.class);
		assertThat(response.getCode()).isEqualTo("200");
	}

	@Test
	public void _04insertSuccess() {
		Login user = new Login();
		user.setId(3);
		user.setUsername("TT");
		user.setPassword("TT");
		Response response = this.restTemplate.postForObject("http://localhost:" + port + "/insertlogin", user,
				Response.class);
		assertThat(response.getCode()).isEqualTo("200");
	}

	@Test
	public void _05insertFail() {
		Login user = new Login();
		user.setId(3);
		user.setUsername("TT");
		user.setPassword("TT");
		Response response = this.restTemplate.postForObject("http://localhost:" + port + "/insertlogin", user,
				Response.class);
		assertThat(response.getCode()).isEqualTo("404");
	}

	@Test
	public void _06updateSuccess() {
		Login user = new Login();
		user.setId(6);
		user.setUsername("abc");
		user.setPassword("1234");
		Response response = this.restTemplate.postForObject("http://localhost:" + port + "/updatelogin", user,
				Response.class);
		assertThat(response.getCode()).isEqualTo("200");
	}

	@Test
	public void _07updateFail() {
		Login user = new Login();
		user.setId(3);
		user.setUsername("TT");
		user.setPassword("TT");
		Response response = this.restTemplate.postForObject("http://localhost:" + port + "/updatelogin", user,
				Response.class);
		assertThat(response.getCode()).isEqualTo("404");
	}

//	@Test
//	public void _08searchSuccess() {
//		Response response = this.restTemplate.getForObject("http://localhost:" + port + "/searchuser?username=JJJ",
//				Response.class);
//		assertThat(response.getCode()).isEqualTo("success");
//	}
//
//	@Test
//	public void _09searchFail() {
//		Response response = this.restTemplate
//				.getForObject("http://localhost:" + port + "/searchuser?username=JJJJJJJJJ", Response.class);
//		assertThat(response.getCode()).isEqualTo("fail");
//	}

	@Test
	public void _10deleteSuccess() { // อย่าลืม!!!! มัน delete record ออก ตลอด โง่ฉิบหาย !!!!!
		Login user = new Login();
		user.setUsername("TT");
		user.setPassword("TT");
		Response response = this.restTemplate.postForObject("http://localhost:" + port + "/deletelogin", user,
				Response.class);
		assertThat(response.getCode()).isEqualTo("200");
	}

	@Test
	public void _11deleteFail() {
		Login user = new Login();
		user.setUsername("TTT");
		user.setPassword("TTT");
		Response response = this.restTemplate.postForObject("http://localhost:" + port + "/deletelogin", user,
				Response.class);
		assertThat(response.getCode()).isEqualTo("404");
	}
}