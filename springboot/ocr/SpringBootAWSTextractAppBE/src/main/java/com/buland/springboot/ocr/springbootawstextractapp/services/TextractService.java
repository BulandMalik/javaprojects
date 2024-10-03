package com.buland.springboot.ocr.springbootawstextractapp.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.textract.TextractClient;
import software.amazon.awssdk.services.textract.model.*;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;

@Service
@Slf4j
public class TextractService {

    private final TextractClient textractClient;

    public TextractService(TextractClient textractClient) {
        this.textractClient = textractClient;
    }

    public Map<String, String> extractInsuranceData(MultipartFile frontImage, MultipartFile backImage) throws IOException {

        log.info("inside extractInsuranceData");
        Map<String, String> extractedData = new HashMap<>();

        // Process front and back images separately
        extractedData.putAll(processImageWithQueriesFeatureType(frontImage, Boolean.TRUE));
        extractedData.putAll(processImageWithQueriesFeatureType(backImage, Boolean.FALSE));

        //extractedData.putAll(processImageWithFormFeatureType(frontImage));

        return extractedData;
    }

    private Map<String, String> processImageWithQueriesFeatureType(MultipartFile image, boolean isCardFrontSide) throws IOException {
        log.info("inside processImage...");
        ByteBuffer imageBytes = ByteBuffer.wrap(image.getBytes());

        List<Query> frontSideCardQuestions = Arrays.asList(
                        Query.builder().text("What is member name?").alias("INSURANCE_CARD_NAME").build(),
                        Query.builder().text("What is the member id?").alias("INSURANCE_CARD_MEMBER_ID").build(),
                        Query.builder().text("What is the effective date?").alias("INSURANCE_CARD_EFFECTIVE_DATE").build(),
                        Query.builder().text("What is the plan type?").alias("INSURANCE_CARD_PLAN_TYPE").build(),
                        Query.builder().text("What is the Group Number?").alias("INSURANCE_CARD_PLAN_GROUP_NUMBER").build(),
                        Query.builder().text("What is the office visit copay?").alias("INSURANCE_CARD_OFFICE_VISIT_COPAY").build(),
                        Query.builder().text("What is the specialist visit copay?").alias("INSURANCE_CARD_SPEC_VISIT_COPAY").build(),
                        Query.builder().text("What is the urgent care visit copay?").alias("INSURANCE_CARD_URGCARE_COPAY").build(),
                        Query.builder().text("What is the coinsurance amount?").alias("INSURANCE_CARD_COINSURANCE").build(),
                        Query.builder().text("What is medical insurance provider?").alias("INSURANCE_CARD_PROVIDER").build(),
                        Query.builder().text("What is the OOP max?").alias("INSURANCE_CARD_OOP_MAX").build(),
                        Query.builder().text("What is the level of benefits?").alias("INSURANCE_CARD_LEVEL_BENEFITS").build() );

        List<Query> backSideCardQuestions = Arrays.asList(
                Query.builder().text("What is provider claim submission address?").alias("INSURANCE_CARD_PROVIDER_CLAIM_ADDRESS").build(),
                Query.builder().text("What is the Insurance contact number?").alias("INSURANCE_CARD_CONTACT_NUMBER").build());

        // Call AWS Textract
        AnalyzeDocumentRequest request = AnalyzeDocumentRequest.builder()
                .document(Document.builder().bytes(SdkBytes.fromByteBuffer(imageBytes)).build())
                .featureTypes(FeatureType.QUERIES, FeatureType.TABLES, FeatureType.FORMS)
                .queriesConfig(QueriesConfig.builder().queries( (isCardFrontSide ? frontSideCardQuestions : backSideCardQuestions)).build())
                .build();
        log.info("going to analyze the document ...");
        AnalyzeDocumentResponse response = textractClient.analyzeDocument(request);
        Map<String, String> formData = extractQueryResults(response);
        log.info("Analyze the document with response={}",formData);
        return formData;
    }

    private Map<String, String> processImageWithFormFeatureType(MultipartFile image) throws IOException {
        log.info("inside processImage...");
        ByteBuffer imageBytes = ByteBuffer.wrap(image.getBytes());

        // Call AWS Textract
        AnalyzeDocumentRequest request = AnalyzeDocumentRequest.builder()
                .document(Document.builder().bytes(SdkBytes.fromByteBuffer(imageBytes)).build())
                .featureTypes(FeatureType.FORMS)  // Forms mode helps capture key-value pairs
                .build();
        log.info("going to analyze the document ...");
        AnalyzeDocumentResponse response = textractClient.analyzeDocument(request);
        Map<String, String> formData = extractFormResults(response);
        log.info("Analyze the document with response={}",formData);
        return formData;
    }

    // Helper method to extract key-value pairs from forms
    private Map<String, String> extractFormResults(AnalyzeDocumentResponse response) {
        Map<String, String> formResults = new HashMap<>();
        List<Block> blocks = response.blocks();

        Block currentKeyBlock = null;
        for (Block block : blocks) {
            if (block.blockTypeAsString().equals("KEY_VALUE_SET")) {
                if (block.entityTypes().contains("KEY")) {
                    currentKeyBlock = block; // Save the current key
                } else if (block.entityTypes().contains("VALUE") && currentKeyBlock != null) {
                    // Retrieve the key-value pair
                    String key = extractTextFromBlock(currentKeyBlock, blocks);
                    String value = extractTextFromBlock(block, blocks);

                    formResults.put(key.toLowerCase(), value);
                    currentKeyBlock = null;
                }
            }
        }

        return formResults;
    }

    // Helper method to extract text from blocks
    private String extractTextFromBlock(Block block, List<Block> blocks) {
        StringBuilder text = new StringBuilder();
        if (block.relationships() != null) {
            for (var relationship : block.relationships()) {
                if (relationship.type().equals(RelationshipType.CHILD)) {
                    for (String id : relationship.ids()) {
                        blocks.stream()
                                .filter(b -> b.id().equals(id))
                                .findFirst()
                                .ifPresent(childBlock -> text.append(childBlock.text()).append(" "));
                    }
                }
            }
        }
        return text.toString().trim();
    }


    public Map<String, String> extractQueryResults(AnalyzeDocumentResponse response) {
        Map<String, String> queryResults = new HashMap<>();

        Iterator<Block> blockIterator = response.blocks().iterator();
        while (blockIterator.hasNext()) {
            Block block = blockIterator.next();
            log.info("The block type is {}", block.blockType().toString());
        }

        // Iterate through each block to find QUERY and QUERY_RESULT types
        for (Block block : response.blocks()) {
            if ("QUERY".equals(block.blockTypeAsString())) {
                // Extract the query alias (configured in the request) from the QUERY block
                String queryAlias = block.query().alias();
                String queryText = block.query().text();

                // Find the related QUERY_RESULT block
                Block queryResultBlock = findRelatedQueryResult(block, response.blocks());

                if (queryResultBlock != null) {
                    // Get the answer text
                    String answerText = queryResultBlock.text();
                    queryResults.put(queryAlias, answerText);
                } else {
                    queryResults.put(queryAlias, "No result found");
                }
            }
        }

        return queryResults;
    }

    // Helper method to find the related QUERY_RESULT block for a QUERY block
    private Block findRelatedQueryResult(Block queryBlock, List<Block> blocks) {
        if (queryBlock.relationships() != null) {
            for (var relationship : queryBlock.relationships()) {
                if (RelationshipType.ANSWER.equals(relationship.type())) {
                    for (String id : relationship.ids()) {
                        for (Block block : blocks) {
                            if (block.id().equals(id) && "QUERY_RESULT".equals(block.blockTypeAsString())) {
                                return block;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
}