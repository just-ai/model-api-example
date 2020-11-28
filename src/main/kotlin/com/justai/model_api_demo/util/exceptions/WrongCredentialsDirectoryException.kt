package com.justai.model_api_demo.util.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.FAILED_DEPENDENCY, reason = "Credentials folder specified for project is invalid")
class WrongCredentialsDirectoryException : Exception("Credentials folder specified for project is invalid")