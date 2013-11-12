package com.percipient.matrix.service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.percipient.matrix.dao.AttachmentRepository;
import com.percipient.matrix.domain.TimesheetAttachment;
import com.percipient.matrix.util.DateUtil;
import com.percipient.matrix.util.HibernateUtil;
import com.percipient.matrix.view.TimesheetAttachmentView;

public interface AttachmentService {

    public void downloadAttachment(Integer attachmentId,
            HttpServletResponse response);

    public void uploadAttachment(Integer timesheetId, MultipartFile file);

    public List<TimesheetAttachmentView> getTimesheetAttachmentViewList(
            Integer timesheetId);

    public void deleteAttachment(Integer attachmentId);

}

@Service
class AttachmentServiceImpl implements AttachmentService {

    private static final String[] ACCEPTED_CONTENT_TYPES = new String[] {
            "application/pdf", "application/doc", "application/msword",
            "application/rtf", "image/bmp", "image/jpeg", "image/jpg",
            "image/gif" };

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    DateUtil dateUtil;

    @Autowired
    HibernateUtil hibernateUtil;

    @Override
    @Transactional
    public void downloadAttachment(Integer attachmentId,
            HttpServletResponse response) {

        TimesheetAttachment attachment;
        try {
            attachment = attachmentRepository
                    .getTimesheetAttachment(attachmentId);
            if (attachment == null) {
                return;
            }
            response.setHeader("Content-Disposition", "inline;filename=\""
                    + attachment.getFileName() + "\"");
            OutputStream out = response.getOutputStream();
            response.setContentType(attachment.getContentType());
            IOUtils.copy(attachment.getContent().getBinaryStream(), out);
            out.flush();
            out.close();
        } catch (Exception e) {
            throw new RuntimeException("couldn't download ", e);
        }
    }

    @Override
    @Transactional
    public void uploadAttachment(Integer timesheetId, MultipartFile file) {
        if (file != null
                && Arrays.asList(ACCEPTED_CONTENT_TYPES).contains(
                        file.getContentType())) {

            TimesheetAttachment attachment = new TimesheetAttachment();
            try {
                attachment.setContent(hibernateUtil.getBlobFromByteArray(file
                        .getBytes()));
            } catch (IOException e) {
                throw new RuntimeException("couldn't uload ", e);
            }
            attachment.setContentType(file.getContentType());
            attachment.setFileName(file.getOriginalFilename());
            attachment.setTimesheetId(timesheetId);
            attachment.setSize(file.getSize());

            attachmentRepository.saveAttachment(attachment);
        }
    }

    @Override
    @Transactional
    public List<TimesheetAttachmentView> getTimesheetAttachmentViewList(
            Integer timesheetId) {
        List<TimesheetAttachmentView> attachmentViewList = new ArrayList<TimesheetAttachmentView>();
        TimesheetAttachmentView attachmentView;
        List<TimesheetAttachment> attachmentList = attachmentRepository
                .getAllAttachmentsForTimesheet(timesheetId);
        for (TimesheetAttachment attachment : attachmentList) {
            attachmentView = getTSAttachmentViewFromTSAttachment(attachment);
            attachmentViewList.add(attachmentView);
        }
        return attachmentViewList;
    }

    private TimesheetAttachmentView getTSAttachmentViewFromTSAttachment(
            TimesheetAttachment attachment) {
        TimesheetAttachmentView attachmentView = new TimesheetAttachmentView();
        attachmentView.setSize(attachment.getSize());
        attachmentView.setId(attachment.getId());
        attachmentView.setFileName(attachment.getFileName());
        attachmentView.setContentType(attachment.getContentType());
        return attachmentView;
    }

    @Override
    @Transactional
    public void deleteAttachment(Integer attachmentId) {

        Boolean success = attachmentRepository
                .deleteTimesheetAttachment(attachmentId);
        if (!success) {
            // TODO
            throw new RuntimeException("couldn't delete ");
        }
    }

}
