package org.papadeas.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum Reaction {

    POSITIVE("1"),
    NEGATIVE("2");

    final String num;

    Reaction(String num) {
        this.num = num;
    }

    @Override
    @JsonValue
    public String toString() {
        return this.num;
    }

}
