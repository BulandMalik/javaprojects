PrescribeNow FeatureFlag SDK

This sdk is to utilize feature flag login within the code.

## Build

To build and run the unit tests:

```bash
mvn clean install
```

## SDK Ownership
Please reach out to the following folks if you have any questions.
* <b>Owners</b>
  * Buland Malik
  * Anzer Salem
  * Sarmad Ahmed
* <b>Slack</b> 
  * For any FF related updates (config changes, status changes etc.) will be communicated via [`#pn_featureflags`](https://prescribenowworkspace.slack.com/archives/C04HS1258UX)

## How to Integrate it?
Please include the following dependency to your project.
```
  <dependency>
      <groupId>com.prescribenow.ffsdk</groupId>
      <artifactId>featureflag-sdk</artifactId>
      <version>1.0.2.0</version>
  </dependency>
```

Add the `FeatureFlagServiceFactory` and create `FeatureFlagService` refrence. 

```
private final FeatureFlagServiceFactory featureFlagServiceFactory;

FeatureFlagService ffSvc = featureFlagServiceFactory.getFeatureFlagService("launchdarkly");
```

Set the map with right values and evaluate the flag.
```
String key = "PN_ERX-1023_MULTI_TENANCY_ENABLED";
Map<String, String> attributes = new HashMap<String,String>();
attributes.put("tenant_id", tid);
attributes.put("facility_id", fid);
attributes.put("provider_id", pid);
attributes.put("user_email", email);

if ( !ffSvc.evaluateFeatureFlag(key, attributes, Boolean.TRUE, Boolean.class) )
{
  //logic behind the FF goes here
}
else {
  //older logic goes here
}
```