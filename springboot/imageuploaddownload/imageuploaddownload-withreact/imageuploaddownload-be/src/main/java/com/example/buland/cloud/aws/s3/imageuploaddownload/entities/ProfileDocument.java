package com.example.buland.cloud.aws.s3.imageuploaddownload.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileDocument {

    private Long profileDocumentSize;

    private String profileDocumentMimeType;

    private String profileDocumentCategory;

    private String profileDocumentAccessURI;
}
