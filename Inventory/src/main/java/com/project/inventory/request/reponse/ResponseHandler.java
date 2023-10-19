package com.project.inventory.request.reponse;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
/**
 * Response handler class.
*/

public class ResponseHandler {
  /**
  * custom response method.
  */

  public static ResponseEntity<Object> generateResponse(String message,
                HttpStatus status, String objName, Object responseObj) {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put(objName, responseObj);
    map.put("message", message);
    map.put("status", status.value());

    return new ResponseEntity<Object>(map, status);
  }
}