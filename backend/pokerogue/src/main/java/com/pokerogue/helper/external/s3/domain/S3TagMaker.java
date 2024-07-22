package com.pokerogue.helper.external.s3.domain;

import java.util.List;
import software.amazon.awssdk.services.s3.model.Tag;
import software.amazon.awssdk.services.s3.model.Tagging;

public final class S3TagMaker {

    private S3TagMaker() {
    }

    public static Tagging makeDefaultTags() {
        Tag serviceTag = Tag.builder()
                .key("Service")
                .value("techcourse")
                .build();

        Tag roleTage = Tag.builder()
                .key("Role")
                .value("techcourse-etc")
                .build();

        Tag projectTeamTag = Tag.builder()
                .key("ProjectTeam")
                .value("pokerogue-helper")
                .build();

        return Tagging.builder()
                .tagSet(List.of(serviceTag, roleTage, projectTeamTag))
                .build();
    }
}
