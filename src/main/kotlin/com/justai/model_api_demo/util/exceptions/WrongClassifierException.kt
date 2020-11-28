package com.justai.model_api_demo.util.exceptions

class WrongClassifierException(name: String) : Exception("Classifier $name doesnt exist.")