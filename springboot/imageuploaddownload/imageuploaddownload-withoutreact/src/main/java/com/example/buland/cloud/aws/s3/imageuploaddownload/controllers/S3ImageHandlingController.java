package com.example.buland.cloud.aws.s3.imageuploaddownload.controllers;

import com.example.buland.cloud.aws.s3.imageuploaddownload.utils.S3Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@Slf4j
public class S3ImageHandlingController {

    @GetMapping("")
    public String viewHomePage() {
        return "upload"; //it will be resolved to the upload.html file that by default should exists inside src/main./resources/templates folder.
    }

    @GetMapping("/download")
    public String viewDownloadPage() {
        return "download"; //it will be resolved to the upload.html file that by default should exists inside src/main./resources/templates folder.
    }

    @PostMapping("/upload")
    public String handleUploadForm(Model model, String description,
                                   @RequestParam("file") MultipartFile multipart) {
        String message = processIncomingData(description, multipart, null, 0);

        model.addAttribute("message", message);

        return "message";
    }

    /**
     * We can use @PathVariable or @requestParam. Difference here is that because @PathVariable is extracting values from
     * the URI path, it’s not encoded. On the other hand, @RequestParam is encoded
     *
     * Either use @RequestParam String id but in this case, we can use  @PostMapping("/upload").
     *
     * Both @RequestParam and @PathVariable can be optional.
     *
     * @param model
     * @param description
     * @param id
     * @param multipart
     * @return
     */
    @PostMapping("/upload/{id}")
    public String handleUploadFormWithId(Model model, String description,@PathVariable("id") String id,
                                   @RequestParam("file") MultipartFile multipart) {
        log.info("incoming request for handleUploadFormWithId={}",id);

        String message = processIncomingData(description, multipart, id, 0);

        model.addAttribute("message", message);

        return "message";
    }

    @PostMapping("/upload/public")
    public String handleUploadFormWithPublicAccess(Model model, String description,
                                                   @RequestParam("file") MultipartFile multipart) {
        String message = processIncomingData(description, multipart, null, 1);

        model.addAttribute("message", message);

        return "message";
    }

    @GetMapping("/upload/{id}")
    public ResponseEntity<byte[]> handleGetData(Model model, String description,@PathVariable("id") String id, @RequestParam("key") String key) {
        log.info("incoming request for handleGetData={}",id);

        byte bytes[] = null;
        try {
            bytes = S3Util.getFile(id + "/" + key);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (bytes == null || bytes.length<=0 ) {
            throw new RuntimeException("Cannot read object from S3");
        }

        String fileName = null;
        try {
            fileName = URLEncoder.encode(key, "UTF-8").replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.setContentLength(bytes.length);
        httpHeaders.setContentDispositionFormData("attachment", fileName);

        return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
    }

    private static String processIncomingData(String description, MultipartFile multipart, String id, int s3ClientType) {
        String fileName = multipart.getOriginalFilename();

        log.info("Description: " + description);
        log.info("filename: " + fileName);

        String message = "";

        try {
            if ( s3ClientType == 0 ) {
                S3Util.uploadFileWithoutPublicAccess(id, fileName, multipart.getInputStream());
            } else if ( s3ClientType == 1) {
                S3Util.uploadFileWithPublicAccess(fileName, multipart.getInputStream());
            } else {
                S3Util.asyncUploadFileWithPublicAccess(fileName, multipart.getInputStream());
            }
            message = "Your file has been uploaded successfully!";
        } catch (Exception ex) {
            ex.printStackTrace();
            message = "Error uploading file: " + ex.getMessage();
        }
        return message;
    }

}
