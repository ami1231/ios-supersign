package com.naoh.iossupersign.model.bo;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UdidBO implements Serializable {

    private String payloadOrganization;

    private String payloadDisplayName;

    private String payloadUUID;
}
