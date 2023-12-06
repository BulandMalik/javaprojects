package com.example.buland.cloud.aws.s3.imageuploaddownload.dtos;

import com.example.buland.cloud.aws.s3.imageuploaddownload.entities.ProfileDocument;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
public class ProfileDTO {

    private String profileId;
    private String profileName;
    private long profileDocumentStorageQuota;

    private List<ProfileDocument> profileDocuments;// = new ArrayList<ProfileMediaAttachment>();

    public List<ProfileDocument> getProfileDocuments() {
        if ( profileDocuments == null )
            this.profileDocuments = new ArrayList<ProfileDocument>();
        return this.profileDocuments;
    }
}
