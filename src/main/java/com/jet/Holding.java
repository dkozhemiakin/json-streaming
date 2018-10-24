package com.jet;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.annotation.Generated;

@JsonPropertyOrder({
        "order_hashCode",
        "id",
        "publicationTitle",
        "print_identifier",
        "online_identifier",
        "date_first_issue_online",
        "num_first_vol_online",
        "num_first_issue_online",
        "date_last_issue_online",
        "num_last_vol_online",
        "num_last_issue_online",
        "title_url",
        "first_author",
        "title_id",
        "embargo_info",
        "coverage_depth",
        "notes",
        "publisher_name",
        "publication_type",
        "date_monograph_published_print",
        "date_monograph_published_online",
        "monograph_volume",
        "monograph_edition",
        "first_editor",
        "parent_publication_title_id",
        "preceding_publication_title_id",
        "access_type",
        "package_name",
        "package_id",
        "vendor_name",
        "vendor_id",
        "resource_type",
        "package_content_type"
})
@Generated("org.jsonschema2pojo")
@Data
@NoArgsConstructor
@EqualsAndHashCode
public class Holding {

    @SerializedName("orderHashCode")
    private Integer orderHashCode;
    @SerializedName("id")
    private Integer id;
    @SerializedName("publication_title")
    private String publicationTitle;
    @SerializedName("print_identifier")
    private String printIdentifier;
    @SerializedName("online_identifier")
    private String onlineIdentifier;
    @SerializedName("date_first_issue_online")
    private String dateFirstIssueOnline;
    @SerializedName("num_first_vol_online")
    private String numFirstVolOnline;
    @SerializedName("num_first_issue_online")
    private String numFirstIssueOnline;
    @SerializedName("date_last_issue_online")
    private String dateLastIssueOnline;
    @SerializedName("num_last_vol_online")
    private String numLastVolOnline;
    @SerializedName("num_last_issue_online")
    private String numLastIssueOnline;
    @SerializedName("title_url")
    private String titleUrl;
    @SerializedName("first_author")
    private String firstAuthor;
    @SerializedName("title_id")
    private String titleId;
    @SerializedName("embargo_info")
    private String embargoInfo;
    @SerializedName("coverage_depth")
    private String coverageDepth;
    @SerializedName("notes")
    private String notes;
    @SerializedName("publisher_name")
    private String publisherName;
    @SerializedName("publication_type")
    private String publicationType;
    @SerializedName("date_monograph_published_print")
    private String dateMonographPublishedPrint;
    @SerializedName("date_monograph_published_online")
    private String dateMonographPublishedOnline;
    @SerializedName("monograph_volume")
    private String monographVolume;
    @SerializedName("monograph_edition")
    private String monographEdition;
    @SerializedName("first_editor")
    private String firstEditor;
    @SerializedName("parent_publication_title_id")
    private String parentPublicationTitleId;
    @SerializedName("preceding_publication_title_id")
    private String preceedingPublicationTitleId;
    @SerializedName("access_type")
    private String accessType;
    @SerializedName("package_name")
    private String packageName;
    @SerializedName("package_id")
    private String packageId;
    @SerializedName("vendor_name")
    private String vendorName;
    @SerializedName("vendor_id")
    private String vendorId;
    @SerializedName("resource_type")
    private String resourceType;
    @SerializedName("package_content_type")
    private String packageContentType;

}
