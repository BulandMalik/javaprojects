package com.example.buland.cloud.aws.s3.imageuploaddownload.repository;

import com.example.buland.cloud.aws.s3.imageuploaddownload.entities.Profile;
import com.example.buland.cloud.aws.s3.imageuploaddownload.entities.ProfileDocument;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository {

    public List<Profile> getAllProfiles();

    public Profile getProfile(String profileId);

    public Boolean saveProfile(Optional<Profile> profile);
    public Boolean updateProfileDocuments(String profileId, Optional<ProfileDocument> profileDocument);

}
