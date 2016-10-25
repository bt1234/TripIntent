package edu.npu.troopers.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.npu.troopers.ApiPathConstants;

@RestController(value = ApiPathConstants.BASE)
public class SampleApi {

  @RequestMapping(ApiPathConstants.EMPTY)
  String home() {
    return "Hello World!";
  }

}
