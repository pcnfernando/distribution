package org.wso2.carbon.stream.processor.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * Artifact
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaMSF4JServerCodegen",
        date = "2017-05-31T15:43:24.557Z")
public class Artifact {
    @JsonProperty("name")
    private String name = null;

    @JsonProperty("query")
    private String query = null;

    public Artifact name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Execution Plan Name
     *
     * @return name
     **/
    @ApiModelProperty(value = "Siddhi App Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Artifact query(String query) {
        this.query = query;
        return this;
    }

    /**
     * Siddhi Query
     *
     * @return query
     **/
    @ApiModelProperty(value = "Siddhi Query")
    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Artifact artifact = (Artifact) o;
        return Objects.equals(this.name, artifact.name) &&
                Objects.equals(this.query, artifact.query);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, query);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Artifact {\n");

        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    query: ").append(toIndentedString(query)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
