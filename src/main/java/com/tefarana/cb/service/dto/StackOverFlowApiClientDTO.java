package com.tefarana.cb.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the StackOverFlowApiClient entity.
 */
public class StackOverFlowApiClientDTO implements Serializable {

    private Long id;

    @Size(max = 20000)
    private String users;

    private LocalDate firstPeriod;

    private LocalDate lastPeriod;

    @Size(max = 10000)
    private String tags;

    private LocalDate nextSendTime;

    private Boolean active;

    private String secret;

    private Set<ExtraDTO> extras = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public LocalDate getFirstPeriod() {
        return firstPeriod;
    }

    public void setFirstPeriod(LocalDate firstPeriod) {
        this.firstPeriod = firstPeriod;
    }

    public LocalDate getLastPeriod() {
        return lastPeriod;
    }

    public void setLastPeriod(LocalDate lastPeriod) {
        this.lastPeriod = lastPeriod;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public LocalDate getNextSendTime() {
        return nextSendTime;
    }

    public void setNextSendTime(LocalDate nextSendTime) {
        this.nextSendTime = nextSendTime;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Set<ExtraDTO> getExtras() {
        return extras;
    }

    public void setExtras(Set<ExtraDTO> extras) {
        this.extras = extras;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StackOverFlowApiClientDTO stackOverFlowApiClientDTO = (StackOverFlowApiClientDTO) o;
        if (stackOverFlowApiClientDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stackOverFlowApiClientDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StackOverFlowApiClientDTO{" +
            "id=" + getId() +
            ", users='" + getUsers() + "'" +
            ", firstPeriod='" + getFirstPeriod() + "'" +
            ", lastPeriod='" + getLastPeriod() + "'" +
            ", tags='" + getTags() + "'" +
            ", nextSendTime='" + getNextSendTime() + "'" +
            ", active='" + isActive() + "'" +
            ", secret='" + getSecret() + "'" +
            "}";
    }
}
