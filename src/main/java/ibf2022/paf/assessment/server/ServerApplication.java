package ibf2022.paf.assessment.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class ServerApplication implements CommandLineRunner{

	@Autowired
	JdbcTemplate template;

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

	@Override
	public void run(String... args) {
		String update= "update user_ set username=? where user_id=?";
		Integer inte = template.update(update, "fred", "1b80114c");
		System.out.println(Integer.toString(inte));
	}
}
