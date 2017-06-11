package net.vincedgy.aws.library.controller;

import net.vincedgy.aws.library.service.AWSSQSAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Created by vincent on 08/05/2017.
 */
@Controller
public class PostMessageController {

    private AWSSQSAdapter awssqsAdapter;

    @Autowired
    public PostMessageController(AWSSQSAdapter awssqsAdapter) {
    }




}
