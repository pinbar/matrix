package com.percipient.matrix.dashboard;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.percipient.matrix.display.TimesheetAttachmentView;
import com.percipient.matrix.service.AttachmentService;
import com.percipient.matrix.util.DateUtil;

@Controller
@RequestMapping(value = "/attachment")
public class AttachmentController {

    @Autowired
    AttachmentService attachmentService;

    @Autowired
    DateUtil dateUtil;

    private static String URL_STRING = "/attachment/timesheet?attachmentId=";

    @RequestMapping(value = "/timesheet", method = RequestMethod.GET)
    public void downloadAttachment(
            @RequestParam(value = "attachmentId", required = true) Integer attachmentId,
            HttpServletResponse response) {

        attachmentService.downloadAttachment(attachmentId, response);
    }

    @RequestMapping(value = "/timesheet", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteAttachment(
            @RequestParam(value = "attachmentId", required = true) Integer attachmentId,
            HttpServletResponse response) {

        attachmentService.deleteAttachment(attachmentId);
    }

    @RequestMapping(value = "/timesheet", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ObjectNode uploadAttachment(
            @RequestParam(value = "timesheetId", required = true) Integer timesheetId,
            @RequestParam(value = "file", required = true) MultipartFile file,
            HttpServletRequest request, Model model) {

        if (timesheetId == null) {
            return null;
        }

        attachmentService.uploadAttachment(timesheetId, file);
        List<TimesheetAttachmentView> attachments = attachmentService
                .getTimesheetAttachmentViewList(timesheetId);
        List<TimesheetAttachmentView> currentAttachment = getCurrentAttachment(
                attachments, file.getOriginalFilename());
        return convertToViewJSonFormat(currentAttachment,
                request.getContextPath());
    }

    @RequestMapping(value = "/timesheet/all", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ObjectNode getAttachments(
            @RequestParam(value = "timesheetId", required = true) Integer timesheetId,
            HttpServletRequest request) {

        List<TimesheetAttachmentView> attachments = attachmentService
                .getTimesheetAttachmentViewList(timesheetId);
        return convertToViewJSonFormat(attachments, request.getContextPath());
    }

    private List<TimesheetAttachmentView> getCurrentAttachment(
            List<TimesheetAttachmentView> attachments, String originalFilename) {

        List<TimesheetAttachmentView> currentAttachment = new ArrayList<TimesheetAttachmentView>();
        for (TimesheetAttachmentView attachment : attachments) {
            if (originalFilename.equalsIgnoreCase(attachment.getFileName())) {
                currentAttachment.add(attachment);
                break;
            }
        }
        return currentAttachment;
    }

    /**
     * 
     * @param attachments
     * @param contextPath
     * @return JSONObject in this format :
     * 
     *         <pre>
     * {"files": [
     *   {
     *     "name": "picture1.jpg",
     *     "size": 902604,
     *     "url": "http:\/\/example.org\/files\/picture1.jpg",
     *     "delete_url": "http:\/\/example.org\/files\/picture1.jpg",
     *     "delete_type": "DELETE"
     *   }
     *  
     * ]}
     * </pre>
     */
    private ObjectNode convertToViewJSonFormat(
            List<TimesheetAttachmentView> attachments, String contextPath) {

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode retObject = mapper.createObjectNode();
        ArrayNode aa = retObject.putArray("files");
        for (TimesheetAttachmentView attachment : attachments) {
            String url = contextPath + URL_STRING + attachment.getId();
            ObjectNode attachmentJSON = mapper.createObjectNode();
            attachmentJSON.put("name", attachment.getFileName());
            attachmentJSON.put("size", attachment.getSize());
            attachmentJSON.put("url", url);
            attachmentJSON.put("delete_url", url);
            attachmentJSON.put("delete_type", "DELETE");
            aa.add(attachmentJSON);
        }
        return retObject;

    }

}
