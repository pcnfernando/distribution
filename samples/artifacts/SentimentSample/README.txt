Purpose of this sample is to test the functionality of sentiment extension in SP 4.0.0

1. Copy {SiddhiDistributionHome}/samples/artifacts/0032/sentimentExtensionSample.siddhi file to {SiddhiDistributionHome}/wso2/worker/deployment/siddhi-files/

2. Start the worker using ./{SiddhiDistributionHome}/bin/worker.sh

3. Run following curls commands to send some login events

curl -X POST \
  http://localhost:9090/simulation/single \
  -u admin:admin \
  -H 'content-type: text/plain' \
  -d '{
  "siddhiAppName": "sentimentExtensionSample",
  "streamName": "userWallPostStream",
  "timestamp": null,
  "data": [
    "Mohan",
    "David is a good person. David is a bad person"
  ]
}'


curl -X POST \
  http://localhost:9090/simulation/single \
  -u admin:admin \
  -H 'content-type: text/plain' \
  -d '{
  "siddhiAppName": "sentimentExtensionSample",
  "streamName": "userWallPostStream",
  "timestamp": null,
  "data": [
    "Nuwan",
    "David is a good person."
  ]
}'

4. See the output in the Siddhi Distribution terminal

NOTE: User credentials used in the curl commands are default values.
