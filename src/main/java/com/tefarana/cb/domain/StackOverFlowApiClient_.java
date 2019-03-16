package com.tefarana.cb.domain;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel( StackOverFlowApiClient.class )
public class StackOverFlowApiClient_ {
    public static volatile SingularAttribute<StackOverFlowApiClient, Long> id;
    public static volatile SingularAttribute<StackOverFlowApiClient, String> users;
    public static volatile SingularAttribute<StackOverFlowApiClient, LocalDate> firstPeriod;
    public static volatile SingularAttribute<StackOverFlowApiClient, LocalDate> lastPeriod;
    public static volatile SingularAttribute<StackOverFlowApiClient, String> tags;
    public static volatile SingularAttribute<StackOverFlowApiClient, LocalDate> nextSendTime;
    public static volatile SingularAttribute<StackOverFlowApiClient, Boolean> active;
    public static volatile SingularAttribute<StackOverFlowApiClient, String> secret;
    public static volatile CollectionAttribute<StackOverFlowApiClient, Set<Extra>> extras;
     
}