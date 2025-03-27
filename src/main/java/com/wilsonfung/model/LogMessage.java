package com.wilsonfung.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.AccessLevel;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import com.wilsonfung.utils.HashUtil;

@Data
@Document(collection = "log_messages")
@ToString(exclude = {"id", "signature"})
public class LogMessage {
    @Id
    private String id;
    private String message;
    private Date timestamp;
    private String instance;
    private String application;
    private String additionalInformation;
    private String userId;
    @Getter @Setter(AccessLevel.NONE) private String signature;

    public void generateSignature() {
        this.signature = HashUtil.sha1(this.toString());
    }

    //disabled generating Signature for the setters for performance reasons

    // public void setMessage(String message) {
    //     this.message = message;
    //     generateSignature();
    // }

    // public void setTimestamp(Date timestamp) {
    //     this.timestamp = timestamp;
    //     generateSignature();
    // }

    // public void setInstance(String instance) {
    //     this.instance = instance;
    //     generateSignature();
    // }

    // public void setApplication(String application) {
    //     this.application = application;
    //     generateSignature();
    // }

    // public void setUserId(String userId) {
    //     this.userId = userId;
    //     generateSignature();
    // }
}
