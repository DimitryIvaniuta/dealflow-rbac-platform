package com.github.dimitryivaniuta.dealflow.graphql.input;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCustomerInput {
    private UUID workspaceId;
    private String displayName;
    private String email;
    private String phone;
    private String externalRef;
    private UUID ownerMemberId;
}
