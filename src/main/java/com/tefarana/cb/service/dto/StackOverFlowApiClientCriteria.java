package com.tefarana.cb.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the StackOverFlowApiClient entity. This class is used in StackOverFlowApiClientResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /stack-over-flow-api-clients?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StackOverFlowApiClientCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter users;

    private LocalDateFilter firstPeriod;

    private LocalDateFilter lastPeriod;

    private StringFilter tags;

    private LocalDateFilter nextSendTime;

    private BooleanFilter active;

    private StringFilter secret;

    private LongFilter extraId;

    public StackOverFlowApiClientCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getUsers() {
        return users;
    }

    public void setUsers(StringFilter users) {
        this.users = users;
    }

    public LocalDateFilter getFirstPeriod() {
        return firstPeriod;
    }

    public void setFirstPeriod(LocalDateFilter firstPeriod) {
        this.firstPeriod = firstPeriod;
    }

    public LocalDateFilter getLastPeriod() {
        return lastPeriod;
    }

    public void setLastPeriod(LocalDateFilter lastPeriod) {
        this.lastPeriod = lastPeriod;
    }

    public StringFilter getTags() {
        return tags;
    }

    public void setTags(StringFilter tags) {
        this.tags = tags;
    }

    public LocalDateFilter getNextSendTime() {
        return nextSendTime;
    }

    public void setNextSendTime(LocalDateFilter nextSendTime) {
        this.nextSendTime = nextSendTime;
    }

    public BooleanFilter getActive() {
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
    }

    public StringFilter getSecret() {
        return secret;
    }

    public void setSecret(StringFilter secret) {
        this.secret = secret;
    }

    public LongFilter getExtraId() {
        return extraId;
    }

    public void setExtraId(LongFilter extraId) {
        this.extraId = extraId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final StackOverFlowApiClientCriteria that = (StackOverFlowApiClientCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(users, that.users) &&
            Objects.equals(firstPeriod, that.firstPeriod) &&
            Objects.equals(lastPeriod, that.lastPeriod) &&
            Objects.equals(tags, that.tags) &&
            Objects.equals(nextSendTime, that.nextSendTime) &&
            Objects.equals(active, that.active) &&
            Objects.equals(secret, that.secret) &&
            Objects.equals(extraId, that.extraId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        users,
        firstPeriod,
        lastPeriod,
        tags,
        nextSendTime,
        active,
        secret,
        extraId
        );
    }

    @Override
    public String toString() {
        return "StackOverFlowApiClientCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (users != null ? "users=" + users + ", " : "") +
                (firstPeriod != null ? "firstPeriod=" + firstPeriod + ", " : "") +
                (lastPeriod != null ? "lastPeriod=" + lastPeriod + ", " : "") +
                (tags != null ? "tags=" + tags + ", " : "") +
                (nextSendTime != null ? "nextSendTime=" + nextSendTime + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (secret != null ? "secret=" + secret + ", " : "") +
                (extraId != null ? "extraId=" + extraId + ", " : "") +
            "}";
    }

}
