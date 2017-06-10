package net.vincedgy.aws.library.controller;

import net.vincedgy.aws.library.service.AWSSQSAdapter_0;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Created by vincent on 08/05/2017.
 */
@Controller
public class PostMessageController {

    @Autowired
    private AWSSQSAdapter_0 AWSSQSAdapter_0;

}
