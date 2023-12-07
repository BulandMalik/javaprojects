package com.example.buland.cloud.aws.s3.imageuploaddownload.controllers;

import com.example.buland.cloud.aws.s3.imageuploaddownload.dtos.ProfileDTO;
import com.example.buland.cloud.aws.s3.imageuploaddownload.dtos.ProfileRequest;
import com.example.buland.cloud.aws.s3.imageuploaddownload.entities.Profile;
import com.example.buland.cloud.aws.s3.imageuploaddownload.service.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Controller
@Slf4j
@RequestMapping("/profiles")
@CrossOrigin(origins = "http://localhost:3000")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("")
    public ResponseEntity<String> saveProfile(@RequestBody ProfileRequest profileReq) {

        log.info("inside saveProfile with incoming request={}", profileReq);

        if ( !StringUtils.hasLength( profileReq.getProfileId() ) ) {
            profileReq.setProfileId(UUID.randomUUID().toString());
        }
        Profile profile = Profile.builder()
                .profileId(profileReq.getProfileId())
                .profileName(profileReq.getProfileName())
                .build();

        profileService.saveProfile(profile);
        return new ResponseEntity<>("Profile Saved Successfully!", HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<ProfileDTO>> getProfiles() {

        log.info("inside getProfiles.....");

        List<ProfileDTO> profilesDTO = profileService.getAllProfiles();
        return new ResponseEntity<>(profilesDTO, HttpStatus.OK);
    }

    @GetMapping("{profileId}")
    public ResponseEntity<ProfileDTO> getProfile(@PathVariable("profileId") String profileId) {

        log.info("inside getProfile with incoming profileId={}", profileId);

        ProfileDTO profileDTO = profileService.getProfile(profileId);
        return new ResponseEntity<>(profileDTO, HttpStatus.OK);
    }

    @PostMapping("/{profileId}/upload")
    public ResponseEntity<String> handleUploadForm(@PathVariable("profileId") String profileId,
                                                   @RequestParam("documentCategory") String documentCategory,
                                                   @RequestParam("file") MultipartFile multipart) {
        String fileName = multipart.getOriginalFilename();
        String mimeType = multipart.getContentType();
        Long documentSize = multipart.getSize();

        log.info("inside handleUploadForm with profileId={}, filename={}, documentCategory={}, mimeType={} and documentSize={}", profileId, fileName, documentCategory, mimeType, documentSize);

        String message = "";

        try {
            Boolean result = profileService.addProfileDocument(profileId, fileName, documentSize, documentCategory, mimeType, multipart.getInputStream());
            if ( result )
                message = "Your file has been uploaded successfully!";
            else
                message = "Your file upload operation failed!!!";
        } catch (Exception ex) {
            ex.printStackTrace();
            message = "Error uploading file: " + ex.getMessage();
        }

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("{profileId}/download/{objectKey}")
    public ResponseEntity<String> downloadProfileMediaFile(@PathVariable("profileId") String profileId, @PathVariable("objectKey") String objectKey) {

        log.info("inside Profile with incoming profileId={} and objectKey={}", profileId, objectKey);

        String preSignedUrl = profileService.getPreSignedGetUrl(new StringBuilder(profileId).append("/").append(objectKey).toString());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", preSignedUrl);
        return new ResponseEntity<>(headers, HttpStatus.FOUND); //Redirect
        //return new ResponseEntity<>(preSignedUrl, HttpStatus.FOUND); //Redirect
    }


}
