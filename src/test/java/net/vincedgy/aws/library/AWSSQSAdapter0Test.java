package net.vincedgy.aws.library;

import net.vincedgy.aws.library.service.AWSSQSAdapter_0;
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
public class AWSSQSAdapter0Test {
    AWSSQSAdapter_0 AWSSQSAdapter_0;

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
            AWSSQSAdapter_0 = new AWSSQSAdapter_0();
            AWSSQSAdapter_0.testQueue();
            Assert.assertNotNull(this.AWSSQSAdapter_0);
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
