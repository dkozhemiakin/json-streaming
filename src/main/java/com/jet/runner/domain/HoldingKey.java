package com.jet.runner.domain;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor(staticName = "of")
public class HoldingKey {
    private final String titleId;
    private final String packageId;
}
