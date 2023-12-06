package com.example.buland.cloud.aws.s3.imageuploaddownload.repository;

import com.example.buland.cloud.aws.s3.imageuploaddownload.entities.Profile;
import com.example.buland.cloud.aws.s3.imageuploaddownload.entities.ProfileDocument;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class InMemoryProfileRepositoryImpl implements ProfileRepository {

    private Map<String, Profile> profileStore;

    public InMemoryProfileRepositoryImpl() {
        profileStore = new HashMap<String, Profile>();
    }

    public InMemoryProfileRepositoryImpl(Profile [] profiles) {
        profileStore = new HashMap<String, Profile>();

        Arrays.stream(profiles).forEach(profile -> {
            profileStore.put(profile.getProfileId(), profile);
        });
    }

    @Override
    public List<Profile> getAllProfiles() {
        List<Profile> profiles = this.profileStore.keySet().stream().map( key -> {
            return this.profileStore.get(key);
        }).collect(Collectors.toList());

        return profiles;
    }

    @Override
    public Profile getProfile(String profileId) {
        Profile profile = this.profileStore.get(profileId);
        return profile;
    }


    @Override
    public Boolean saveProfile(Optional<Profile> profile) {

        log.info("inside saveProfile to save profile={}", profile.get());
        if ( !profile.isPresent() ){
            log.error("saveProfile operation failed as incoming profile object is empty/not valid");
            return Boolean.FALSE; //failed operation
        }

        profileStore.put(profile.get().getProfileId(), profile.get());

        return Boolean.TRUE; //successful operation
    }

    @Override
    public Boolean updateProfileDocuments(String profileId, Optional<ProfileDocument> profileDocument) {
        log.info("inside updateProfileMediaAttachments to save profileId={} and profileDocument={}", profileId, profileDocument.get());
        if ( !profileDocument.isPresent() ){
            log.error("updateProfileMediaAttachments operation failed as incoming profileDocument object is empty/not valid");
            return Boolean.FALSE; //failed operation
        }

        Profile profile = profileStore.get(profileId);
        log.info("profile Object before Update={}", profile);
        profile.getProfileDocuments().add(profileDocument.get());
        log.info("profile Object after Update={}", profile);
        profileStore.put(profileId, profile);

        return Boolean.TRUE; //successful operation
    }
}
