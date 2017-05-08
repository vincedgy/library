package net.vincedgy.aws.library.controller;

import net.vincedgy.aws.library.service.SimpleQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by vincent on 08/05/2017.
 */
@Controller
public class PostMessageController {

    @Autowired
    private SimpleQueueService simpleQueueService;

}
