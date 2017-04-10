package com.mascot.server.model;

/**
 * Created by Nikolay on 07.04.2017.
 */
public enum IntegrationActionType {
    START, END,
    SEND_NEW_USER, SEND_UPDATE_USER, SEND_REMOVE_USER,
    SEND_NEW_PRODUCT, SEND_UPDATE_PRODUCT, SEND_REMOVE_PRODUCT,
    SEND_NEW_CLOTH, SEND_UPDATE_CLOTH, SEND_REMOVE_CLOTH,
    RECIEVE_ORDER
}
