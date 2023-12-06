package com.example.buland.cloud.aws.s3.imageuploaddownload.service;

import com.example.buland.cloud.aws.s3.imageuploaddownload.entities.Profile;
import com.example.buland.cloud.aws.s3.imageuploaddownload.entities.ProfileDocument;
import org.springframework.stereotype.Service;

@Service
public class StorageQuotaService {

    public boolean hasStorageSpace(Profile profile, long sizeToAdd) {
        long currentStorage = getCurrentStorage(profile);
        long newStorage = currentStorage + sizeToAdd;
        return newStorage <= profile.getProfileDocumentStorageQuota();
    }

    private long getCurrentStorage(Profile profile) {
        return profile.getProfileDocuments().stream()
                .mapToLong(ProfileDocument::getProfileDocumentSize)
                .sum();
    }
}