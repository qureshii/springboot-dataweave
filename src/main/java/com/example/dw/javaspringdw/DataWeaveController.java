package com.example.dw.javaspringdw;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.mule.weave.v2.module.MimeType;
import org.mule.weave.v2.runtime.DataWeaveResult;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.mule.weave.v2.runtime.DataWeaveScript;
import org.mule.weave.v2.runtime.DataWeaveScriptingEngine;
import org.mule.weave.v2.runtime.ScriptingBindings;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.HashMap;

@RestController
@Log4j2
@RequestMapping("/api")
public class DataWeaveController {


    @GetMapping(value = "/hello")
    public ResponseEntity<String> sayhello() {
        log.info("Hello World");
        DataWeaveScriptingEngine scriptingEngine = new DataWeaveScriptingEngine();
        String script = "output json --- {\"message\": upper(payload), \"vars\": vars }";
        String inputPayload = "Hello World";
        DataWeaveScript compile = scriptingEngine.compile(script, new String[]{"payload","vars"});
        DataWeaveResult result =  compile.write(new ScriptingBindings()
                .addBinding("payload", inputPayload, "application/java", new HashMap<>())
                .addBinding("vars","my_variable", "application/java", new HashMap<>()));
        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(httpHeaders -> httpHeaders.add("Content-Type", result.getMimeType()))
                .body(result.getContentAsString());
    }

}
