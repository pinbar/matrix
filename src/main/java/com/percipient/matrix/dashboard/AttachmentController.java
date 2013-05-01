package com.percipient.matrix.dashboard;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.percipient.matrix.service.AttachmentService;
import com.percipient.matrix.util.DateUtil;

@Controller
@RequestMapping(value = "/attachment")
public class AttachmentController {

    @Autowired
    AttachmentService attachmentService;

    @Autowired
    DateUtil dateUtil;

    @RequestMapping(value = "/timesheet", method = RequestMethod.GET)
    public void downloadAttachment(
            @RequestParam(value = "attachmentId", required = true) Integer attachmentId,
            HttpServletResponse response) {

        attachmentService.downloadAttachment(attachmentId, response);
    }

    @RequestMapping(value = "/timesheet", method = RequestMethod.POST)
    public void uploadAttachment(
            @RequestParam(value = "timesheetId", required = true) Integer timesheetId,
            @RequestParam(value = "file", required = true) MultipartFile file) {

        attachmentService.uploadAttachment(timesheetId, file);
    }

}
