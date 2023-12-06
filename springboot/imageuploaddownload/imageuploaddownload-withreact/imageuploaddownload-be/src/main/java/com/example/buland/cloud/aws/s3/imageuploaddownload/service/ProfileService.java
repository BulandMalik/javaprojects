package com.example.buland.cloud.aws.s3.imageuploaddownload.service;

import com.example.buland.cloud.aws.s3.imageuploaddownload.dtos.ProfileDTO;
import com.example.buland.cloud.aws.s3.imageuploaddownload.entities.Profile;
import com.example.buland.cloud.aws.s3.imageuploaddownload.entities.ProfileDocument;
import com.example.buland.cloud.aws.s3.imageuploaddownload.repository.ProfileRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProfileService {

    private final ProfileRepository profileRepository;

    private final S3Service s3Service;
    private final StorageQuotaService storageQuotaService;

    public ProfileService(ProfileRepository profileRepository, StorageQuotaService storageQuotaService, S3Service s3Service){
        this.profileRepository = profileRepository;
        this.storageQuotaService = storageQuotaService;
        this.s3Service = s3Service;
    }

    public ProfileDTO getProfile(String profileId) {
        Profile profile = profileRepository.getProfile( profileId );
        ProfileDTO profileDTO = ProfileDTO.builder()
                                .profileId(profile.getProfileId())
                                .profileName(profile.getProfileName())
                                .profileDocumentStorageQuota(profile.getProfileDocumentStorageQuota())
                                .profileDocuments(profile.getProfileDocuments())
                                .build();
        return profileDTO;
    }

    //@Transactional
    public Boolean saveProfile(Profile profile) {
        return profileRepository.saveProfile( Optional.of(profile) );
    }

    /**
     * Validates that the user not going above its quota and if validation passes than add document into S3 and save it
     * to the profile.
     *
     * @param profileId
     * @param fileName
     * @param documentCategory
     * @param mimeType
     * @param inputStream
     * @return
     * @throws Exception
     */
    //@Transactional
    public Boolean addProfileDocument(String profileId, String fileName, Long documentSize, String documentCategory, String mimeType, InputStream inputStream)
            throws Exception {

        log.info("inside addProfileMediaAttachment with profileId={}, fileName={}, documentCategory={}, mimeType={}",profileId, fileName, documentCategory, mimeType);
        String s3ObjectKey = new StringBuilder(profileId).append("/").append(fileName).toString();

        Profile profile = profileRepository.getProfile(profileId);

        //Check Profile/User Document Size Quota Limit
        if ( !storageQuotaService.hasStorageSpace(profile, documentSize)) {
            throw new RuntimeException("Insufficient storage space.");
        }

        //Save Object in S3
        Boolean operationResult = s3Service.putObject(s3ObjectKey, inputStream);

        if ( !operationResult ) {
            throw new Exception("Cannot save object into S3 for fileName="+fileName);
        }
        ProfileDocument profileDocument = ProfileDocument.builder()
                .profileDocumentSize(documentSize)
                .profileDocumentMimeType(mimeType)
                .profileDocumentCategory(documentCategory)
                .profileDocumentAccessURI(s3ObjectKey)
                .build();
        operationResult = profileRepository.updateProfileDocuments(profileId, Optional.of(profileDocument));
        log.info("returning from addProfileMediaAttachment with profileId={}, fileName={}, operationResult={}",profileId, fileName, operationResult);

        return operationResult;
    }

    public List<ProfileDTO> getAllProfiles() {
        List<Profile> profiles = profileRepository.getAllProfiles( );
        List<ProfileDTO> profilesDTO = new ArrayList<ProfileDTO>(profiles.size());
        profiles.stream().forEach(profile -> {
            ProfileDTO profileDTO = ProfileDTO.builder()
                    .profileId(profile.getProfileId())
                    .profileName(profile.getProfileName())
                    .profileDocumentStorageQuota(profile.getProfileDocumentStorageQuota())
                    .profileDocuments(profile.getProfileDocuments())
                    .build();
            profilesDTO.add(profileDTO);
        });

        return profilesDTO;
    }

    public String getPreSignedGetUrl(String objectKey) {
        log.info("inside getPreSignedGetUrl with objectKey={}",objectKey);
        return s3Service.getPreSignedGetUrl(objectKey);
    }
}
