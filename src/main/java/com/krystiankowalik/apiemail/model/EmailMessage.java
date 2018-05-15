package com.krystiankowalik.apiemail.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailMessage implements Serializable {

    @Nullable
    private String from;
    @Nullable
    private String replyTo;
    @Nullable
    private String[] to;
    @Nullable
    private String[] cc;
    @Nullable
    private String[] bcc;
    @Nullable
    private Date sentDate;
    @Nullable
    private String subject;
    @Nullable
    private String text;


}
