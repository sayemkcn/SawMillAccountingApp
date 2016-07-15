package sayem.picoapps.app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import sayem.toracode.app.AccountingAppApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AccountingAppApplication.class)
@WebAppConfiguration
public class AccountingAppApplicationTests {

	@Test
	public void contextLoads() {
	}

}
