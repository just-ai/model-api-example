# Model API
​
Model API is a JAICP feature that allows our partners to use third-party tokenizers, named entity- and intent-recognition services together with JAICP DSL.
## Contract
The third-party service implementing the Model API feature should fulfill a contract that is described in [Model API Specification](https://github.com/just-ai/model-api-example/blob/main/model-api-specification.yml).
## Integration with JAICP project
To use external NLU service in your JAICP project you should configure it in project NLU settings. Please, refer to the [documentation](https://help.just-ai.com/#/docs/en/NLU_core/advanced_classifer_settings?id=external-nlu-service).
## Service example
This repository contains an example of a microservice implementing Model API specification using Spacy as tokenizing engine and Dialogflow as an intent-processor and named entity recognizer (NER).
​
## Example restrictions:
Due to Dialogflow restrictions, given example only processes named entities present inside valid intents and does not support topN intents feature, providing single best-match intent (thus, is only capable of correctly handling global intents).
​
## Requirements:
Following requirements will be installed automatically if you choose to run the service in docker container:
- Java 8;
- Maven;
- Python3;
- Spacy (And SudachiPy for Japanese language processing).
​
## Installation
- Create a dialogflow project;
- Fill in the intents, each intent must have a single output context with a lifetime of 1 request;
- Create a service account in a Google Cloud Platform project associated with Dialogflow project;
- Download the service account key as JSON and place it inside the microservice credentials folder (by default it's named "creds")
- Fill in the configuration files (description below)
- Run in docker:
    - Run `mvn package`
    - Run `docker compose up`
- Run without docker:
    - Run `mvn spring-boot:run`
​
## Configuration
##### application.properties:
```
model.api.auth.login    //Basic auth credentials used to access the microservice
model.api.auth.password
```    
##### dialogflow.properties:
```
dialogflow.nerProjectId         //GCP id of a project that will be used for NER
dialogflow.credentialsDirectory //Path to a folder containing service key JSONs
```
##### exceptions.properties:
```
exception.message.malformedBody     //Map of language codes to error messages for requests with malformed bodies
```
```
preprocessor.spacyPath          //Path to main spacy script
preprocessor.modelByLanguage    //Map of language codes to spacy model names
