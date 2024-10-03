## OCR Demo App

## Architecture Overview:
1. `Frontend (React)`: The user uploads front and back images of the insurance card.
2. `Backend (Spring Boot)`: Receives the uploaded images, sends them to AWS Textract for OCR processing, and returns the extracted data.
3. `AWS Textract`: Processes the images and returns structured data (e.g., JSON) containing the extracted text.

## Dependencies
```
<dependency>
    <groupId>software.amazon.awssdk</groupId>
    <artifactId>textract</artifactId>
    <version>2.28.12</version>
</dependency>

<dependency>
    <groupId>software.amazon.awssdk</groupId>
    <artifactId>s3</artifactId>
    <version>2.28.12</version>
</dependency>
```

### Testing Locally
#### Request
```
curl -F frontImage=@IMG_9176.jpg -F backImage=@IMG_9176.jpg http://localhost:8080/api/ocr/extractInsuranceInfo
```
#### Response
```Response
{"INSURANCE_CARD_SPEC_VISIT_COPAY":"$50","INSURANCE_CARD_OOP_MAX":"No result found","INSURANCE_CARD_CONTACT_NUMBER":"No result found","INSURANCE_CARD_PLAN_TYPE":"Cigna SureFit","INSURANCE_CARD_OFFICE_VISIT_COPAY":"No result found","INSURANCE_CARD_PROVIDER_CLAIM_ADDRESS":"No result found","INSURANCE_CARD_COINSURANCE":"No result found","INSURANCE_CARD_LEVEL_BENEFITS":"G","INSURANCE_CARD_MEMBER_ID":"122222222","INSURANCE_CARD_PROVIDER":"Cigna","INSURANCE_CARD_EFFECTIVE_DATE":"No result found","INSURANCE_CARD_NAME":"John Doe","INSURANCE_CARD_URGCARE_COPAY":"$15","INSURANCE_CARD_PLAN_GROUP_NUMBER":"00699999"}
```

### More Details
1. Visit [aws site](https://aws.amazon.com/getting-started/hands-on/extract-text-with-amazon-textract/)
2. [AWS Code Samples](https://github.com/aws-samples/amazon-textract-code-samples/blob/master/python/queries/insurance-card.ipynb)

