package com.example.questifyit.domain;

import java.io.Serializable;

public interface Entity<ID> extends Serializable {

    static final long serialVersionUID = 7331115341259248461L;
    ID getEntityID();
}
