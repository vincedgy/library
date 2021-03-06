package net.vincedgy.aws.library;

import net.vincedgy.aws.library.service.AWSSQSAdapter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LibraryApplicationTests {

    @Autowired
	AWSSQSAdapter awssqsAdapter;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testSimpleQueueService() {
        Assert.isInstanceOf(AWSSQSAdapter.class, awssqsAdapter);
	}

}
