package com.justai.model_api_demo.util.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.FAILED_DEPENDENCY, reason = "Invalid nerProjectId supplied.")
class WrongNerProjectIdException(name: String) :
        Exception("nerProjectId $name given in dialogflowConfig is invalid")