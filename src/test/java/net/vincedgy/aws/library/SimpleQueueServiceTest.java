package net.vincedgy.aws.library;

import net.vincedgy.aws.library.service.SimpleQueueService;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.Assert;


/**
 * SimpleQueueService Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>mai 7, 2017</pre>
 */
public class SimpleQueueServiceTest {
    SimpleQueueService simpleQueueService;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }


    /**
     * Method: getCredentials()
     */
    @Test
    public void testConstructor() throws Exception {
        try {
            simpleQueueService = new SimpleQueueService();
            simpleQueueService.testQueue();
            Assert.assertNotNull(this.simpleQueueService);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Method: openQueue()
     */
    //@Test
    public void testOpenQueue() throws Exception {
        Assert.fail("Echec");

    }

} 
