package com.example.buland.cloud.aws.s3.imageuploaddownload.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileRequest {

    private String profileId;
    private String profileName;
    private Long profileDocumentStorageQuota = 5l * 1024;
}
