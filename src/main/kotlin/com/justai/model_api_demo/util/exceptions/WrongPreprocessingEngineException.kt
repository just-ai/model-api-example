package com.justai.model_api_demo.util.exceptions

class WrongPreprocessingEngineException(name: String) : Exception("Engine $name is not supported")