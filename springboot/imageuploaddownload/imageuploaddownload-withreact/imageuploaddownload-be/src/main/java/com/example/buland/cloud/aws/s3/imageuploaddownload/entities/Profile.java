package com.example.buland.cloud.aws.s3.imageuploaddownload.entities;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
public class Profile {

    private String profileId;
    private String profileName;

    @Builder.Default
    private Long profileDocumentStorageQuota = 15L * 1000 * 1024;  // default Storage quota in bytes ==> 15MB
    ;
    private List<ProfileDocument> profileDocuments;// = new ArrayList<ProfileMediaAttachment>();

    public List<ProfileDocument> getProfileDocuments() {
        if ( profileDocuments == null )
            this.profileDocuments = new ArrayList<ProfileDocument>();
        return this.profileDocuments;
    }
}
