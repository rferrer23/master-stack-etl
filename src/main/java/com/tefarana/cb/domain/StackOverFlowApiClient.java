package com.tefarana.cb.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A StackOverFlowApiClient.
 */
@Entity
@Table(name = "stack_over_flow_api_client")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class StackOverFlowApiClient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 20000)
    @Column(name = "users", length = 20000)
    private String users;

    @Column(name = "first_period")
    private LocalDate firstPeriod;

    @Column(name = "last_period")
    private LocalDate lastPeriod;

    @Size(max = 10000)
    @Column(name = "tags", length = 10000)
    private String tags;

    @Column(name = "next_send_time")
    private LocalDate nextSendTime;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "secret")
    private String secret;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "stack_over_flow_api_client_extra",
               joinColumns = @JoinColumn(name = "stack_over_flow_api_clients_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "extras_id", referencedColumnName = "id"))
    private Set<Extra> extras = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsers() {
        return users;
    }

    public StackOverFlowApiClient users(String users) {
        this.users = users;
        return this;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public LocalDate getFirstPeriod() {
        return firstPeriod;
    }

    public StackOverFlowApiClient firstPeriod(LocalDate firstPeriod) {
        this.firstPeriod = firstPeriod;
        return this;
    }

    public void setFirstPeriod(LocalDate firstPeriod) {
        this.firstPeriod = firstPeriod;
    }

    public LocalDate getLastPeriod() {
        return lastPeriod;
    }

    public StackOverFlowApiClient lastPeriod(LocalDate lastPeriod) {
        this.lastPeriod = lastPeriod;
        return this;
    }

    public void setLastPeriod(LocalDate lastPeriod) {
        this.lastPeriod = lastPeriod;
    }

    public String getTags() {
        return tags;
    }

    public StackOverFlowApiClient tags(String tags) {
        this.tags = tags;
        return this;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public LocalDate getNextSendTime() {
        return nextSendTime;
    }

    public StackOverFlowApiClient nextSendTime(LocalDate nextSendTime) {
        this.nextSendTime = nextSendTime;
        return this;
    }

    public void setNextSendTime(LocalDate nextSendTime) {
        this.nextSendTime = nextSendTime;
    }

    public Boolean isActive() {
        return active;
    }

    public StackOverFlowApiClient active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getSecret() {
        return secret;
    }

    public StackOverFlowApiClient secret(String secret) {
        this.secret = secret;
        return this;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Set<Extra> getExtras() {
        return extras;
    }

    public StackOverFlowApiClient extras(Set<Extra> extras) {
        this.extras = extras;
        return this;
    }

    public StackOverFlowApiClient addExtra(Extra extra) {
        this.extras.add(extra);
        extra.getStackOverFlowApiClients().add(this);
        return this;
    }

    public StackOverFlowApiClient removeExtra(Extra extra) {
        this.extras.remove(extra);
        extra.getStackOverFlowApiClients().remove(this);
        return this;
    }

    public void setExtras(Set<Extra> extras) {
        this.extras = extras;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StackOverFlowApiClient stackOverFlowApiClient = (StackOverFlowApiClient) o;
        if (stackOverFlowApiClient.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stackOverFlowApiClient.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StackOverFlowApiClient{" +
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
